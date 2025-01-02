package org.wb.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Meeting room booking (without participants)
 */

@Schema(name = "MeetingRoomBookingShort", description = "Meeting room booking (without participants)")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class MeetingRoomBookingShort {

  private Long id;

  private String description;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime startDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime endDate;

  private Object organizer;

  public MeetingRoomBookingShort id(Long id) {
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

  public MeetingRoomBookingShort description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Description
   * @return description
   */
  
  @Schema(name = "description", description = "Description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public MeetingRoomBookingShort startDate(OffsetDateTime startDate) {
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

  public MeetingRoomBookingShort endDate(OffsetDateTime endDate) {
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

  public MeetingRoomBookingShort organizer(Object organizer) {
    this.organizer = organizer;
    return this;
  }

  /**
   * Organizer
   * @return organizer
   */
  
  @Schema(name = "organizer", description = "Organizer", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("organizer")
  public Object getOrganizer() {
    return organizer;
  }

  public void setOrganizer(Object organizer) {
    this.organizer = organizer;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MeetingRoomBookingShort meetingRoomBookingShort = (MeetingRoomBookingShort) o;
    return Objects.equals(this.id, meetingRoomBookingShort.id) &&
        Objects.equals(this.description, meetingRoomBookingShort.description) &&
        Objects.equals(this.startDate, meetingRoomBookingShort.startDate) &&
        Objects.equals(this.endDate, meetingRoomBookingShort.endDate) &&
        Objects.equals(this.organizer, meetingRoomBookingShort.organizer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, description, startDate, endDate, organizer);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MeetingRoomBookingShort {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
    sb.append("    organizer: ").append(toIndentedString(organizer)).append("\n");
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

