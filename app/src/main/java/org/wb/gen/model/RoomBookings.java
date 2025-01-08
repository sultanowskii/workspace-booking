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
 * Room bookings
 */

@Schema(name = "RoomBookings", description = "Room bookings")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class RoomBookings {

  private Long id;

  @Valid
  private List<@Valid WorkplaceBooking> workplaceBookings = new ArrayList<>();

  @Valid
  private List<@Valid MeetingRoomBookingShort> meetingRoomBookings = new ArrayList<>();

  public RoomBookings id(Long id) {
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

  public RoomBookings workplaceBookings(List<@Valid WorkplaceBooking> workplaceBookings) {
    this.workplaceBookings = workplaceBookings;
    return this;
  }

  public RoomBookings addWorkplaceBookingsItem(WorkplaceBooking workplaceBookingsItem) {
    if (this.workplaceBookings == null) {
      this.workplaceBookings = new ArrayList<>();
    }
    this.workplaceBookings.add(workplaceBookingsItem);
    return this;
  }

  /**
   * Workplace bookings
   * @return workplaceBookings
   */
  @Valid 
  @Schema(name = "workplaceBookings", description = "Workplace bookings", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("workplaceBookings")
  public List<@Valid WorkplaceBooking> getWorkplaceBookings() {
    return workplaceBookings;
  }

  public void setWorkplaceBookings(List<@Valid WorkplaceBooking> workplaceBookings) {
    this.workplaceBookings = workplaceBookings;
  }

  public RoomBookings meetingRoomBookings(List<@Valid MeetingRoomBookingShort> meetingRoomBookings) {
    this.meetingRoomBookings = meetingRoomBookings;
    return this;
  }

  public RoomBookings addMeetingRoomBookingsItem(MeetingRoomBookingShort meetingRoomBookingsItem) {
    if (this.meetingRoomBookings == null) {
      this.meetingRoomBookings = new ArrayList<>();
    }
    this.meetingRoomBookings.add(meetingRoomBookingsItem);
    return this;
  }

  /**
   * Meeting room bookings
   * @return meetingRoomBookings
   */
  @Valid 
  @Schema(name = "meetingRoomBookings", description = "Meeting room bookings", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("meetingRoomBookings")
  public List<@Valid MeetingRoomBookingShort> getMeetingRoomBookings() {
    return meetingRoomBookings;
  }

  public void setMeetingRoomBookings(List<@Valid MeetingRoomBookingShort> meetingRoomBookings) {
    this.meetingRoomBookings = meetingRoomBookings;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RoomBookings roomBookings = (RoomBookings) o;
    return Objects.equals(this.id, roomBookings.id) &&
        Objects.equals(this.workplaceBookings, roomBookings.workplaceBookings) &&
        Objects.equals(this.meetingRoomBookings, roomBookings.meetingRoomBookings);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, workplaceBookings, meetingRoomBookings);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RoomBookings {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    workplaceBookings: ").append(toIndentedString(workplaceBookings)).append("\n");
    sb.append("    meetingRoomBookings: ").append(toIndentedString(meetingRoomBookings)).append("\n");
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

