package org.wb.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Office occupancy
 */

@Schema(name = "OfficeOccupancy", description = "Office occupancy")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class OfficeOccupancy {

  private Long id;

  private String name;

  private String address;

  private Double rate;

  public OfficeOccupancy id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Office ID
   * @return id
   */
  
  @Schema(name = "id", description = "Office ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public OfficeOccupancy name(String name) {
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

  public OfficeOccupancy address(String address) {
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

  public OfficeOccupancy rate(Double rate) {
    this.rate = rate;
    return this;
  }

  /**
   * Rate
   * @return rate
   */
  
  @Schema(name = "rate", description = "Rate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("rate")
  public Double getRate() {
    return rate;
  }

  public void setRate(Double rate) {
    this.rate = rate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OfficeOccupancy officeOccupancy = (OfficeOccupancy) o;
    return Objects.equals(this.id, officeOccupancy.id) &&
        Objects.equals(this.name, officeOccupancy.name) &&
        Objects.equals(this.address, officeOccupancy.address) &&
        Objects.equals(this.rate, officeOccupancy.rate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, address, rate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OfficeOccupancy {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    rate: ").append(toIndentedString(rate)).append("\n");
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

