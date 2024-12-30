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
 * Room Wall
 */

@Schema(name = "RoomWall", description = "Room Wall")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-12-30T14:24:22.057047733+03:00[Europe/Moscow]", comments = "Generator version: 7.10.0")
public class RoomWall {

  private Object id;

  private Object roomId;

  private Object x1;

  private Object y1;

  private Object x2;

  private Object y2;

  public RoomWall id(Object id) {
    this.id = id;
    return this;
  }

  /**
   * ID
   * @return id
   */
  
  @Schema(name = "id", description = "ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Object getId() {
    return id;
  }

  public void setId(Object id) {
    this.id = id;
  }

  public RoomWall roomId(Object roomId) {
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

  public RoomWall x1(Object x1) {
    this.x1 = x1;
    return this;
  }

  /**
   * Name
   * @return x1
   */
  
  @Schema(name = "x1", description = "Name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("x1")
  public Object getX1() {
    return x1;
  }

  public void setX1(Object x1) {
    this.x1 = x1;
  }

  public RoomWall y1(Object y1) {
    this.y1 = y1;
    return this;
  }

  /**
   * Name
   * @return y1
   */
  
  @Schema(name = "y1", description = "Name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("y1")
  public Object getY1() {
    return y1;
  }

  public void setY1(Object y1) {
    this.y1 = y1;
  }

  public RoomWall x2(Object x2) {
    this.x2 = x2;
    return this;
  }

  /**
   * Name
   * @return x2
   */
  
  @Schema(name = "x2", description = "Name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("x2")
  public Object getX2() {
    return x2;
  }

  public void setX2(Object x2) {
    this.x2 = x2;
  }

  public RoomWall y2(Object y2) {
    this.y2 = y2;
    return this;
  }

  /**
   * Name
   * @return y2
   */
  
  @Schema(name = "y2", description = "Name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("y2")
  public Object getY2() {
    return y2;
  }

  public void setY2(Object y2) {
    this.y2 = y2;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RoomWall roomWall = (RoomWall) o;
    return Objects.equals(this.id, roomWall.id) &&
        Objects.equals(this.roomId, roomWall.roomId) &&
        Objects.equals(this.x1, roomWall.x1) &&
        Objects.equals(this.y1, roomWall.y1) &&
        Objects.equals(this.x2, roomWall.x2) &&
        Objects.equals(this.y2, roomWall.y2);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, roomId, x1, y1, x2, y2);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RoomWall {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    roomId: ").append(toIndentedString(roomId)).append("\n");
    sb.append("    x1: ").append(toIndentedString(x1)).append("\n");
    sb.append("    y1: ").append(toIndentedString(y1)).append("\n");
    sb.append("    x2: ").append(toIndentedString(x2)).append("\n");
    sb.append("    y2: ").append(toIndentedString(y2)).append("\n");
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

