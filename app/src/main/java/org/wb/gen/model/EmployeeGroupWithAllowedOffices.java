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
 * Employee group
 */

@Schema(name = "EmployeeGroupWithAllowedOffices", description = "Employee group")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class EmployeeGroupWithAllowedOffices {

  private Long id;

  private String name;

  @Valid
  private List<@Valid Office> allowedOffices = new ArrayList<>();

  public EmployeeGroupWithAllowedOffices id(Long id) {
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

  public EmployeeGroupWithAllowedOffices name(String name) {
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

  public EmployeeGroupWithAllowedOffices allowedOffices(List<@Valid Office> allowedOffices) {
    this.allowedOffices = allowedOffices;
    return this;
  }

  public EmployeeGroupWithAllowedOffices addAllowedOfficesItem(Office allowedOfficesItem) {
    if (this.allowedOffices == null) {
      this.allowedOffices = new ArrayList<>();
    }
    this.allowedOffices.add(allowedOfficesItem);
    return this;
  }

  /**
   * Allowed offices
   * @return allowedOffices
   */
  @Valid 
  @Schema(name = "allowedOffices", description = "Allowed offices", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("allowedOffices")
  public List<@Valid Office> getAllowedOffices() {
    return allowedOffices;
  }

  public void setAllowedOffices(List<@Valid Office> allowedOffices) {
    this.allowedOffices = allowedOffices;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EmployeeGroupWithAllowedOffices employeeGroupWithAllowedOffices = (EmployeeGroupWithAllowedOffices) o;
    return Objects.equals(this.id, employeeGroupWithAllowedOffices.id) &&
        Objects.equals(this.name, employeeGroupWithAllowedOffices.name) &&
        Objects.equals(this.allowedOffices, employeeGroupWithAllowedOffices.allowedOffices);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, allowedOffices);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EmployeeGroupWithAllowedOffices {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    allowedOffices: ").append(toIndentedString(allowedOffices)).append("\n");
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

