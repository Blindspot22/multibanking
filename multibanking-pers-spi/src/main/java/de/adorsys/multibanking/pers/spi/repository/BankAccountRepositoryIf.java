package de.adorsys.multibanking.pers.spi.repository;

import de.adorsys.multibanking.domain.BankAccount;
import de.adorsys.multibanking.domain.BankAccountEntity;

import java.util.List;
import java.util.Optional;

/**
 * @author alexg on 07.02.17
 * @author fpo on 21.05.2017
 */
public interface BankAccountRepositoryIf {

    List<BankAccountEntity> findByUserId(String userId);

    List<BankAccountEntity> findByUserIdAndBankAccessId(String userId, String bankAccessId);

    Optional<BankAccountEntity> findByUserIdAndId(String userId, String id);

    boolean exists(String accountId);

    List<BankAccountEntity> save(List<BankAccountEntity> bankAccounts);

    void save(BankAccountEntity bankAccount);

    BankAccount.SyncStatus getSyncStatus(String accountId);

    void updateSyncStatus(String accountId, BankAccount.SyncStatus syncStatus);

    List<BankAccountEntity> deleteByBankAccess(String accessId);

    Optional<BankAccountEntity> findOne(String accountId);
}
