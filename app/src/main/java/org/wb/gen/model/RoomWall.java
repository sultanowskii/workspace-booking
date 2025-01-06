package org.wb.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Room Wall
 */

@Schema(name = "RoomWall", description = "Room Wall")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class RoomWall {

  private Double x1;

  private Double y1;

  private Double x2;

  private Double y2;

  public RoomWall x1(Double x1) {
    this.x1 = x1;
    return this;
  }

  /**
   * X1
   * @return x1
   */
  
  @Schema(name = "x1", description = "X1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("x1")
  public Double getX1() {
    return x1;
  }

  public void setX1(Double x1) {
    this.x1 = x1;
  }

  public RoomWall y1(Double y1) {
    this.y1 = y1;
    return this;
  }

  /**
   * Y1
   * @return y1
   */
  
  @Schema(name = "y1", description = "Y1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("y1")
  public Double getY1() {
    return y1;
  }

  public void setY1(Double y1) {
    this.y1 = y1;
  }

  public RoomWall x2(Double x2) {
    this.x2 = x2;
    return this;
  }

  /**
   * X2
   * @return x2
   */
  
  @Schema(name = "x2", description = "X2", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("x2")
  public Double getX2() {
    return x2;
  }

  public void setX2(Double x2) {
    this.x2 = x2;
  }

  public RoomWall y2(Double y2) {
    this.y2 = y2;
    return this;
  }

  /**
   * Y2
   * @return y2
   */
  
  @Schema(name = "y2", description = "Y2", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("y2")
  public Double getY2() {
    return y2;
  }

  public void setY2(Double y2) {
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
    return Objects.equals(this.x1, roomWall.x1) &&
        Objects.equals(this.y1, roomWall.y1) &&
        Objects.equals(this.x2, roomWall.x2) &&
        Objects.equals(this.y2, roomWall.y2);
  }

  @Override
  public int hashCode() {
    return Objects.hash(x1, y1, x2, y2);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RoomWall {\n");
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

