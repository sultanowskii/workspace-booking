package org.wb.gen.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.wb.gen.model.RoomWall;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Room with walls
 */

@Schema(name = "RoomWithWalls", description = "Room with walls")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-01T12:50:04.846966322+03:00[Europe/Moscow]", comments = "Generator version: 7.10.0")
public class RoomWithWalls {

  private Object id;

  private Object officeId;

  private String address;

  @Valid
  private List<@Valid RoomWall> walls = new ArrayList<>();

  public RoomWithWalls id(Object id) {
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

  public RoomWithWalls officeId(Object officeId) {
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

  public RoomWithWalls address(String address) {
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

  public RoomWithWalls walls(List<@Valid RoomWall> walls) {
    this.walls = walls;
    return this;
  }

  public RoomWithWalls addWallsItem(RoomWall wallsItem) {
    if (this.walls == null) {
      this.walls = new ArrayList<>();
    }
    this.walls.add(wallsItem);
    return this;
  }

  /**
   * List of room walls
   * @return walls
   */
  @Valid 
  @Schema(name = "walls", description = "List of room walls", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("walls")
  public List<@Valid RoomWall> getWalls() {
    return walls;
  }

  public void setWalls(List<@Valid RoomWall> walls) {
    this.walls = walls;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RoomWithWalls roomWithWalls = (RoomWithWalls) o;
    return Objects.equals(this.id, roomWithWalls.id) &&
        Objects.equals(this.officeId, roomWithWalls.officeId) &&
        Objects.equals(this.address, roomWithWalls.address) &&
        Objects.equals(this.walls, roomWithWalls.walls);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, officeId, address, walls);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RoomWithWalls {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    officeId: ").append(toIndentedString(officeId)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    walls: ").append(toIndentedString(walls)).append("\n");
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

