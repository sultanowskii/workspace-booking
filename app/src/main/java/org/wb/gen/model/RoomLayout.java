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
 * Room layout
 */

@Schema(name = "RoomLayout", description = "Room layout")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class RoomLayout {

  private Long id;

  private Long officeId;

  private String name;

  @Valid
  private List<@Valid RoomWall> walls = new ArrayList<>();

  @Valid
  private List<@Valid Workplace> workplaces = new ArrayList<>();

  @Valid
  private List<@Valid MeetingRoom> meetingRooms = new ArrayList<>();

  public RoomLayout id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * ID
   * @return id
   */
  
  @Schema(name = "id", description = "ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public RoomLayout officeId(Long officeId) {
    this.officeId = officeId;
    return this;
  }

  /**
   * Office ID
   * @return officeId
   */
  
  @Schema(name = "officeId", description = "Office ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("officeId")
  public Long getOfficeId() {
    return officeId;
  }

  public void setOfficeId(Long officeId) {
    this.officeId = officeId;
  }

  public RoomLayout name(String name) {
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

  public RoomLayout walls(List<@Valid RoomWall> walls) {
    this.walls = walls;
    return this;
  }

  public RoomLayout addWallsItem(RoomWall wallsItem) {
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

  public RoomLayout workplaces(List<@Valid Workplace> workplaces) {
    this.workplaces = workplaces;
    return this;
  }

  public RoomLayout addWorkplacesItem(Workplace workplacesItem) {
    if (this.workplaces == null) {
      this.workplaces = new ArrayList<>();
    }
    this.workplaces.add(workplacesItem);
    return this;
  }

  /**
   * Workplaces
   * @return workplaces
   */
  @Valid 
  @Schema(name = "workplaces", description = "Workplaces", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("workplaces")
  public List<@Valid Workplace> getWorkplaces() {
    return workplaces;
  }

  public void setWorkplaces(List<@Valid Workplace> workplaces) {
    this.workplaces = workplaces;
  }

  public RoomLayout meetingRooms(List<@Valid MeetingRoom> meetingRooms) {
    this.meetingRooms = meetingRooms;
    return this;
  }

  public RoomLayout addMeetingRoomsItem(MeetingRoom meetingRoomsItem) {
    if (this.meetingRooms == null) {
      this.meetingRooms = new ArrayList<>();
    }
    this.meetingRooms.add(meetingRoomsItem);
    return this;
  }

  /**
   * Meeting rooms
   * @return meetingRooms
   */
  @Valid 
  @Schema(name = "meetingRooms", description = "Meeting rooms", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("meetingRooms")
  public List<@Valid MeetingRoom> getMeetingRooms() {
    return meetingRooms;
  }

  public void setMeetingRooms(List<@Valid MeetingRoom> meetingRooms) {
    this.meetingRooms = meetingRooms;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RoomLayout roomLayout = (RoomLayout) o;
    return Objects.equals(this.id, roomLayout.id) &&
        Objects.equals(this.officeId, roomLayout.officeId) &&
        Objects.equals(this.name, roomLayout.name) &&
        Objects.equals(this.walls, roomLayout.walls) &&
        Objects.equals(this.workplaces, roomLayout.workplaces) &&
        Objects.equals(this.meetingRooms, roomLayout.meetingRooms);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, officeId, name, walls, workplaces, meetingRooms);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RoomLayout {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    officeId: ").append(toIndentedString(officeId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    walls: ").append(toIndentedString(walls)).append("\n");
    sb.append("    workplaces: ").append(toIndentedString(workplaces)).append("\n");
    sb.append("    meetingRooms: ").append(toIndentedString(meetingRooms)).append("\n");
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

