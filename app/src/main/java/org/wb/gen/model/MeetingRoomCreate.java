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
 * Meeting room
 */

@Schema(name = "MeetingRoomCreate", description = "Meeting room")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-12-30T15:55:34.595779528+03:00[Europe/Moscow]", comments = "Generator version: 7.10.0")
public class MeetingRoomCreate {

  private Object roomId;

  private String name;

  public MeetingRoomCreate roomId(Object roomId) {
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

  public MeetingRoomCreate name(String name) {
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
    MeetingRoomCreate meetingRoomCreate = (MeetingRoomCreate) o;
    return Objects.equals(this.roomId, meetingRoomCreate.roomId) &&
        Objects.equals(this.name, meetingRoomCreate.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(roomId, name);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MeetingRoomCreate {\n");
    sb.append("    roomId: ").append(toIndentedString(roomId)).append("\n");
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

