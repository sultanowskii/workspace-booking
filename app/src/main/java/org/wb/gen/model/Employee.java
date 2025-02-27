package org.wb.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Employee
 */

@Schema(name = "Employee", description = "Employee")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class Employee {

  private Long id;

  private String username;

  private Long employeeGroupId;

  private String fullName;

  public Employee id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * ID
   * @return id
   */
  
  @Schema(name = "id", description = "ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Employee username(String username) {
    this.username = username;
    return this;
  }

  /**
   * Username
   * @return username
   */
  
  @Schema(name = "username", description = "Username", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("username")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Employee employeeGroupId(Long employeeGroupId) {
    this.employeeGroupId = employeeGroupId;
    return this;
  }

  /**
   * Employee Group ID
   * @return employeeGroupId
   */
  
  @Schema(name = "employeeGroupId", description = "Employee Group ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("employeeGroupId")
  public Long getEmployeeGroupId() {
    return employeeGroupId;
  }

  public void setEmployeeGroupId(Long employeeGroupId) {
    this.employeeGroupId = employeeGroupId;
  }

  public Employee fullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  /**
   * Full name
   * @return fullName
   */
  
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
    Employee employee = (Employee) o;
    return Objects.equals(this.id, employee.id) &&
        Objects.equals(this.username, employee.username) &&
        Objects.equals(this.employeeGroupId, employee.employeeGroupId) &&
        Objects.equals(this.fullName, employee.fullName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, employeeGroupId, fullName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Employee {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    employeeGroupId: ").append(toIndentedString(employeeGroupId)).append("\n");
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

