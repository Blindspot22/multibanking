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
import io.swagger.client.model.Category;
import java.io.IOException;

/**
 * CategorizationCheckResult
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-02-05T12:19:21.458Z")
public class CategorizationCheckResult {
  @SerializedName("transactionId")
  private String transactionId = null;

  @SerializedName("category")
  private Category category = null;

  public CategorizationCheckResult transactionId(String transactionId) {
    this.transactionId = transactionId;
    return this;
  }

   /**
   * Transaction identifier
   * @return transactionId
  **/
  @ApiModelProperty(example = "transaction", required = true, value = "Transaction identifier")
  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public CategorizationCheckResult category(Category category) {
    this.category = category;
    return this;
  }

   /**
   * Category
   * @return category
  **/
  @ApiModelProperty(value = "Category")
  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CategorizationCheckResult categorizationCheckResult = (CategorizationCheckResult) o;
    return Objects.equals(this.transactionId, categorizationCheckResult.transactionId) &&
        Objects.equals(this.category, categorizationCheckResult.category);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionId, category);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CategorizationCheckResult {\n");
    
    sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
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

