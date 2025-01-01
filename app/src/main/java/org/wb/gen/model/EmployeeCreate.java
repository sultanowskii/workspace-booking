package org.wb.gen.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Employee
 */

@Schema(name = "EmployeeCreate", description = "Employee")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-01T12:50:04.846966322+03:00[Europe/Moscow]", comments = "Generator version: 7.10.0")
public class EmployeeCreate {

  private String username;

  private String password;

  private String fullName;

  public EmployeeCreate username(String username) {
    this.username = username;
    return this;
  }

  /**
   * Username
   * @return username
   */
  @Size(min = 3, max = 32) 
  @Schema(name = "username", description = "Username", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("username")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public EmployeeCreate password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Password
   * @return password
   */
  @Size(min = 8, max = 64) 
  @Schema(name = "password", description = "Password", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public EmployeeCreate fullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  /**
   * Full name
   * @return fullName
   */
  @Size(min = 1) 
  @Schema(name = "fullName", description = "Full name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fullName")
  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EmployeeCreate employeeCreate = (EmployeeCreate) o;
    return Objects.equals(this.username, employeeCreate.username) &&
        Objects.equals(this.password, employeeCreate.password) &&
        Objects.equals(this.fullName, employeeCreate.fullName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, password, fullName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EmployeeCreate {\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    fullName: ").append(toIndentedString(fullName)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

