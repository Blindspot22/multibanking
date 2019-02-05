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
 * Execute password change parameters
 */
@ApiModel(description = "Execute password change parameters")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-02-05T12:19:21.458Z")
public class ExecutePasswordChangeParams {
  @SerializedName("userId")
  private String userId = null;

  @SerializedName("password")
  private String password = null;

  @SerializedName("passwordChangeToken")
  private String passwordChangeToken = null;

  public ExecutePasswordChangeParams userId(String userId) {
    this.userId = userId;
    return this;
  }

   /**
   * User identifier
   * @return userId
  **/
  @ApiModelProperty(example = "username", required = true, value = "User identifier")
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public ExecutePasswordChangeParams password(String password) {
    this.password = password;
    return this;
  }

   /**
   * User&#39;s new password. Minimum length is 6, and maximum length is 36.
   * @return password
  **/
  @ApiModelProperty(example = "password", required = true, value = "User's new password. Minimum length is 6, and maximum length is 36.")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public ExecutePasswordChangeParams passwordChangeToken(String passwordChangeToken) {
    this.passwordChangeToken = passwordChangeToken;
    return this;
  }

   /**
   * Decrypted password change token (the token that you received from the /users/requestPasswordChange service, decrypted with your data decryption key)
   * @return passwordChangeToken
  **/
  @ApiModelProperty(example = "PassWordCHAnGEToKen", required = true, value = "Decrypted password change token (the token that you received from the /users/requestPasswordChange service, decrypted with your data decryption key)")
  public String getPasswordChangeToken() {
    return passwordChangeToken;
  }

  public void setPasswordChangeToken(String passwordChangeToken) {
    this.passwordChangeToken = passwordChangeToken;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExecutePasswordChangeParams executePasswordChangeParams = (ExecutePasswordChangeParams) o;
    return Objects.equals(this.userId, executePasswordChangeParams.userId) &&
        Objects.equals(this.password, executePasswordChangeParams.password) &&
        Objects.equals(this.passwordChangeToken, executePasswordChangeParams.passwordChangeToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, password, passwordChangeToken);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExecutePasswordChangeParams {\n");
    
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    passwordChangeToken: ").append(toIndentedString(passwordChangeToken)).append("\n");
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

