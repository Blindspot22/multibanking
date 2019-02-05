/*
 * finAPI RESTful Services
 * finAPI RESTful Services
 *
 * OpenAPI spec version: v1.64.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package io.swagger.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.client.model.NewTransaction;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Mock account data
 */
@ApiModel(description = "Mock account data")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-02-05T12:19:21.458Z")
public class MockAccountData {
  @SerializedName("accountId")
  private Long accountId = null;

  @SerializedName("accountBalance")
  private BigDecimal accountBalance = null;

  @SerializedName("newTransactions")
  private List<NewTransaction> newTransactions = null;

  public MockAccountData accountId(Long accountId) {
    this.accountId = accountId;
    return this;
  }

   /**
   * Account identifier
   * @return accountId
  **/
  @ApiModelProperty(example = "1", required = true, value = "Account identifier")
  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public MockAccountData accountBalance(BigDecimal accountBalance) {
    this.accountBalance = accountBalance;
    return this;
  }

   /**
   * The balance that this account should be set to. Note that when the balance does not add up to the current balance plus the sum of the transactions you pass in the &#39;newTransactions&#39; field, finAPI will fix the balance deviation with the insertion of an adjusting entry (&#39;Zwischensaldo&#39; transaction).
   * @return accountBalance
  **/
  @ApiModelProperty(example = "99.99", required = true, value = "The balance that this account should be set to. Note that when the balance does not add up to the current balance plus the sum of the transactions you pass in the 'newTransactions' field, finAPI will fix the balance deviation with the insertion of an adjusting entry ('Zwischensaldo' transaction).")
  public BigDecimal getAccountBalance() {
    return accountBalance;
  }

  public void setAccountBalance(BigDecimal accountBalance) {
    this.accountBalance = accountBalance;
  }

  public MockAccountData newTransactions(List<NewTransaction> newTransactions) {
    this.newTransactions = newTransactions;
    return this;
  }

  public MockAccountData addNewTransactionsItem(NewTransaction newTransactionsItem) {
    if (this.newTransactions == null) {
      this.newTransactions = new ArrayList<NewTransaction>();
    }
    this.newTransactions.add(newTransactionsItem);
    return this;
  }

   /**
   * New transactions that should be imported into the account (maximum 1000 transactions at once). Please make sure that the value you pass in the &#39;accountBalance&#39; field equals the current account balance plus the sum of the new transactions that you pass here, otherwise finAPI will detect a deviation in the balance and fix it with the insertion of an adjusting entry (&#39;Zwischensaldo&#39; transaction). Please also note that it is not guaranteed that all transactions that you pass here will actually get imported. More specifically, finAPI will ignore any transactions whose booking date is prior to the booking date of the latest currently existing transactions minus 10 days (which is the &#39;update window&#39; that finAPI uses when importing new transactions). Also, finAPI will ignore any transactions that are considered duplicates of already existing transactions within the update window. This is the case for instance when you try to import a new transaction with the same booking date and same amount as an already existing transaction. In such cases, you might get an adjusting entry too (&#39;Zwischensaldo&#39; transaction), as your given balance might not add up to the transactions that will exist in the account after the update.
   * @return newTransactions
  **/
  @ApiModelProperty(value = "New transactions that should be imported into the account (maximum 1000 transactions at once). Please make sure that the value you pass in the 'accountBalance' field equals the current account balance plus the sum of the new transactions that you pass here, otherwise finAPI will detect a deviation in the balance and fix it with the insertion of an adjusting entry ('Zwischensaldo' transaction). Please also note that it is not guaranteed that all transactions that you pass here will actually get imported. More specifically, finAPI will ignore any transactions whose booking date is prior to the booking date of the latest currently existing transactions minus 10 days (which is the 'update window' that finAPI uses when importing new transactions). Also, finAPI will ignore any transactions that are considered duplicates of already existing transactions within the update window. This is the case for instance when you try to import a new transaction with the same booking date and same amount as an already existing transaction. In such cases, you might get an adjusting entry too ('Zwischensaldo' transaction), as your given balance might not add up to the transactions that will exist in the account after the update.")
  public List<NewTransaction> getNewTransactions() {
    return newTransactions;
  }

  public void setNewTransactions(List<NewTransaction> newTransactions) {
    this.newTransactions = newTransactions;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MockAccountData mockAccountData = (MockAccountData) o;
    return Objects.equals(this.accountId, mockAccountData.accountId) &&
        Objects.equals(this.accountBalance, mockAccountData.accountBalance) &&
        Objects.equals(this.newTransactions, mockAccountData.newTransactions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountId, accountBalance, newTransactions);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MockAccountData {\n");
    
    sb.append("    accountId: ").append(toIndentedString(accountId)).append("\n");
    sb.append("    accountBalance: ").append(toIndentedString(accountBalance)).append("\n");
    sb.append("    newTransactions: ").append(toIndentedString(newTransactions)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

