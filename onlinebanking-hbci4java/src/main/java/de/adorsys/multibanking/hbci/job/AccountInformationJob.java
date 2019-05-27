/*
 * Copyright 2018-2019 adorsys GmbH & Co KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.adorsys.multibanking.hbci.job;

import de.adorsys.multibanking.domain.BankAccount;
import de.adorsys.multibanking.domain.BankApi;
import de.adorsys.multibanking.domain.Product;
import de.adorsys.multibanking.domain.TanTransportType;
import de.adorsys.multibanking.domain.exception.MultibankingException;
import de.adorsys.multibanking.domain.request.LoadAccountInformationRequest;
import de.adorsys.multibanking.domain.response.LoadAccountInformationResponse;
import de.adorsys.multibanking.hbci.model.*;
import lombok.extern.slf4j.Slf4j;
import org.kapott.hbci.GV.GVSEPAInfo;
import org.kapott.hbci.GV.GVTANMediaList;
import org.kapott.hbci.manager.HBCIDialog;
import org.kapott.hbci.passport.PinTanPassport;
import org.kapott.hbci.structures.Konto;

import java.util.*;
import java.util.stream.Collectors;

import static de.adorsys.multibanking.domain.exception.MultibankingError.HBCI_ERROR;

@Slf4j
public class AccountInformationJob {

    public static List<TanTransportType> extractTanTransportTypes(PinTanPassport hbciPassport) {
        return hbciPassport.getUserTwostepMechanisms()
                .stream()
                .map(id -> hbciPassport.getBankTwostepMechanisms().get(id))
                .filter(Objects::nonNull)
                .map(hbciTwoStepMechanism -> TanTransportType.builder()
                        .id(hbciTwoStepMechanism.getSecfunc())
                        .name(hbciTwoStepMechanism.getName())
                        .inputInfo(hbciTwoStepMechanism.getInputinfo())
                        .medium(hbciPassport.getTanMedia(hbciTwoStepMechanism.getId()) != null ?
                                hbciPassport.getTanMedia(hbciTwoStepMechanism.getId()).mediaName : null)
                        .build())
                .collect(Collectors.toList());

    }

    public LoadAccountInformationResponse loadBankAccounts(LoadAccountInformationRequest request,
                                                           HbciCallback callback) {
        log.info("Loading account list for bank [{}]", request.getBankCode());

        HbciDialogRequest dialogRequest = HbciDialogRequest.builder()
                .bankCode(request.getBankCode() != null ? request.getBankCode() : request.getBankAccess().getBankCode())
                .customerId(request.getBankAccess().getBankLogin())
                .login(request.getBankAccess().getBankLogin2())
                .hbciPassportState(request.getBankAccess().getHbciPassportState())
                .pin(request.getPin())
                .callback(callback)
                .build();

        dialogRequest.setProduct(Optional.ofNullable(request.getProduct())
                .map(product -> new Product(product.getName(), product.getVersion()))
                .orElse(null));
        dialogRequest.setBpd(request.getBpd());

        HBCIDialog dialog = HbciDialogFactory.createDialog(null, dialogRequest);

        if (!dialog.getPassport().jobSupported("SEPAInfo"))
            throw new MultibankingException(HBCI_ERROR, "SEPAInfo job not supported");

        log.info("fetching SEPA informations");
        GVSEPAInfo gvsepaInfo = new GVSEPAInfo(dialog.getPassport());
        dialog.addTask(gvsepaInfo);

        // TAN-Medien abrufen
        if (request.isUpdateTanTransportTypes() && dialog.getPassport().jobSupported("TANMediaList")) {
            log.info("fetching TAN media list");
            dialog.addTask(new GVTANMediaList(dialog.getPassport()));
        }

        dialog.execute(true);

        if (gvsepaInfo.getJobResult().getJobStatus().hasErrors()) {
            throw new MultibankingException(HBCI_ERROR, gvsepaInfo.getJobResult().getJobStatus().getErrorList());
        }

        request.getBankAccess().setBankName(dialog.getPassport().getInstName());
        List<BankAccount> hbciAccounts = new ArrayList<>();
        for (Konto konto : dialog.getPassport().getAccounts()) {
            BankAccount bankAccount = HbciMapping.toBankAccount(konto);
            bankAccount.externalId(BankApi.HBCI, UUID.randomUUID().toString());
            bankAccount.bankName(request.getBankAccess().getBankName());
            hbciAccounts.add(bankAccount);
        }

        if (request.isUpdateTanTransportTypes()) {
            request.getBankAccess().setTanTransportTypes(new HashMap<>());
            request.getBankAccess().getTanTransportTypes().put(BankApi.HBCI,
                    extractTanTransportTypes(dialog.getPassport()));
        }

        request.getBankAccess().setHbciPassportState(new HbciPassport.State(dialog.getPassport()).toJson());
        return LoadAccountInformationResponse.builder()
                .bankAccess(request.getBankAccess())
                .bankAccounts(hbciAccounts)
                .build();
    }
}
