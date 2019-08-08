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

package de.adorsys.multibanking.hbci;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import de.adorsys.multibanking.domain.BankAccess;
import de.adorsys.multibanking.domain.BankAccount;
import de.adorsys.multibanking.domain.BankApi;
import de.adorsys.multibanking.domain.BankApiUser;
import de.adorsys.multibanking.domain.exception.MultibankingError;
import de.adorsys.multibanking.domain.exception.MultibankingException;
import de.adorsys.multibanking.domain.request.*;
import de.adorsys.multibanking.domain.response.*;
import de.adorsys.multibanking.domain.spi.Consent;
import de.adorsys.multibanking.domain.spi.ConsentStatus;
import de.adorsys.multibanking.domain.spi.OnlineBankingService;
import de.adorsys.multibanking.domain.spi.StrongCustomerAuthorisable;
import de.adorsys.multibanking.hbci.job.*;
import de.adorsys.multibanking.hbci.model.HbciCallback;
import de.adorsys.multibanking.hbci.model.HbciDialogFactory;
import de.adorsys.multibanking.hbci.model.HbciDialogRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kapott.hbci.exceptions.HBCI_Exception;
import org.kapott.hbci.manager.BankInfo;
import org.kapott.hbci.manager.HBCIDialog;
import org.kapott.hbci.manager.HBCIUtils;
import org.kapott.hbci.manager.HBCIVersion;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static de.adorsys.multibanking.hbci.job.AccountInformationJob.extractTanTransportTypes;

@Slf4j
public class Hbci4JavaBanking implements OnlineBankingService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private Map<String, Map<String, String>> bpdCache;

    public Hbci4JavaBanking() {
        this(null, false);
    }

    public Hbci4JavaBanking(boolean cacheBpdUpd) {
        this(null, cacheBpdUpd);
    }

    public Hbci4JavaBanking(InputStream customBankConfigInput, boolean cacheBpdUpd) {
        if (cacheBpdUpd) {
            bpdCache = new HashMap<>();
        }

        try (InputStream inputStream = Optional.ofNullable(customBankConfigInput)
            .orElseGet(this::getDefaultBanksInput)) {
            HBCIUtils.refreshBLZList(inputStream);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.registerModule(new Jdk8Module());
    }

    private InputStream getDefaultBanksInput() {
        return Optional.ofNullable(HBCIUtils.class.getClassLoader().getResource("blz.properties"))
            .map(url -> {
                try {
                    return url.openStream();
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            })
            .orElseThrow(() -> new RuntimeException("blz.properties not exists in classpath"));
    }

    @Override
    public BankApi bankApi() {
        return BankApi.HBCI;
    }

    @Override
    public boolean externalBankAccountRequired() {
        return false;
    }

    @Override
    public boolean userRegistrationRequired() {
        return false;
    }

    @Override
    public BankApiUser registerUser(BankAccess bankAccess, String pin) {
        //no registration needed
        return null;
    }

    @Override
    public void removeUser(BankApiUser bankApiUser) {
        //not needed
    }

    public ScaMethodsResponse authenticatePsu(String bankingUrl, AuthenticatePsuRequest authenticatePsuRequest) {
        HbciDialogRequest dialogRequest = HbciDialogRequest.builder()
            .bankCode(authenticatePsuRequest.getBankCode())
            .login(authenticatePsuRequest.getLogin())
            .customerId(authenticatePsuRequest.getCustomerId())
            .pin(authenticatePsuRequest.getPin())
            .build();

        BpdUpdHbciCallback hbciCallback = setRequestBpdAndCreateCallback(dialogRequest.getBankCode(), dialogRequest);
        dialogRequest.setCallback(hbciCallback);

        HBCIDialog dialog = createDialog(bankingUrl, dialogRequest);

        ScaMethodsResponse response = ScaMethodsResponse.builder()
            .tanTransportTypes(extractTanTransportTypes(dialog.getPassport()))
            .build();
        updateUpd(hbciCallback, response);
        return response;
    }

    @Override
    public LoadAccountInformationResponse loadBankAccounts(LoadAccountInformationRequest request) {
        checkBankExists(request.getBankCode(), request.getBankUrl());

        BpdUpdHbciCallback hbciCallback = setRequestBpdAndCreateCallback(request.getBankCode(), request);

        try {
            AccountInformationJob accountInformationJob = new AccountInformationJob(request);
            LoadAccountInformationResponse response = accountInformationJob.execute(hbciCallback);
            updateUpd(hbciCallback, response);
            return response;
        } catch (HBCI_Exception e) {
            throw handleHbciException(e);
        }
    }

    @Override
    public LoadBookingsResponse loadBookings(LoadBookingsRequest request) {
        checkBankExists(request.getBankCode(), request.getBankUrl());

        BpdUpdHbciCallback hbciCallback = setRequestBpdAndCreateCallback(request.getBankCode(), request);

        try {
            LoadBookingsJob loadBookingsJob = new LoadBookingsJob(request);
            LoadBookingsResponse response = loadBookingsJob.execute(hbciCallback);
            updateUpd(hbciCallback, response);
            return response;
        } catch (HBCI_Exception e) {
            throw handleHbciException(e);
        }
    }

    public LoadBalancesResponse loadBalances(String bankingUrl, LoadBalanceRequest request) {
        checkBankExists(request.getBankCode(), bankingUrl);

        BpdUpdHbciCallback hbciCallback = setRequestBpdAndCreateCallback(request.getBankCode(), request);

        try {
            LoadBalancesJob loadBalanceJob = new LoadBalancesJob(request);
            LoadBalancesResponse response = loadBalanceJob.execute(hbciCallback);
            updateUpd(hbciCallback, response);
            return response;
        } catch (HBCI_Exception e) {
            throw handleHbciException(e);
        }
    }

    @Override
    public boolean bookingsCategorized() {
        return false;
    }

    public void executeTransactionWithoutSca(String bankingUrl, TransactionRequest paymentRequest) {
        checkBankExists(paymentRequest.getBankCode(), bankingUrl);
        Optional.ofNullable(bpdCache)
            .ifPresent(cache -> paymentRequest.setHbciBPD(cache.get(paymentRequest.getBankCode())));

        try {
            TransferJob transferJob = new TransferJob();
            transferJob.requestTransfer(paymentRequest);
        } catch (HBCI_Exception e) {
            throw handleHbciException(e);
        }
    }

    @Override
    public AuthorisationCodeResponse requestAuthorizationCode(TransactionRequest transactionRequest) {
        checkBankExists(transactionRequest.getBankCode(), transactionRequest.getBankUrl());
        BpdUpdHbciCallback hbciCallback = setRequestBpdAndCreateCallback(transactionRequest.getBankCode(),
            transactionRequest);

        try {
            ScaRequiredJob scaJob = Optional.ofNullable(transactionRequest.getTransaction())
                .map(transaction -> createScaJob(transactionRequest))
                .orElse(new EmptyJob(transactionRequest));

            AuthorisationCodeResponse response = (AuthorisationCodeResponse) scaJob.execute(hbciCallback);
            updateUpd(hbciCallback, response);

            return response;
        } catch (HBCI_Exception e) {
            throw handleHbciException(e);
        }
    }

    @Override
    public SubmitAuthorizationCodeResponse submitAuthorizationCode(SubmitAuthorizationCodeRequest submitAuthorizationCodeRequest) {
        try {
            Map<String, String> bpd = Optional.ofNullable(bpdCache)
                .map(cache -> cache.get(submitAuthorizationCodeRequest.getBankCode()))
                .orElse(null);

            submitAuthorizationCodeRequest.setHbciBPD(bpd);

            ScaRequiredJob scaJob = Optional.ofNullable(submitAuthorizationCodeRequest.getTransaction())
                .map(transaction -> createScaJob(submitAuthorizationCodeRequest))
                .orElse(new EmptyJob(submitAuthorizationCodeRequest));

            return new SubmitAuthorisationCodeJob(scaJob).sumbitAuthorizationCode(submitAuthorizationCodeRequest);
        } catch (HBCI_Exception e) {
            throw handleHbciException(e);
        }
    }

    @Override
    public StrongCustomerAuthorisable getStrongCustomerAuthorisation() {
        return new StrongCustomerAuthorisable() {

            @Override
            public Consent createConsent(Consent consentTemplate) {
                consentTemplate.setScaStatus(ConsentStatus.PSU_AUTHORISED);
                // FIXME we should save the consent somewhere
                consentTemplate.setConsentId(UUID.randomUUID().toString());
                return consentTemplate;
            }

            @Override
            public Consent getConsent(String consentId) {
                // FIXME get the consent from the local storage
                return null;
            }

            @Override
            public Consent loginConsent(Consent consent) {
                // there is no login for HBCI of the consent
                if (consent.getScaStatus() == ConsentStatus.PARTIALLY_AUTHORISED) {
                    consent.setScaStatus(ConsentStatus.PSU_AUTHORISED);
                }
                // get the List of TAN Methods available
                // FIXME call HBCI and get the Tan Method List
//                consent.setScaMethodList(new ArrayList<>());
                return consent;
            }

            @Override
            public Consent authorizeConsent(Consent consent) {
                // never call this for HBCI
                // you need to call the distinct function you want to authorize
                // it will return the challenge
                throw new IllegalArgumentException();
            }

            @Override
            public Consent selectScaMethod(Consent consent) {
                // there is no server side selection
                if (consent.getScaStatus() == ConsentStatus.PSU_AUTHORISED) {
                    consent.setScaStatus(ConsentStatus.SCA_METHOD_SELECTED);
                }
                return consent;
            }

            @Override
            public void validateConsent(String consentId) throws MultibankingException {
                Consent consent = getConsent(consentId);
                if (consent == null || consent.getCredentials() == null) {
                    throw new MultibankingException(MultibankingError.INVALID_PIN);
                }
                if (consent.getSelectedScaMethodId() == null) {
                    throw new MultibankingException(MultibankingError.INVALID_SCA_METHOD);
                }
                if (consent.getTan() == null) {
                    throw new MultibankingException(MultibankingError.INVALID_TAN);
                }
            }
        };
    }

    @Override
    public void removeBankAccount(BankAccount bankAccount, BankApiUser bankApiUser) {
        //not needed
    }

    @SuppressWarnings("unchecked")
    private BpdUpdHbciCallback setRequestBpdAndCreateCallback(String bankCode, AbstractRequest request) {
        return Optional.ofNullable(bpdCache)
            .map(cache -> {
                request.setHbciBPD(cache.get(bankCode));
                return new BpdUpdHbciCallback(bankCode, bpdCache);
            }).orElse(null);
    }

    private HBCIDialog createDialog(String bankingUrl, HbciDialogRequest dialogRequest) {
        checkBankExists(dialogRequest.getBankCode(), bankingUrl);
        Optional.ofNullable(bpdCache)
            .ifPresent(cache -> dialogRequest.setHbciBPD(cache.get(dialogRequest.getBankCode())));
        try {
            return HbciDialogFactory.startHbciDialog(null, dialogRequest);
        } catch (HBCI_Exception e) {
            throw handleHbciException(e);
        }
    }

    @Override
    public boolean bankSupported(String bankCode) {
        BankInfo bankInfo = HBCIUtils.getBankInfo(bankCode);
        return bankInfo != null && bankInfo.getPinTanVersion() != null;
    }

    private void checkBankExists(String bankCode, String bankingUrl) {
        Optional.ofNullable(bankingUrl).ifPresent(s -> {
            BankInfo bankInfo = HBCIUtils.getBankInfo(bankCode);
            if (bankInfo == null) {
                bankInfo = new BankInfo();
                bankInfo.setBlz(bankCode);
                bankInfo.setPinTanAddress(s);
                bankInfo.setPinTanVersion(HBCIVersion.HBCI_300);
                HBCIUtils.addBankInfo(bankInfo);
            }
        });
    }

    private ScaRequiredJob createScaJob(TransactionRequest transactionRequest) {
        switch (transactionRequest.getTransaction().getTransactionType()) {
            case SINGLE_PAYMENT:
            case FUTURE_SINGLE_PAYMENT:
                return new SinglePaymentJob(transactionRequest);
            case FOREIGN_PAYMENT:
                return new ForeignPaymentJob(transactionRequest);
            case BULK_PAYMENT:
            case FUTURE_BULK_PAYMENT:
                return new BulkPaymentJob(transactionRequest);
            case STANDING_ORDER:
                return new NewStandingOrderJob(transactionRequest);
            case RAW_SEPA:
                return new RawSepaJob(transactionRequest);
            case FUTURE_SINGLE_PAYMENT_DELETE:
                return new DeleteFutureSinglePaymentJob(transactionRequest);
            case FUTURE_BULK_PAYMENT_DELETE:
                return new DeleteFutureBulkPaymentJob(transactionRequest);
            case STANDING_ORDER_DELETE:
                return new DeleteStandingOrderJob(transactionRequest);
            case TAN_REQUEST:
                return new EmptyJob(transactionRequest);
            default:
                throw new IllegalArgumentException("invalid transaction type " + transactionRequest.getTransaction().getTransactionType());
        }
    }

    private RuntimeException handleHbciException(HBCI_Exception e) {
        Throwable processException = e;
        while (processException.getCause() != null && !(processException.getCause() instanceof MultibankingException)) {
            processException = processException.getCause();
        }

        if (processException.getCause() instanceof MultibankingException) {
            return (MultibankingException) processException.getCause();
        }

        return e;
    }

    private void updateUpd(BpdUpdHbciCallback bpdUpdHbciCallback, AbstractResponse response) {
        Optional.ofNullable(bpdUpdHbciCallback)
            .ifPresent(callback -> {
                response.setHbciUpd(callback.getUpd());
                response.setHbciSysId(callback.getSysId());
            });
    }

    @RequiredArgsConstructor
    @Data
    @EqualsAndHashCode(callSuper = false)
    private static class BpdUpdHbciCallback extends HbciCallback {

        private final String bankCode;
        private final Map<String, Map<String, String>> bpdCache;
        private Map<String, String> upd;
        private String sysId;

        @SuppressWarnings("unchecked")
        @Override
        public void status(int statusTag, Object o) {
            if (statusTag == STATUS_INST_BPD_INIT_DONE) {
                bpdCache.put(bankCode, (Map<String, String>) o);
            } else if (statusTag == STATUS_INIT_UPD_DONE) {
                this.upd = (Map<String, String>) o;
            }
        }

        @Override
        public void status(int statusTag, Object[] o) {
            if (statusTag == STATUS_INIT_SYSID_DONE) {
                this.sysId = o[1].toString();
            }
        }
    }
}
