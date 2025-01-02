package org.wb.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Meeting room booking
 */

@Schema(name = "MeetingRoomBookingUpdate", description = "Meeting room booking")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class MeetingRoomBookingUpdate {

  private String description;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime startDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime endDate;

  @Valid
  private List<Object> participants = new ArrayList<>();

  public MeetingRoomBookingUpdate description(String description) {
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

  public MeetingRoomBookingUpdate startDate(OffsetDateTime startDate) {
    this.startDate = startDate;
    return this;
  }

  /**
   * Start date
   * @return startDate
   */
  @Valid 
  @Schema(name = "startDate", description = "Start date", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("startDate")
  public OffsetDateTime getStartDate() {
    return startDate;
  }

  public void setStartDate(OffsetDateTime startDate) {
    this.startDate = startDate;
  }

  public MeetingRoomBookingUpdate endDate(OffsetDateTime endDate) {
    this.endDate = endDate;
    return this;
  }

  /**
   * End date
   * @return endDate
   */
  @Valid 
  @Schema(name = "endDate", description = "End date", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("endDate")
  public OffsetDateTime getEndDate() {
    return endDate;
  }

  public void setEndDate(OffsetDateTime endDate) {
    this.endDate = endDate;
  }

  public MeetingRoomBookingUpdate participants(List<Object> participants) {
    this.participants = participants;
    return this;
  }

  public MeetingRoomBookingUpdate addParticipantsItem(Object participantsItem) {
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
  public List<Object> getParticipants() {
    return participants;
  }

  public void setParticipants(List<Object> participants) {
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
    MeetingRoomBookingUpdate meetingRoomBookingUpdate = (MeetingRoomBookingUpdate) o;
    return Objects.equals(this.description, meetingRoomBookingUpdate.description) &&
        Objects.equals(this.startDate, meetingRoomBookingUpdate.startDate) &&
        Objects.equals(this.endDate, meetingRoomBookingUpdate.endDate) &&
        Objects.equals(this.participants, meetingRoomBookingUpdate.participants);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, startDate, endDate, participants);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MeetingRoomBookingUpdate {\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
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

