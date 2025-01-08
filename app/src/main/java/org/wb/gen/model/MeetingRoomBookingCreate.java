package org.wb.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Meeting room booking
 */

@Schema(name = "MeetingRoomBookingCreate", description = "Meeting room booking")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class MeetingRoomBookingCreate {

  private String description;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate date;

  private String startTime;

  private String endTime;

  private Long employeeId;

  private Long meetingRoomId;

  @Valid
  private List<Long> participants = new ArrayList<>();

  public MeetingRoomBookingCreate description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Description
   * @return description
   */
  @Size(min = 1) 
  @Schema(name = "description", description = "Description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public MeetingRoomBookingCreate date(LocalDate date) {
    this.date = date;
    return this;
  }

  /**
   * Date
   * @return date
   */
  @Valid 
  @Schema(name = "date", description = "Date", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("date")
  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public MeetingRoomBookingCreate startTime(String startTime) {
    this.startTime = startTime;
    return this;
  }

  /**
   * Start time
   * @return startTime
   */
  
  @Schema(name = "startTime", description = "Start time", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("startTime")
  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public MeetingRoomBookingCreate endTime(String endTime) {
    this.endTime = endTime;
    return this;
  }

  /**
   * End time
   * @return endTime
   */
  
  @Schema(name = "endTime", description = "End time", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("endTime")
  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public MeetingRoomBookingCreate employeeId(Long employeeId) {
    this.employeeId = employeeId;
    return this;
  }

  /**
   * Employee ID (organizer)
   * @return employeeId
   */
  
  @Schema(name = "employeeId", description = "Employee ID (organizer)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("employeeId")
  public Long getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(Long employeeId) {
    this.employeeId = employeeId;
  }

  public MeetingRoomBookingCreate meetingRoomId(Long meetingRoomId) {
    this.meetingRoomId = meetingRoomId;
    return this;
  }

  /**
   * Workplace ID
   * @return meetingRoomId
   */
  
  @Schema(name = "meetingRoomId", description = "Workplace ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("meetingRoomId")
  public Long getMeetingRoomId() {
    return meetingRoomId;
  }

  public void setMeetingRoomId(Long meetingRoomId) {
    this.meetingRoomId = meetingRoomId;
  }

  public MeetingRoomBookingCreate participants(List<Long> participants) {
    this.participants = participants;
    return this;
  }

  public MeetingRoomBookingCreate addParticipantsItem(Long participantsItem) {
    if (this.participants == null) {
      this.participants = new ArrayList<>();
    }
    this.participants.add(participantsItem);
    return this;
  }

  /**
   * Other participants
   * @return participants
   */
  
  @Schema(name = "participants", description = "Other participants", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("participants")
  public List<Long> getParticipants() {
    return participants;
  }

  public void setParticipants(List<Long> participants) {
    this.participants = participants;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MeetingRoomBookingCreate meetingRoomBookingCreate = (MeetingRoomBookingCreate) o;
    return Objects.equals(this.description, meetingRoomBookingCreate.description) &&
        Objects.equals(this.date, meetingRoomBookingCreate.date) &&
        Objects.equals(this.startTime, meetingRoomBookingCreate.startTime) &&
        Objects.equals(this.endTime, meetingRoomBookingCreate.endTime) &&
        Objects.equals(this.employeeId, meetingRoomBookingCreate.employeeId) &&
        Objects.equals(this.meetingRoomId, meetingRoomBookingCreate.meetingRoomId) &&
        Objects.equals(this.participants, meetingRoomBookingCreate.participants);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, date, startTime, endTime, employeeId, meetingRoomId, participants);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MeetingRoomBookingCreate {\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    employeeId: ").append(toIndentedString(employeeId)).append("\n");
    sb.append("    meetingRoomId: ").append(toIndentedString(meetingRoomId)).append("\n");
    sb.append("    participants: ").append(toIndentedString(participants)).append("\n");
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

