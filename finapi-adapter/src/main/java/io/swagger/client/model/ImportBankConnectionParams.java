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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Container for bank connection import parameters
 */
@ApiModel(description = "Container for bank connection import parameters")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-02-05T12:19:21.458Z")
public class ImportBankConnectionParams {
  @SerializedName("bankId")
  private Long bankId = null;

  @SerializedName("bankingUserId")
  private String bankingUserId = null;

  @SerializedName("bankingCustomerId")
  private String bankingCustomerId = null;

  @SerializedName("bankingPin")
  private String bankingPin = null;

  @SerializedName("storePin")
  private Boolean storePin = false;

  @SerializedName("name")
  private String name = null;

  @SerializedName("skipPositionsDownload")
  private Boolean skipPositionsDownload = false;

  @SerializedName("loadOwnerData")
  private Boolean loadOwnerData = false;

  @SerializedName("maxDaysForDownload")
  private Integer maxDaysForDownload = 0;

  @SerializedName("accountTypeIds")
  private List<Long> accountTypeIds = null;

  @SerializedName("challengeResponse")
  private String challengeResponse = null;

  public ImportBankConnectionParams bankId(Long bankId) {
    this.bankId = bankId;
    return this;
  }

   /**
   * Bank Identifier
   * @return bankId
  **/
  @ApiModelProperty(example = "277672", required = true, value = "Bank Identifier")
  public Long getBankId() {
    return bankId;
  }

  public void setBankId(Long bankId) {
    this.bankId = bankId;
  }

  public ImportBankConnectionParams bankingUserId(String bankingUserId) {
    this.bankingUserId = bankingUserId;
    return this;
  }

   /**
   * Online banking user ID credential. Max length: 64. NOTES:&lt;br/&gt;- if you import the &#39;demo connection&#39;, this field can be left unset;&lt;br/&gt; - if the user will need to enter his credentials in finAPI&#39;s web form, this field can contain any value. It will be ignored.
   * @return bankingUserId
  **/
  @ApiModelProperty(example = "123456", value = "Online banking user ID credential. Max length: 64. NOTES:<br/>- if you import the 'demo connection', this field can be left unset;<br/> - if the user will need to enter his credentials in finAPI's web form, this field can contain any value. It will be ignored.")
  public String getBankingUserId() {
    return bankingUserId;
  }

  public void setBankingUserId(String bankingUserId) {
    this.bankingUserId = bankingUserId;
  }

  public ImportBankConnectionParams bankingCustomerId(String bankingCustomerId) {
    this.bankingCustomerId = bankingCustomerId;
    return this;
  }

   /**
   * Online banking customer ID credential (for most banks this field can remain unset). Max length: 64. NOTES:&lt;br/&gt;- if the user will need to enter his credentials in finAPI&#39;s web form, this field can contain any value. It will be ignored.
   * @return bankingCustomerId
  **/
  @ApiModelProperty(example = "123456", value = "Online banking customer ID credential (for most banks this field can remain unset). Max length: 64. NOTES:<br/>- if the user will need to enter his credentials in finAPI's web form, this field can contain any value. It will be ignored.")
  public String getBankingCustomerId() {
    return bankingCustomerId;
  }

  public void setBankingCustomerId(String bankingCustomerId) {
    this.bankingCustomerId = bankingCustomerId;
  }

  public ImportBankConnectionParams bankingPin(String bankingPin) {
    this.bankingPin = bankingPin;
    return this;
  }

   /**
   * Online banking PIN. Max length: 170. Any symbols are allowed. NOTES:&lt;br/&gt;- if you import the &#39;demo connection&#39;, this field can be left unset;&lt;br/&gt; - if the user will need to enter his credentials in finAPI&#39;s web form, this field can be left unset or contain any value. It will be ignored.
   * @return bankingPin
  **/
  @ApiModelProperty(example = "123456", value = "Online banking PIN. Max length: 170. Any symbols are allowed. NOTES:<br/>- if you import the 'demo connection', this field can be left unset;<br/> - if the user will need to enter his credentials in finAPI's web form, this field can be left unset or contain any value. It will be ignored.")
  public String getBankingPin() {
    return bankingPin;
  }

  public void setBankingPin(String bankingPin) {
    this.bankingPin = bankingPin;
  }

  public ImportBankConnectionParams storePin(Boolean storePin) {
    this.storePin = storePin;
    return this;
  }

   /**
   * Whether to store the PIN. If the PIN is stored, it is not required to pass the PIN again when updating this bank connection or when executing orders (like money transfers). Default is false. &lt;br/&gt;&lt;br/&gt;NOTES:&lt;br/&gt; - before you set this field to true, please regard the &#39;pinsAreVolatile&#39; flag of this connection&#39;s bank;&lt;br/&gt; - this field is ignored in case when the user will need to use finAPI&#39;s web form. The user will be able to decide whether to store the PIN or not in the web form, depending on the &#39;pinStorageAvailableInWebForm&#39; setting (see Client Configuration).
   * @return storePin
  **/
  @ApiModelProperty(example = "true", value = "Whether to store the PIN. If the PIN is stored, it is not required to pass the PIN again when updating this bank connection or when executing orders (like money transfers). Default is false. <br/><br/>NOTES:<br/> - before you set this field to true, please regard the 'pinsAreVolatile' flag of this connection's bank;<br/> - this field is ignored in case when the user will need to use finAPI's web form. The user will be able to decide whether to store the PIN or not in the web form, depending on the 'pinStorageAvailableInWebForm' setting (see Client Configuration).")
  public Boolean isStorePin() {
    return storePin;
  }

  public void setStorePin(Boolean storePin) {
    this.storePin = storePin;
  }

  public ImportBankConnectionParams name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Custom name for the bank connection. Maximum length is 64. If you do not want to set a name, you can leave this field unset.
   * @return name
  **/
  @ApiModelProperty(example = "Bank connection", value = "Custom name for the bank connection. Maximum length is 64. If you do not want to set a name, you can leave this field unset.")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ImportBankConnectionParams skipPositionsDownload(Boolean skipPositionsDownload) {
    this.skipPositionsDownload = skipPositionsDownload;
    return this;
  }

   /**
   * Whether to skip the download of transactions and securities or not. If set to true, then finAPI will download just the accounts list with the accounts&#39; information (like account name, number, holder, etc), as well as the accounts&#39; balances (if possible), but skip the download of transactions and securities. Default is false.&lt;br/&gt;&lt;br/&gt;NOTES:&lt;br/&gt;&amp;bull; If you skip the download of transactions and securities during an import or update, you can still download them on a later update (though you might not get all positions at a later point, because the date range in which the bank servers provide this data is usually limited). However, once finAPI has downloaded the transactions or securities for the first time, you will not be able to go back to skipping the download of transactions and securities! In other words: Once you make your first request with skipPositionsDownload&#x3D;false for a certain bank connection, you will no longer be able to make a request with skipPositionsDownload&#x3D;true for that same bank connection.&lt;br/&gt;&amp;bull; If this bank connection is updated via finAPI&#39;s automatic batch update, then transactions and security positions &lt;u&gt;will&lt;/u&gt; be downloaded in any case!&lt;br/&gt;&amp;bull; For security accounts, skipping the downloading of the securities might result in the account&#39;s balance also not being downloaded.&lt;br/&gt;&amp;bull; For Bausparen accounts, this field is ignored. finAPI will always download transactions for Bausparen accounts.&lt;br/&gt;&lt;br/&gt;&lt;b&gt;This flag is currently not guaranteed to work for all banks!&lt;/b&gt;
   * @return skipPositionsDownload
  **/
  @ApiModelProperty(example = "false", value = "Whether to skip the download of transactions and securities or not. If set to true, then finAPI will download just the accounts list with the accounts' information (like account name, number, holder, etc), as well as the accounts' balances (if possible), but skip the download of transactions and securities. Default is false.<br/><br/>NOTES:<br/>&bull; If you skip the download of transactions and securities during an import or update, you can still download them on a later update (though you might not get all positions at a later point, because the date range in which the bank servers provide this data is usually limited). However, once finAPI has downloaded the transactions or securities for the first time, you will not be able to go back to skipping the download of transactions and securities! In other words: Once you make your first request with skipPositionsDownload=false for a certain bank connection, you will no longer be able to make a request with skipPositionsDownload=true for that same bank connection.<br/>&bull; If this bank connection is updated via finAPI's automatic batch update, then transactions and security positions <u>will</u> be downloaded in any case!<br/>&bull; For security accounts, skipping the downloading of the securities might result in the account's balance also not being downloaded.<br/>&bull; For Bausparen accounts, this field is ignored. finAPI will always download transactions for Bausparen accounts.<br/><br/><b>This flag is currently not guaranteed to work for all banks!</b>")
  public Boolean isSkipPositionsDownload() {
    return skipPositionsDownload;
  }

  public void setSkipPositionsDownload(Boolean skipPositionsDownload) {
    this.skipPositionsDownload = skipPositionsDownload;
  }

  public ImportBankConnectionParams loadOwnerData(Boolean loadOwnerData) {
    this.loadOwnerData = loadOwnerData;
    return this;
  }

   /**
   * Whether to load information about the bank connection owner(s) - see field &#39;owners&#39;. Default value is &#39;false&#39;.
   * @return loadOwnerData
  **/
  @ApiModelProperty(example = "false", value = "Whether to load information about the bank connection owner(s) - see field 'owners'. Default value is 'false'.")
  public Boolean isLoadOwnerData() {
    return loadOwnerData;
  }

  public void setLoadOwnerData(Boolean loadOwnerData) {
    this.loadOwnerData = loadOwnerData;
  }

  public ImportBankConnectionParams maxDaysForDownload(Integer maxDaysForDownload) {
    this.maxDaysForDownload = maxDaysForDownload;
    return this;
  }

   /**
   * Use this parameter if you want to limit the date range for transactions download. The value depicts the number of days that finAPI will download transactions for, starting from - and including - today. For example, if you want to download only transactions from within the past 30 days (including today), then pass the value 30. The minimum allowed value is 14, the maximum value is 3650. You may also pass the value 0 though (which is also the default value when you do not specify this parameter), in which case there will be no limit to the transactions download and finAPI will try to get all transactions that it can. Please note that when you specify the parameter there is no guarantee that finAPI will actually download transactions for the entire given date range, as the bank servers may limit the date range on their own. Also note that this parameter only applies to transactions, not to security positions; finAPI will always download all positions that it can get.&lt;br/&gt;&lt;br/&gt;&lt;b&gt;This flag is currently not guaranteed to work for all banks!&lt;/b&gt;
   * @return maxDaysForDownload
  **/
  @ApiModelProperty(example = "365", value = "Use this parameter if you want to limit the date range for transactions download. The value depicts the number of days that finAPI will download transactions for, starting from - and including - today. For example, if you want to download only transactions from within the past 30 days (including today), then pass the value 30. The minimum allowed value is 14, the maximum value is 3650. You may also pass the value 0 though (which is also the default value when you do not specify this parameter), in which case there will be no limit to the transactions download and finAPI will try to get all transactions that it can. Please note that when you specify the parameter there is no guarantee that finAPI will actually download transactions for the entire given date range, as the bank servers may limit the date range on their own. Also note that this parameter only applies to transactions, not to security positions; finAPI will always download all positions that it can get.<br/><br/><b>This flag is currently not guaranteed to work for all banks!</b>")
  public Integer getMaxDaysForDownload() {
    return maxDaysForDownload;
  }

  public void setMaxDaysForDownload(Integer maxDaysForDownload) {
    this.maxDaysForDownload = maxDaysForDownload;
  }

  public ImportBankConnectionParams accountTypeIds(List<Long> accountTypeIds) {
    this.accountTypeIds = accountTypeIds;
    return this;
  }

  public ImportBankConnectionParams addAccountTypeIdsItem(Long accountTypeIdsItem) {
    if (this.accountTypeIds == null) {
      this.accountTypeIds = new ArrayList<Long>();
    }
    this.accountTypeIds.add(accountTypeIdsItem);
    return this;
  }

   /**
   * Whitelist of identifiers of finAPI account types that are considered for the import. Only accounts whose type matches with one of the given types will be imported. Note that when the bank connection does not contain any accounts of the given types, the import will fail with error code NO_ACCOUNTS_FOR_TYPE_LIST. If no whitelist is given, then all accounts will be imported.&lt;br/&gt;&lt;br/&gt;&lt;br/&gt;1 &#x3D; Checking,&lt;br/&gt;2 &#x3D; Savings,&lt;br/&gt;3 &#x3D; CreditCard,&lt;br/&gt;4 &#x3D; Security,&lt;br/&gt;5 &#x3D; Loan,&lt;br/&gt;6 &#x3D; Pocket (DEPRECATED; will not be returned for any account unless this type has explicitly been set via PATCH),&lt;br/&gt;7 &#x3D; Membership,&lt;br/&gt;8 &#x3D; Bausparen&lt;br/&gt;&lt;br/&gt;&lt;b&gt;This flag is currently not guaranteed to work for all banks!&lt;/b&gt;
   * @return accountTypeIds
  **/
  @ApiModelProperty(example = "[1,2,3,4,5,6,7,8]", value = "Whitelist of identifiers of finAPI account types that are considered for the import. Only accounts whose type matches with one of the given types will be imported. Note that when the bank connection does not contain any accounts of the given types, the import will fail with error code NO_ACCOUNTS_FOR_TYPE_LIST. If no whitelist is given, then all accounts will be imported.<br/><br/><br/>1 = Checking,<br/>2 = Savings,<br/>3 = CreditCard,<br/>4 = Security,<br/>5 = Loan,<br/>6 = Pocket (DEPRECATED; will not be returned for any account unless this type has explicitly been set via PATCH),<br/>7 = Membership,<br/>8 = Bausparen<br/><br/><b>This flag is currently not guaranteed to work for all banks!</b>")
  public List<Long> getAccountTypeIds() {
    return accountTypeIds;
  }

  public void setAccountTypeIds(List<Long> accountTypeIds) {
    this.accountTypeIds = accountTypeIds;
  }

  public ImportBankConnectionParams challengeResponse(String challengeResponse) {
    this.challengeResponse = challengeResponse;
    return this;
  }

   /**
   * Challenge response. This field should be set only when the previous attempt of importing the bank connection failed with HTTP code 510, i.e. the bank sent a challenge for the user for an additional authentication. In this case, this field must contain the response to the bank&#39;s challenge. Note that in the context of finAPI&#39;s web form flow, finAPI will automatically deal with getting the challenge response from the user via the web form.
   * @return challengeResponse
  **/
  @ApiModelProperty(example = "0123", value = "Challenge response. This field should be set only when the previous attempt of importing the bank connection failed with HTTP code 510, i.e. the bank sent a challenge for the user for an additional authentication. In this case, this field must contain the response to the bank's challenge. Note that in the context of finAPI's web form flow, finAPI will automatically deal with getting the challenge response from the user via the web form.")
  public String getChallengeResponse() {
    return challengeResponse;
  }

  public void setChallengeResponse(String challengeResponse) {
    this.challengeResponse = challengeResponse;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ImportBankConnectionParams importBankConnectionParams = (ImportBankConnectionParams) o;
    return Objects.equals(this.bankId, importBankConnectionParams.bankId) &&
        Objects.equals(this.bankingUserId, importBankConnectionParams.bankingUserId) &&
        Objects.equals(this.bankingCustomerId, importBankConnectionParams.bankingCustomerId) &&
        Objects.equals(this.bankingPin, importBankConnectionParams.bankingPin) &&
        Objects.equals(this.storePin, importBankConnectionParams.storePin) &&
        Objects.equals(this.name, importBankConnectionParams.name) &&
        Objects.equals(this.skipPositionsDownload, importBankConnectionParams.skipPositionsDownload) &&
        Objects.equals(this.loadOwnerData, importBankConnectionParams.loadOwnerData) &&
        Objects.equals(this.maxDaysForDownload, importBankConnectionParams.maxDaysForDownload) &&
        Objects.equals(this.accountTypeIds, importBankConnectionParams.accountTypeIds) &&
        Objects.equals(this.challengeResponse, importBankConnectionParams.challengeResponse);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bankId, bankingUserId, bankingCustomerId, bankingPin, storePin, name, skipPositionsDownload, loadOwnerData, maxDaysForDownload, accountTypeIds, challengeResponse);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ImportBankConnectionParams {\n");
    
    sb.append("    bankId: ").append(toIndentedString(bankId)).append("\n");
    sb.append("    bankingUserId: ").append(toIndentedString(bankingUserId)).append("\n");
    sb.append("    bankingCustomerId: ").append(toIndentedString(bankingCustomerId)).append("\n");
    sb.append("    bankingPin: ").append(toIndentedString(bankingPin)).append("\n");
    sb.append("    storePin: ").append(toIndentedString(storePin)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    skipPositionsDownload: ").append(toIndentedString(skipPositionsDownload)).append("\n");
    sb.append("    loadOwnerData: ").append(toIndentedString(loadOwnerData)).append("\n");
    sb.append("    maxDaysForDownload: ").append(toIndentedString(maxDaysForDownload)).append("\n");
    sb.append("    accountTypeIds: ").append(toIndentedString(accountTypeIds)).append("\n");
    sb.append("    challengeResponse: ").append(toIndentedString(challengeResponse)).append("\n");
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

