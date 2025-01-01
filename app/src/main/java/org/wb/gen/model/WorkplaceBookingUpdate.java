package org.wb.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Workplace booking
 */

@Schema(name = "WorkplaceBookingUpdate", description = "Workplace booking")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class WorkplaceBookingUpdate {

  private Long workplaceId;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate date;

  public WorkplaceBookingUpdate workplaceId(Long workplaceId) {
    this.workplaceId = workplaceId;
    return this;
  }

  /**
   * Workplace ID
   * @return workplaceId
   */
  
  @Schema(name = "workplaceId", description = "Workplace ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("workplaceId")
  public Long getWorkplaceId() {
    return workplaceId;
  }

  public void setWorkplaceId(Long workplaceId) {
    this.workplaceId = workplaceId;
  }

  public WorkplaceBookingUpdate date(LocalDate date) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WorkplaceBookingUpdate workplaceBookingUpdate = (WorkplaceBookingUpdate) o;
    return Objects.equals(this.workplaceId, workplaceBookingUpdate.workplaceId) &&
        Objects.equals(this.date, workplaceBookingUpdate.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(workplaceId, date);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WorkplaceBookingUpdate {\n");
    sb.append("    workplaceId: ").append(toIndentedString(workplaceId)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
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

