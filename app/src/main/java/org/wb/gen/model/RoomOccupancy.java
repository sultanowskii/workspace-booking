package org.wb.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Room occupancy
 */

@Schema(name = "RoomOccupancy", description = "Room occupancy")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class RoomOccupancy {

  private Long id;

  private String name;

  private Double rate;

  public RoomOccupancy id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Room ID
   * @return id
   */
  
  @Schema(name = "id", description = "Room ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public RoomOccupancy name(String name) {
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

  public RoomOccupancy rate(Double rate) {
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
    RoomOccupancy roomOccupancy = (RoomOccupancy) o;
    return Objects.equals(this.id, roomOccupancy.id) &&
        Objects.equals(this.name, roomOccupancy.name) &&
        Objects.equals(this.rate, roomOccupancy.rate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, rate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RoomOccupancy {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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

