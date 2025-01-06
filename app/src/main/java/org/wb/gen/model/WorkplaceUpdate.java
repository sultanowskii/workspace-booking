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

@Schema(name = "WorkplaceUpdate", description = "Workplace")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class WorkplaceUpdate {

  private Long numberOfMonitors;

  private Double x;

  private Double y;

  private Double width;

  private Double height;

  public WorkplaceUpdate numberOfMonitors(Long numberOfMonitors) {
    this.numberOfMonitors = numberOfMonitors;
    return this;
  }

  /**
   * Number of monitors
   * minimum: 0
   * 
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

  public WorkplaceUpdate x(Double x) {
    this.x = x;
    return this;
  }

  /**
   * X
   * 
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

  public WorkplaceUpdate y(Double y) {
    this.y = y;
    return this;
  }

  /**
   * Y
   * 
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

  public WorkplaceUpdate width(Double width) {
    this.width = width;
    return this;
  }

  /**
   * Width
   * 
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

  public WorkplaceUpdate height(Double height) {
    this.height = height;
    return this;
  }

  /**
   * Height
   * 
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
    WorkplaceUpdate workplaceUpdate = (WorkplaceUpdate) o;
    return Objects.equals(this.numberOfMonitors, workplaceUpdate.numberOfMonitors) &&
        Objects.equals(this.x, workplaceUpdate.x) &&
        Objects.equals(this.y, workplaceUpdate.y) &&
        Objects.equals(this.width, workplaceUpdate.width) &&
        Objects.equals(this.height, workplaceUpdate.height);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numberOfMonitors, x, y, width, height);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WorkplaceUpdate {\n");
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
