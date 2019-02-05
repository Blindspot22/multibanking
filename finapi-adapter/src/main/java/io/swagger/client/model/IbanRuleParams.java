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

/**
 * Parameters of IBAN rule
 */
@ApiModel(description = "Parameters of IBAN rule")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-02-05T12:19:21.458Z")
public class IbanRuleParams {
  @SerializedName("categoryId")
  private Long categoryId = null;

  /**
   * Direction for the rule. &#39;Income&#39; means that the rule applies to transactions with a positive amount only, &#39;Spending&#39; means it applies to transactions with a negative amount only. &#39;Both&#39; means that it applies to both kind of transactions. Note that in case of &#39;Both&#39;, finAPI will create two individual rules (one with direction &#39;Income&#39; and one with direction &#39;Spending&#39;).
   */
  @JsonAdapter(DirectionEnum.Adapter.class)
  public enum DirectionEnum {
    INCOME("Income"),
    
    SPENDING("Spending"),
    
    BOTH("Both");

    private String value;

    DirectionEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static DirectionEnum fromValue(String text) {
      for (DirectionEnum b : DirectionEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<DirectionEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final DirectionEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public DirectionEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return DirectionEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("direction")
  private DirectionEnum direction = null;

  @SerializedName("iban")
  private String iban = null;

  public IbanRuleParams categoryId(Long categoryId) {
    this.categoryId = categoryId;
    return this;
  }

   /**
   * ID of the category that this rule should assign to the matching transactions
   * @return categoryId
  **/
  @ApiModelProperty(example = "378", required = true, value = "ID of the category that this rule should assign to the matching transactions")
  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public IbanRuleParams direction(DirectionEnum direction) {
    this.direction = direction;
    return this;
  }

   /**
   * Direction for the rule. &#39;Income&#39; means that the rule applies to transactions with a positive amount only, &#39;Spending&#39; means it applies to transactions with a negative amount only. &#39;Both&#39; means that it applies to both kind of transactions. Note that in case of &#39;Both&#39;, finAPI will create two individual rules (one with direction &#39;Income&#39; and one with direction &#39;Spending&#39;).
   * @return direction
  **/
  @ApiModelProperty(example = "Spending", required = true, value = "Direction for the rule. 'Income' means that the rule applies to transactions with a positive amount only, 'Spending' means it applies to transactions with a negative amount only. 'Both' means that it applies to both kind of transactions. Note that in case of 'Both', finAPI will create two individual rules (one with direction 'Income' and one with direction 'Spending').")
  public DirectionEnum getDirection() {
    return direction;
  }

  public void setDirection(DirectionEnum direction) {
    this.direction = direction;
  }

  public IbanRuleParams iban(String iban) {
    this.iban = iban;
    return this;
  }

   /**
   * IBAN (case-insensitive)
   * @return iban
  **/
  @ApiModelProperty(example = "DE89370400440532013000", required = true, value = "IBAN (case-insensitive)")
  public String getIban() {
    return iban;
  }

  public void setIban(String iban) {
    this.iban = iban;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IbanRuleParams ibanRuleParams = (IbanRuleParams) o;
    return Objects.equals(this.categoryId, ibanRuleParams.categoryId) &&
        Objects.equals(this.direction, ibanRuleParams.direction) &&
        Objects.equals(this.iban, ibanRuleParams.iban);
  }

  @Override
  public int hashCode() {
    return Objects.hash(categoryId, direction, iban);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IbanRuleParams {\n");
    
    sb.append("    categoryId: ").append(toIndentedString(categoryId)).append("\n");
    sb.append("    direction: ").append(toIndentedString(direction)).append("\n");
    sb.append("    iban: ").append(toIndentedString(iban)).append("\n");
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

