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

@Schema(name = "EmployeeUpdate", description = "Employee")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class EmployeeUpdate {

  private String fullName;

  private Long employeeGroupId = null;

  public EmployeeUpdate fullName(String fullName) {
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

  public EmployeeUpdate employeeGroupId(Long employeeGroupId) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EmployeeUpdate employeeUpdate = (EmployeeUpdate) o;
    return Objects.equals(this.fullName, employeeUpdate.fullName) &&
        Objects.equals(this.employeeGroupId, employeeUpdate.employeeGroupId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fullName, employeeGroupId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EmployeeUpdate {\n");
    sb.append("    fullName: ").append(toIndentedString(fullName)).append("\n");
    sb.append("    employeeGroupId: ").append(toIndentedString(employeeGroupId)).append("\n");
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

