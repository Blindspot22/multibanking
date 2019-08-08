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

package de.adorsys.multibanking.hbci.model;

import de.adorsys.multibanking.domain.exception.MultibankingException;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.kapott.hbci.manager.BankInfo;
import org.kapott.hbci.manager.HBCIDialog;
import org.kapott.hbci.manager.HBCIProduct;
import org.kapott.hbci.manager.HBCIUtils;

import java.util.HashMap;
import java.util.Optional;

import static de.adorsys.multibanking.domain.exception.MultibankingError.BANK_NOT_SUPPORTED;

@UtilityClass
public class HbciDialogFactory {

    public static HBCIDialog startHbciDialog(HbciPassport passport, HbciDialogRequest dialogRequest) {
        BankInfo bankInfo = Optional.ofNullable(HBCIUtils.getBankInfo(dialogRequest.getBankCode()))
            .orElseThrow(() -> new MultibankingException(BANK_NOT_SUPPORTED,
                "Bank [" + dialogRequest.getBankCode() + "] not supported"));

        HBCIProduct hbciProduct = Optional.ofNullable(dialogRequest.getHbciProduct())
            .map(product -> new HBCIProduct(product.getName(), product.getVersion()))
            .orElse(null);

        HbciPassport newPassport = Optional.ofNullable(passport)
            .orElseGet(() -> createPassport(bankInfo.getPinTanVersion().getId(), dialogRequest.getBankCode(),
                dialogRequest.getCustomerId(), dialogRequest.getLogin(), hbciProduct,
                dialogRequest.getCallback()
            ));

        Optional.ofNullable(dialogRequest.getHbciPassportState())
            .ifPresent(s -> HbciPassport.State.fromJson(dialogRequest.getHbciPassportState()).apply(newPassport));

        Optional.ofNullable(dialogRequest.getHbciBPD())
            .ifPresent(newPassport::setBPD);

        Optional.ofNullable(dialogRequest.getHbciUPD())
            .ifPresent(newPassport::setUPD);

        Optional.ofNullable(dialogRequest.getHbciSysId())
            .ifPresent(newPassport::setSysId);

        newPassport.setPIN(dialogRequest.getPin());

        String url = bankInfo.getPinTanAddress();
        String proxyPrefix = System.getProperty("proxyPrefix", null);
        if (proxyPrefix != null) {
            url = proxyPrefix + url;
        }
        newPassport.setHost(url);

        return new HBCIDialog(newPassport);
    }

    public static HbciPassport createPassport(HbciPassport.State state, HbciCallback callback) {
        return createPassport(state.getHbciVersion(), state.getBlz(), state.getCustomerId(), state.getUserId(),
            state.getHbciProduct(), callback);
    }

    private static HbciPassport createPassport(String hbciVersion, String bankCode, String customerId, String login,
                                               HBCIProduct hbciProduct, HbciCallback callback) {
        HashMap<String, String> properties = new HashMap<>();
        properties.put("kernel.rewriter", "InvalidSegment,WrongStatusSegOrder,WrongSequenceNumbers,MissingMsgRef," +
            "HBCIVersion,SigIdLeadingZero,InvalidSuppHBCIVersion,SecTypeTAN,KUmsDelimiters,KUmsEmptyBDateSets");
        properties.put("log.loglevel.default", "2");
        properties.put("default.hbciversion", "FinTS3");
        properties.put("client.passport.PinTan.checkcert", "1");
        properties.put("client.passport.PinTan.init", "1");
        properties.put("client.errors.ignoreJobNotSupported", "yes");

        properties.put("client.passport.country", "DE");
        properties.put("client.passport.blz", bankCode);
        properties.put("client.passport.customerId", customerId);
        properties.put("client.errors.ignoreCryptErrors", "yes");

        if (StringUtils.isNotBlank(login)) {
            properties.put("client.passport.userId", login);
        }

        return new HbciPassport(hbciVersion, properties, callback, hbciProduct);
    }

}
