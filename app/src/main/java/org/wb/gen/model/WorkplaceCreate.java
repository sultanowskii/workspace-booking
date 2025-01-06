package org.wb.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Workplace
 */

@Schema(name = "WorkplaceCreate", description = "Workplace")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class WorkplaceCreate {

  private Long roomId;

  private Long numberOfMonitors;

  private Double x;

  private Double y;

  private Double width;

  private Double height;

  public WorkplaceCreate roomId(Long roomId) {
    this.roomId = roomId;
    return this;
  }

  /**
   * Room ID
   * @return roomId
   */
  
  @Schema(name = "roomId", description = "Room ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("roomId")
  public Long getRoomId() {
    return roomId;
  }

  public void setRoomId(Long roomId) {
    this.roomId = roomId;
  }

  public WorkplaceCreate numberOfMonitors(Long numberOfMonitors) {
    this.numberOfMonitors = numberOfMonitors;
    return this;
  }

  /**
   * Number of monitors
   * minimum: 0
   * @return numberOfMonitors
   */
  @Min(0L) 
  @Schema(name = "numberOfMonitors", description = "Number of monitors", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("numberOfMonitors")
  public Long getNumberOfMonitors() {
    return numberOfMonitors;
  }

  public void setNumberOfMonitors(Long numberOfMonitors) {
    this.numberOfMonitors = numberOfMonitors;
  }

  public WorkplaceCreate x(Double x) {
    this.x = x;
    return this;
  }

  /**
   * X
   * @return x
   */
  
  @Schema(name = "x", description = "X", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("x")
  public Double getX() {
    return x;
  }

  public void setX(Double x) {
    this.x = x;
  }

  public WorkplaceCreate y(Double y) {
    this.y = y;
    return this;
  }

  /**
   * Y
   * @return y
   */
  
  @Schema(name = "y", description = "Y", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("y")
  public Double getY() {
    return y;
  }

  public void setY(Double y) {
    this.y = y;
  }

  public WorkplaceCreate width(Double width) {
    this.width = width;
    return this;
  }

  /**
   * Width
   * @return width
   */
  
  @Schema(name = "width", description = "Width", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("width")
  public Double getWidth() {
    return width;
  }

  public void setWidth(Double width) {
    this.width = width;
  }

  public WorkplaceCreate height(Double height) {
    this.height = height;
    return this;
  }

  /**
   * Height
   * @return height
   */
  
  @Schema(name = "height", description = "Height", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("height")
  public Double getHeight() {
    return height;
  }

  public void setHeight(Double height) {
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
    WorkplaceCreate workplaceCreate = (WorkplaceCreate) o;
    return Objects.equals(this.roomId, workplaceCreate.roomId) &&
        Objects.equals(this.numberOfMonitors, workplaceCreate.numberOfMonitors) &&
        Objects.equals(this.x, workplaceCreate.x) &&
        Objects.equals(this.y, workplaceCreate.y) &&
        Objects.equals(this.width, workplaceCreate.width) &&
        Objects.equals(this.height, workplaceCreate.height);
  }

  @Override
  public int hashCode() {
    return Objects.hash(roomId, numberOfMonitors, x, y, width, height);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WorkplaceCreate {\n");
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

