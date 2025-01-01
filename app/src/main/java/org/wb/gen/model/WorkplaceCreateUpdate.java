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

@Schema(name = "WorkplaceCreateUpdate", description = "Workplace")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-01T12:50:04.846966322+03:00[Europe/Moscow]", comments = "Generator version: 7.10.0")
public class WorkplaceCreateUpdate {

  private Object roomId;

  private Object numberOfMonitors;

  private Object x;

  private Object y;

  private Object width;

  private Object height;

  public WorkplaceCreateUpdate roomId(Object roomId) {
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

  public WorkplaceCreateUpdate numberOfMonitors(Object numberOfMonitors) {
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

  public WorkplaceCreateUpdate x(Object x) {
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

  public WorkplaceCreateUpdate y(Object y) {
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

  public WorkplaceCreateUpdate width(Object width) {
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

  public WorkplaceCreateUpdate height(Object height) {
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
    WorkplaceCreateUpdate workplaceCreateUpdate = (WorkplaceCreateUpdate) o;
    return Objects.equals(this.roomId, workplaceCreateUpdate.roomId) &&
        Objects.equals(this.numberOfMonitors, workplaceCreateUpdate.numberOfMonitors) &&
        Objects.equals(this.x, workplaceCreateUpdate.x) &&
        Objects.equals(this.y, workplaceCreateUpdate.y) &&
        Objects.equals(this.width, workplaceCreateUpdate.width) &&
        Objects.equals(this.height, workplaceCreateUpdate.height);
  }

  @Override
  public int hashCode() {
    return Objects.hash(roomId, numberOfMonitors, x, y, width, height);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WorkplaceCreateUpdate {\n");
    sb.append("    roomId: ").append(toIndentedString(roomId)).append("\n");
    sb.append("    numberOfMonitors: ").append(toIndentedString(numberOfMonitors)).append("\n");
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

