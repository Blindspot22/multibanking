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
import java.math.BigDecimal;

/**
 * Container for a payment&#39;s data
 */
@ApiModel(description = "Container for a payment's data")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-02-05T12:19:21.458Z")
public class Payment {
  @SerializedName("id")
  private Long id = null;

  @SerializedName("accountId")
  private Long accountId = null;

  @SerializedName("requestDate")
  private String requestDate = null;

  @SerializedName("executionDate")
  private String executionDate = null;

  /**
   * Payment type
   */
  @JsonAdapter(TypeEnum.Adapter.class)
  public enum TypeEnum {
    MONEY_TRANSFER("MONEY_TRANSFER"),
    
    DIRECT_DEBIT("DIRECT_DEBIT");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static TypeEnum fromValue(String text) {
      for (TypeEnum b : TypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<TypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final TypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public TypeEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return TypeEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("type")
  private TypeEnum type = null;

  /**
   * Current payment status:&lt;br/&gt; &amp;bull; PENDING: means that this payment has been requested, but not yet executed.&lt;br/&gt; &amp;bull; SUCCESSFUL: means that this payment has been successfully executed.&lt;br/&gt; &amp;bull; NOT_SUCCESSFUL: means that this payment could not be executed successfully.&lt;br/&gt; &amp;bull; DISCARDED: means that this payment was discarded because another payment was requested for the same account before this payment was executed.
   */
  @JsonAdapter(StatusEnum.Adapter.class)
  public enum StatusEnum {
    PENDING("PENDING"),
    
    SUCCESSFUL("SUCCESSFUL"),
    
    NOT_SUCCESSFUL("NOT_SUCCESSFUL"),
    
    DISCARDED("DISCARDED");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static StatusEnum fromValue(String text) {
      for (StatusEnum b : StatusEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<StatusEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final StatusEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public StatusEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return StatusEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("status")
  private StatusEnum status = null;

  @SerializedName("bankMessage")
  private String bankMessage = null;

  @SerializedName("amount")
  private BigDecimal amount = null;

  @SerializedName("orderCount")
  private Integer orderCount = null;

  public Payment id(Long id) {
    this.id = id;
    return this;
  }

   /**
   * Payment identifier
   * @return id
  **/
  @ApiModelProperty(example = "1", required = true, value = "Payment identifier")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Payment accountId(Long accountId) {
    this.accountId = accountId;
    return this;
  }

   /**
   * Identifier of the account to which this payment relates
   * @return accountId
  **/
  @ApiModelProperty(example = "1", required = true, value = "Identifier of the account to which this payment relates")
  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public Payment requestDate(String requestDate) {
    this.requestDate = requestDate;
    return this;
  }

   /**
   * Time of when this payment was requested, in the format &#39;YYYY-MM-DD HH:MM:SS.SSS&#39; (german time)
   * @return requestDate
  **/
  @ApiModelProperty(example = "2019-01-01 00:00:00.000", required = true, value = "Time of when this payment was requested, in the format 'YYYY-MM-DD HH:MM:SS.SSS' (german time)")
  public String getRequestDate() {
    return requestDate;
  }

  public void setRequestDate(String requestDate) {
    this.requestDate = requestDate;
  }

  public Payment executionDate(String executionDate) {
    this.executionDate = executionDate;
    return this;
  }

   /**
   * Time of when this payment was executed, in the format &#39;YYYY-MM-DD HH:MM:SS.SSS&#39; (german time)
   * @return executionDate
  **/
  @ApiModelProperty(example = "2019-01-01 00:00:00.000", value = "Time of when this payment was executed, in the format 'YYYY-MM-DD HH:MM:SS.SSS' (german time)")
  public String getExecutionDate() {
    return executionDate;
  }

  public void setExecutionDate(String executionDate) {
    this.executionDate = executionDate;
  }

  public Payment type(TypeEnum type) {
    this.type = type;
    return this;
  }

   /**
   * Payment type
   * @return type
  **/
  @ApiModelProperty(example = "MONEY_TRANSFER", required = true, value = "Payment type")
  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public Payment status(StatusEnum status) {
    this.status = status;
    return this;
  }

   /**
   * Current payment status:&lt;br/&gt; &amp;bull; PENDING: means that this payment has been requested, but not yet executed.&lt;br/&gt; &amp;bull; SUCCESSFUL: means that this payment has been successfully executed.&lt;br/&gt; &amp;bull; NOT_SUCCESSFUL: means that this payment could not be executed successfully.&lt;br/&gt; &amp;bull; DISCARDED: means that this payment was discarded because another payment was requested for the same account before this payment was executed.
   * @return status
  **/
  @ApiModelProperty(example = "PENDING", required = true, value = "Current payment status:<br/> &bull; PENDING: means that this payment has been requested, but not yet executed.<br/> &bull; SUCCESSFUL: means that this payment has been successfully executed.<br/> &bull; NOT_SUCCESSFUL: means that this payment could not be executed successfully.<br/> &bull; DISCARDED: means that this payment was discarded because another payment was requested for the same account before this payment was executed.")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public Payment bankMessage(String bankMessage) {
    this.bankMessage = bankMessage;
    return this;
  }

   /**
   * Contains the bank&#39;s response to the execution of this payment. This field is not set until the payment gets executed. Note that even after the execution of the payment, the field might still not be set, if the bank did not send any response message.
   * @return bankMessage
  **/
  @ApiModelProperty(value = "Contains the bank's response to the execution of this payment. This field is not set until the payment gets executed. Note that even after the execution of the payment, the field might still not be set, if the bank did not send any response message.")
  public String getBankMessage() {
    return bankMessage;
  }

  public void setBankMessage(String bankMessage) {
    this.bankMessage = bankMessage;
  }

  public Payment amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

   /**
   * Total money amount of the payment order(s), as absolute value
   * @return amount
  **/
  @ApiModelProperty(example = "99.99", required = true, value = "Total money amount of the payment order(s), as absolute value")
  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Payment orderCount(Integer orderCount) {
    this.orderCount = orderCount;
    return this;
  }

   /**
   * Total count of orders included in this payment
   * @return orderCount
  **/
  @ApiModelProperty(example = "1", required = true, value = "Total count of orders included in this payment")
  public Integer getOrderCount() {
    return orderCount;
  }

  public void setOrderCount(Integer orderCount) {
    this.orderCount = orderCount;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Payment payment = (Payment) o;
    return Objects.equals(this.id, payment.id) &&
        Objects.equals(this.accountId, payment.accountId) &&
        Objects.equals(this.requestDate, payment.requestDate) &&
        Objects.equals(this.executionDate, payment.executionDate) &&
        Objects.equals(this.type, payment.type) &&
        Objects.equals(this.status, payment.status) &&
        Objects.equals(this.bankMessage, payment.bankMessage) &&
        Objects.equals(this.amount, payment.amount) &&
        Objects.equals(this.orderCount, payment.orderCount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, accountId, requestDate, executionDate, type, status, bankMessage, amount, orderCount);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Payment {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    accountId: ").append(toIndentedString(accountId)).append("\n");
    sb.append("    requestDate: ").append(toIndentedString(requestDate)).append("\n");
    sb.append("    executionDate: ").append(toIndentedString(executionDate)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    bankMessage: ").append(toIndentedString(bankMessage)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    orderCount: ").append(toIndentedString(orderCount)).append("\n");
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
