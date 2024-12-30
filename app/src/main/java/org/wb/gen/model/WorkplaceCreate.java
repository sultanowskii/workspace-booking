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
 * Workplace
 */

@Schema(name = "WorkplaceCreate", description = "Workplace")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-12-30T15:20:03.134003969+03:00[Europe/Moscow]", comments = "Generator version: 7.10.0")
public class WorkplaceCreate {

  private Object roomId;

  private Object numberOfMonitors;

  public WorkplaceCreate roomId(Object roomId) {
    this.roomId = roomId;
    return this;
  }

  /**
   * Room ID
   * @return roomId
   */
  
  @Schema(name = "roomId", description = "Room ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("roomId")
  public Object getRoomId() {
    return roomId;
  }

  public void setRoomId(Object roomId) {
    this.roomId = roomId;
  }

  public WorkplaceCreate numberOfMonitors(Object numberOfMonitors) {
    this.numberOfMonitors = numberOfMonitors;
    return this;
  }

  /**
   * Number of monitors
   * @return numberOfMonitors
   */
  
  @Schema(name = "numberOfMonitors", description = "Number of monitors", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("numberOfMonitors")
  public Object getNumberOfMonitors() {
    return numberOfMonitors;
  }

  public void setNumberOfMonitors(Object numberOfMonitors) {
    this.numberOfMonitors = numberOfMonitors;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WorkplaceCreate workplaceCreate = (WorkplaceCreate) o;
    return Objects.equals(this.roomId, workplaceCreate.roomId) &&
        Objects.equals(this.numberOfMonitors, workplaceCreate.numberOfMonitors);
  }

  @Override
  public int hashCode() {
    return Objects.hash(roomId, numberOfMonitors);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WorkplaceCreate {\n");
    sb.append("    roomId: ").append(toIndentedString(roomId)).append("\n");
    sb.append("    numberOfMonitors: ").append(toIndentedString(numberOfMonitors)).append("\n");
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

