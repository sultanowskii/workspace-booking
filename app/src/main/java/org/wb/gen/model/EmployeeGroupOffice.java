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
 * Employee group office
 */

@Schema(name = "EmployeeGroupOffice", description = "Employee group office")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class EmployeeGroupOffice {

  private Object employeeGroupId;

  private Object officeId;

  public EmployeeGroupOffice employeeGroupId(Object employeeGroupId) {
    this.employeeGroupId = employeeGroupId;
    return this;
  }

  /**
   * Employee group ID
   * @return employeeGroupId
   */
  
  @Schema(name = "employeeGroupId", description = "Employee group ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("employeeGroupId")
  public Object getEmployeeGroupId() {
    return employeeGroupId;
  }

  public void setEmployeeGroupId(Object employeeGroupId) {
    this.employeeGroupId = employeeGroupId;
  }

  public EmployeeGroupOffice officeId(Object officeId) {
    this.officeId = officeId;
    return this;
  }

  /**
   * Office ID
   * @return officeId
   */
  
  @Schema(name = "officeId", description = "Office ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("officeId")
  public Object getOfficeId() {
    return officeId;
  }

  public void setOfficeId(Object officeId) {
    this.officeId = officeId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EmployeeGroupOffice employeeGroupOffice = (EmployeeGroupOffice) o;
    return Objects.equals(this.employeeGroupId, employeeGroupOffice.employeeGroupId) &&
        Objects.equals(this.officeId, employeeGroupOffice.officeId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(employeeGroupId, officeId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EmployeeGroupOffice {\n");
    sb.append("    employeeGroupId: ").append(toIndentedString(employeeGroupId)).append("\n");
    sb.append("    officeId: ").append(toIndentedString(officeId)).append("\n");
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

