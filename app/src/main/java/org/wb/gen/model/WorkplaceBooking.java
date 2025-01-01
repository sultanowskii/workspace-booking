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

@Schema(name = "WorkplaceBooking", description = "Workplace booking")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class WorkplaceBooking {

  private Long id;

  private Long employeeId;

  private Long workplaceId;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate date;

  public WorkplaceBooking id(Long id) {
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

  public WorkplaceBooking employeeId(Long employeeId) {
    this.employeeId = employeeId;
    return this;
  }

  /**
   * Employee ID
   * @return employeeId
   */
  
  @Schema(name = "employeeId", description = "Employee ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("employeeId")
  public Long getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(Long employeeId) {
    this.employeeId = employeeId;
  }

  public WorkplaceBooking workplaceId(Long workplaceId) {
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

  public WorkplaceBooking date(LocalDate date) {
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
    WorkplaceBooking workplaceBooking = (WorkplaceBooking) o;
    return Objects.equals(this.id, workplaceBooking.id) &&
        Objects.equals(this.employeeId, workplaceBooking.employeeId) &&
        Objects.equals(this.workplaceId, workplaceBooking.workplaceId) &&
        Objects.equals(this.date, workplaceBooking.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, employeeId, workplaceId, date);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WorkplaceBooking {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    employeeId: ").append(toIndentedString(employeeId)).append("\n");
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

