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
 * Employee group
 */

@Schema(name = "EmployeeGroupCreate", description = "Employee group")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-12-30T15:20:03.134003969+03:00[Europe/Moscow]", comments = "Generator version: 7.10.0")
public class EmployeeGroupCreate {

  private String name;

  public EmployeeGroupCreate name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Name
   * @return name
   */
  @Size(min = 1) 
  @Schema(name = "name", description = "Name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EmployeeGroupCreate employeeGroupCreate = (EmployeeGroupCreate) o;
    return Objects.equals(this.name, employeeGroupCreate.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EmployeeGroupCreate {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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

