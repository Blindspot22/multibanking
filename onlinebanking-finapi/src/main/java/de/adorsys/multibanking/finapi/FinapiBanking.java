package de.adorsys.multibanking.finapi;

import de.adorsys.multibanking.domain.*;
import de.adorsys.multibanking.domain.request.*;
import de.adorsys.multibanking.domain.response.*;
import de.adorsys.multibanking.domain.spi.OnlineBankingService;
import de.adorsys.multibanking.domain.spi.StrongCustomerAuthorisable;
import de.adorsys.multibanking.domain.utils.Utils;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.*;
import io.swagger.client.model.*;
import org.adorsys.envutils.EnvProperties;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static de.adorsys.multibanking.domain.utils.Utils.getSecureRandom;

/**
 * Created by alexg on 17.05.17.
 */
public class FinapiBanking implements OnlineBankingService {

    //https://finapi.zendesk.com/hc/en-us/articles/222013148
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789&(){}[]" +
            ".:,?!+-_$@#";
    private static final Logger LOG = LoggerFactory.getLogger(FinapiBanking.class);
    private static SecureRandom random = getSecureRandom();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private String finapiClientId;
    private String finapiSecret;
    private String finapiConnectionUrl;
    private AccessToken clientToken;

    public FinapiBanking() {
        finapiClientId = EnvProperties.getEnvOrSysProp("FINAPI_CLIENT_ID", true);
        finapiSecret = EnvProperties.getEnvOrSysProp("FINAPI_SECRET", true);
        finapiConnectionUrl = EnvProperties.getEnvOrSysProp("FINAPI_CONNECTION_URL", "https://sandbox.finapi.io/");

        if (finapiClientId == null || finapiSecret == null) {
            LOG.warn("missing env properties FINAPI_CLIENT_ID and/or FINAPI_SECRET");
        } else {
            authorizeClient();
        }
    }

    @Override
    public BankApi bankApi() {
        return BankApi.FINAPI;
    }

    @Override
    public boolean externalBankAccountRequired() {
        return true;
    }

    @Override
    public boolean userRegistrationRequired() {
        return true;
    }

    @Override
    public boolean bankSupported(String bankCode) {
        if (clientToken == null) {
            LOG.warn("skip finapi bank api, client token not available, check env properties FINAPI_CLIENT_ID and/or " +
                    "FINAPI_SECRET");
        }
        return true;
    }

    @Override
    public BankApiUser registerUser(BankAccess bankAccess, String pin) {
        String password = RandomStringUtils.random(20, 0, 0, false, false, CHARACTERS.toCharArray(), random);

        try {
            new UsersApi(createApiClient()).createUser(new UserCreateParams().email(bankAccess.getBankLogin() +
                    "@admb.de").password(password).id(bankAccess.getBankLogin()));
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }

        BankApiUser bankApiUser = new BankApiUser();
        bankApiUser.setApiUserId(bankAccess.getBankLogin());
        bankApiUser.setApiPassword(password);
        bankApiUser.setBankApi(BankApi.FINAPI);

        return bankApiUser;
    }

    @Override
    public void removeUser(BankApiUser bankApiUser) {
        try {
            new UsersApi(createApiClient()).deleteUnverifiedUser(bankApiUser.getApiUserId());
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LoadAccountInformationResponse loadBankAccounts(String bankingUrl,
                                                           LoadAccountInformationRequest loadAccountInformationRequest) {
        LOG.info("load bank accounts");
        BankAccess bankAccess = loadAccountInformationRequest.getBankAccess();

        try {
            PageableBankList searchAllBanks = new BanksApi(createApiClient()).getAndSearchAllBanks(null,
                    bankAccess.getBankCode(), null, null, null, null, null, null, null, null);
            if (searchAllBanks.getBanks().size() != 1) {
                throw new RuntimeException("Bank not supported");
            }

            bankAccess.setBankName(searchAllBanks.getBanks().get(0).getName());

            ApiClient apiClient = createUserApiClient();
            apiClient.setAccessToken(authorizeUser(loadAccountInformationRequest.getBankApiUser()));

            BankConnection connections =
                    new BankConnectionsApi(apiClient).importBankConnection(new ImportBankConnectionParams()
                            .bankId(searchAllBanks.getBanks().get(0).getId())
                            .bankingUserId(bankAccess.getBankLogin())
                            .bankingPin(loadAccountInformationRequest.getPin())
                            .storePin(loadAccountInformationRequest.isStorePin()));

            bankAccess.externalId(bankApi(), connections.getId().toString());

            AccountList accounts = new AccountsApi(apiClient).getAndSearchAllAccounts(null, null, null,
                    Arrays.asList(connections.getId()), null, null, null, null);

            return LoadAccountInformationResponse.builder().bankAccounts(accounts.getAccounts().stream().map(account ->
                    new BankAccount()
                            .externalId(bankApi(), account.getId().toString())
                            .owner(account.getAccountHolderName())
                            .bankName(bankAccess.getBankName())
                            .accountNumber(account.getAccountNumber())
                            .name(account.getAccountTypeName())
                            .iban(account.getIban())
                            .blz(bankAccess.getBankCode())
                            .type(BankAccountType.fromFinapiType(account.getAccountTypeId().intValue()))
                            .balances(new BalancesReport()
                                    .readyBalance(Balance.builder().amount(account.getBalance()).build())))
                    .collect(Collectors.toList()))
                    .build();
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeBankAccount(BankAccount bankAccount, BankApiUser bankApiUser) {
        ApiClient apiClient = createUserApiClient();
        apiClient.setAccessToken(authorizeUser(bankApiUser));

        try {
            new AccountsApi(apiClient).deleteAccount(Long.parseLong(bankAccount.getExternalIdMap().get(bankApi())));
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LoadBookingsResponse loadBookings(String bankingUrl, LoadBookingsRequest loadBookingsRequest) {
        BankAccount bankAccount = loadBookingsRequest.getBankAccount();

        //TODO standing orders needed
        LOG.debug("load bookings for account [{}]", bankAccount.getAccountNumber());
        ApiClient apiClient = createUserApiClient();
        apiClient.setAccessToken(authorizeUser(loadBookingsRequest.getBankApiUser()));

        List<Long> accountIds = Arrays.asList(Long.parseLong(bankAccount.getExternalIdMap().get(bankApi())));
        List<String> order = Arrays.asList("id,desc");

        List<Booking> bookingList = new ArrayList<>();
        PageableTransactionList transactionsResponse = null;
        Integer nextPage = null;
        try {
            //wait finapi loaded bookings
            Account account = waitAccountSynced(bankAccount, apiClient);
            //wait finapi categorized bookings
            waitBookingsCategorized(loadBookingsRequest.getBankAccess(), apiClient);

            while (nextPage == null || transactionsResponse.getPaging().getPage() < transactionsResponse.getPaging().getPageCount()) {
                transactionsResponse = new TransactionsApi(apiClient).getAndSearchAllTransactions("bankView", null,
                        null, null, null, accountIds, null, null, null, null, null, null, null, null, null, null, null,
                        null, null, null, null, nextPage, null, order);
                nextPage = transactionsResponse.getPaging().getPage() + 1;
                bookingList.addAll(transactionsResponse.getTransactions().stream().map(transaction -> {
                            Booking booking = new Booking();
                            booking.setExternalId(transaction.getId().toString());
                            booking.setBankApi(bankApi());
                            booking.setBookingDate(LocalDate.from(formatter.parse(transaction.getBankBookingDate(),
                                    new ParsePosition(0))));
                            booking.setValutaDate(LocalDate.from(formatter.parse(transaction.getValueDate(),
                                    new ParsePosition(0))));

                            booking.setAmount(transaction.getAmount());
                            booking.setUsage(transaction.getPurpose());
                            booking.setCreditorId(Utils.extractCreditorId(transaction.getPurpose()));
                            booking.setMandateReference(Utils.extractMandateReference(transaction.getPurpose()));

                            if (transaction.getCounterpartName() != null) {
                                booking.setOtherAccount(new BankAccount());
                                booking.getOtherAccount().setName(transaction.getCounterpartName());
                                booking.getOtherAccount().setAccountNumber(transaction.getCounterpartAccountNumber());
                                booking.getOtherAccount().setIban(Utils.extractIban(transaction.getPurpose()));
                            }

                            if (transaction.getCategory() != null) {
                                BookingCategory bookingCategory = new BookingCategory();
                                bookingCategory.setMainCategory(transaction.getCategory().getParentName());
                                bookingCategory.setSubCategory(transaction.getCategory().getName());
                                booking.setBookingCategory(bookingCategory);
                            }

                            return booking;
                        }
                ).collect(Collectors.toList()));
            }
            LOG.info("loaded [{}] bookings for account [{}]", bookingList.size(), bankAccount.getAccountNumber());

            return LoadBookingsResponse.builder()
                    .bankAccountBalance(new BalancesReport().readyBalance(Balance.builder().amount(account.getBalance()).build()))
                    .bookings(bookingList)
                    .build();
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    private Account waitAccountSynced(BankAccount bankAccount, ApiClient apiClient) throws ApiException {
        Account account =
                new AccountsApi(apiClient).getAccount(Long.parseLong(bankAccount.getExternalIdMap().get(bankApi())));
        while (account.getStatus() == Account.StatusEnum.DOWNLOAD_IN_PROGRESS) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            account =
                    new AccountsApi(apiClient).getAccount(Long.parseLong(bankAccount.getExternalIdMap().get(bankApi())));
        }
        return account;
    }

    private void waitBookingsCategorized(BankAccess bankAccess, ApiClient apiClient) throws ApiException {
        BankConnection connection =
                new BankConnectionsApi(apiClient).getBankConnection(Long.parseLong(bankAccess.getExternalIdMap().get(bankApi())));
        while (connection.getCategorizationStatus() != BankConnection.CategorizationStatusEnum.READY) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            connection =
                    new BankConnectionsApi(apiClient).getBankConnection(Long.parseLong(bankAccess.getExternalIdMap().get(bankApi())));
        }
    }

    @Override
    public boolean bookingsCategorized() {
        return true;
    }

    @Override
    public AuthorisationCodeResponse requestAuthorizationCode(String bankingUrl, TransactionRequest paymentRequest) {
        return null;
    }

    @Override
    public SubmitAuthorizationCodeResponse submitAuthorizationCode(SubmitAuthorizationCodeRequest submitPaymentRequest) {
        return null;
    }

    @Override
    public StrongCustomerAuthorisable getStrongCustomerAuthorisation() {
        return null;
    }

    private ApiClient createApiClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(finapiConnectionUrl);
        apiClient.setAccessToken(clientToken != null ? clientToken.getAccessToken() : null);
        return apiClient;
    }

    private ApiClient createUserApiClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(finapiConnectionUrl);
        return apiClient;
    }

    private String authorizeUser(BankApiUser bankApiUser) {
        try {
            return new AuthorizationApi(createApiClient()).getToken("password", finapiClientId, finapiSecret, null,
                    bankApiUser.getApiUserId(), bankApiUser.getApiPassword()).getAccessToken();
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void authorizeClient() {
        try {
            clientToken = new AuthorizationApi(createApiClient()).getToken("client_credentials", finapiClientId,
                    finapiSecret, null, null, null);
        } catch (ApiException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
