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
import io.swagger.client.model.Paging;
import io.swagger.client.model.Payment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Payment resources with paging information
 */
@ApiModel(description = "Payment resources with paging information")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-02-05T12:19:21.458Z")
public class PageablePaymentResources {
  @SerializedName("payments")
  private List<Payment> payments = new ArrayList<Payment>();

  @SerializedName("paging")
  private Paging paging = null;

  public PageablePaymentResources payments(List<Payment> payments) {
    this.payments = payments;
    return this;
  }

  public PageablePaymentResources addPaymentsItem(Payment paymentsItem) {
    this.payments.add(paymentsItem);
    return this;
  }

   /**
   * List of received account payments
   * @return payments
  **/
  @ApiModelProperty(required = true, value = "List of received account payments")
  public List<Payment> getPayments() {
    return payments;
  }

  public void setPayments(List<Payment> payments) {
    this.payments = payments;
  }

  public PageablePaymentResources paging(Paging paging) {
    this.paging = paging;
    return this;
  }

   /**
   * Information for pagination
   * @return paging
  **/
  @ApiModelProperty(required = true, value = "Information for pagination")
  public Paging getPaging() {
    return paging;
  }

  public void setPaging(Paging paging) {
    this.paging = paging;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PageablePaymentResources pageablePaymentResources = (PageablePaymentResources) o;
    return Objects.equals(this.payments, pageablePaymentResources.payments) &&
        Objects.equals(this.paging, pageablePaymentResources.paging);
  }

  @Override
  public int hashCode() {
    return Objects.hash(payments, paging);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PageablePaymentResources {\n");
    
    sb.append("    payments: ").append(toIndentedString(payments)).append("\n");
    sb.append("    paging: ").append(toIndentedString(paging)).append("\n");
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

