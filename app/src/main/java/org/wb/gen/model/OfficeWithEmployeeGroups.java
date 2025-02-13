package org.wb.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Office
 */

@Schema(name = "OfficeWithEmployeeGroups", description = "Office")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class OfficeWithEmployeeGroups {

  private Long id;

  private String name;

  private String address;

  @Valid
  private List<@Valid EmployeeGroup> employeeGroups = new ArrayList<>();

  public OfficeWithEmployeeGroups id(Long id) {
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

  public OfficeWithEmployeeGroups name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Name
   * @return name
   */
  
  @Schema(name = "name", description = "Name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public OfficeWithEmployeeGroups address(String address) {
    this.address = address;
    return this;
  }

  /**
   * Address
   * @return address
   */
  
  @Schema(name = "address", description = "Address", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("address")
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public OfficeWithEmployeeGroups employeeGroups(List<@Valid EmployeeGroup> employeeGroups) {
    this.employeeGroups = employeeGroups;
    return this;
  }

  public OfficeWithEmployeeGroups addEmployeeGroupsItem(EmployeeGroup employeeGroupsItem) {
    if (this.employeeGroups == null) {
      this.employeeGroups = new ArrayList<>();
    }
    this.employeeGroups.add(employeeGroupsItem);
    return this;
  }

  /**
   * Employee groups
   * @return employeeGroups
   */
  @Valid 
  @Schema(name = "employeeGroups", description = "Employee groups", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("employeeGroups")
  public List<@Valid EmployeeGroup> getEmployeeGroups() {
    return employeeGroups;
  }

  public void setEmployeeGroups(List<@Valid EmployeeGroup> employeeGroups) {
    this.employeeGroups = employeeGroups;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OfficeWithEmployeeGroups officeWithEmployeeGroups = (OfficeWithEmployeeGroups) o;
    return Objects.equals(this.id, officeWithEmployeeGroups.id) &&
        Objects.equals(this.name, officeWithEmployeeGroups.name) &&
        Objects.equals(this.address, officeWithEmployeeGroups.address) &&
        Objects.equals(this.employeeGroups, officeWithEmployeeGroups.employeeGroups);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, address, employeeGroups);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OfficeWithEmployeeGroups {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    employeeGroups: ").append(toIndentedString(employeeGroups)).append("\n");
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

