package de.adorsys.multibanking.domain;

import de.adorsys.multibanking.encrypt.Encrypted;
import domain.BankAccount;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by alexg on 07.02.17.
 */
@Data
@Document
@Encrypted(exclude = {"_id", "bankAccessId", "userId", "syncStatus"})
@CompoundIndexes({
        @CompoundIndex(name = "account_index", def = "{'userId': 1, 'bankAccessId': 1}")
})
public class BankAccountEntity extends BankAccount {

    @Id
    private String id;
    private String bankAccessId;
    private String userId;

    public String getId() {
        return id;
    }

    public BankAccountEntity id(String id) {
        this.id = id;
        return this;
    }

    public BankAccountEntity bankAccessId(String bankAccessId) {
        this.bankAccessId = bankAccessId;
        return this;
    }
}
