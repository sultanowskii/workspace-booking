package org.wb.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Meeting room
 */

@Schema(name = "MeetingRoomCreateUpdate", description = "Meeting room")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class MeetingRoomCreateUpdate {

  private Object roomId;

  private String name;

  private Object x;

  private Object y;

  private Object width;

  private Object height;

  public MeetingRoomCreateUpdate roomId(Object roomId) {
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

  public MeetingRoomCreateUpdate name(String name) {
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

  public MeetingRoomCreateUpdate x(Object x) {
    this.x = x;
    return this;
  }

  /**
   * X
   * @return x
   */
  
  @Schema(name = "x", description = "X", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("x")
  public Object getX() {
    return x;
  }

  public void setX(Object x) {
    this.x = x;
  }

  public MeetingRoomCreateUpdate y(Object y) {
    this.y = y;
    return this;
  }

  /**
   * Y
   * @return y
   */
  
  @Schema(name = "y", description = "Y", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("y")
  public Object getY() {
    return y;
  }

  public void setY(Object y) {
    this.y = y;
  }

  public MeetingRoomCreateUpdate width(Object width) {
    this.width = width;
    return this;
  }

  /**
   * Width
   * @return width
   */
  
  @Schema(name = "width", description = "Width", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("width")
  public Object getWidth() {
    return width;
  }

  public void setWidth(Object width) {
    this.width = width;
  }

  public MeetingRoomCreateUpdate height(Object height) {
    this.height = height;
    return this;
  }

  /**
   * Height
   * @return height
   */
  
  @Schema(name = "height", description = "Height", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("height")
  public Object getHeight() {
    return height;
  }

  public void setHeight(Object height) {
    this.height = height;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MeetingRoomCreateUpdate meetingRoomCreateUpdate = (MeetingRoomCreateUpdate) o;
    return Objects.equals(this.roomId, meetingRoomCreateUpdate.roomId) &&
        Objects.equals(this.name, meetingRoomCreateUpdate.name) &&
        Objects.equals(this.x, meetingRoomCreateUpdate.x) &&
        Objects.equals(this.y, meetingRoomCreateUpdate.y) &&
        Objects.equals(this.width, meetingRoomCreateUpdate.width) &&
        Objects.equals(this.height, meetingRoomCreateUpdate.height);
  }

  @Override
  public int hashCode() {
    return Objects.hash(roomId, name, x, y, width, height);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MeetingRoomCreateUpdate {\n");
    sb.append("    roomId: ").append(toIndentedString(roomId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    x: ").append(toIndentedString(x)).append("\n");
    sb.append("    y: ").append(toIndentedString(y)).append("\n");
    sb.append("    width: ").append(toIndentedString(width)).append("\n");
    sb.append("    height: ").append(toIndentedString(height)).append("\n");
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

