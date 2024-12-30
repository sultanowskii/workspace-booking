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
 * Room
 */

@Schema(name = "RoomCreate", description = "Room")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-12-30T16:53:45.589318086+03:00[Europe/Moscow]", comments = "Generator version: 7.10.0")
public class RoomCreate {

  private Object officeId;

  private String address;

  public RoomCreate officeId(Object officeId) {
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

  public RoomCreate address(String address) {
    this.address = address;
    return this;
  }

  /**
   * Name
   * @return address
   */
  
  @Schema(name = "address", description = "Name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("address")
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RoomCreate roomCreate = (RoomCreate) o;
    return Objects.equals(this.officeId, roomCreate.officeId) &&
        Objects.equals(this.address, roomCreate.address);
  }

  @Override
  public int hashCode() {
    return Objects.hash(officeId, address);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RoomCreate {\n");
    sb.append("    officeId: ").append(toIndentedString(officeId)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
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

