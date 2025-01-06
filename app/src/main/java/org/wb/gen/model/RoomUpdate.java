package org.wb.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Room
 */

@Schema(name = "RoomUpdate", description = "Room")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class RoomUpdate {

  private String name;

  @Valid
  private List<@Valid RoomWall> walls = new ArrayList<>();

  public RoomUpdate name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Name
   * @return name
   */
  
  @Schema(name = "name", description = "Name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public RoomUpdate walls(List<@Valid RoomWall> walls) {
    this.walls = walls;
    return this;
  }

  public RoomUpdate addWallsItem(RoomWall wallsItem) {
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
    RoomUpdate roomUpdate = (RoomUpdate) o;
    return Objects.equals(this.name, roomUpdate.name) &&
        Objects.equals(this.walls, roomUpdate.walls);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, walls);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RoomUpdate {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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

