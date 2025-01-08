/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.10.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package org.wb.gen.api;

import org.springframework.format.annotation.DateTimeFormat;
import org.wb.gen.model.Error;
import java.time.LocalDate;
import org.springframework.data.domain.Pageable;
import org.springdoc.core.annotations.ParameterObject;
import org.wb.gen.model.WorkplaceBooking;
import org.wb.gen.model.WorkplaceBookingCreate;
import org.wb.gen.model.WorkplaceBookingUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
@Validated
@Controller
@Tag(name = "workplaceBookings", description = "the workplaceBookings API")
public interface WorkplaceBookingsApi {

    /**
     * POST /workplaceBookings : Create workplace booking
     *
     * @param workplaceBookingCreate (required)
     * @return Success (status code 201)
     *         or Invalid request (status code 400)
     */
    @Operation(operationId = "createWorkplaceBooking", summary = "Create workplace booking", responses = {
            @ApiResponse(responseCode = "201", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = WorkplaceBooking.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))
            })
    })
    @RequestMapping(method = RequestMethod.POST, value = "/workplaceBookings", produces = {
            "application/json" }, consumes = { "application/json" })

    ResponseEntity<WorkplaceBooking> createWorkplaceBooking(
            @Parameter(name = "WorkplaceBookingCreate", description = "", required = true) @Valid @RequestBody WorkplaceBookingCreate workplaceBookingCreate);

    /**
     * DELETE /workplaceBookings/{id} : Delete workplace booking
     *
     * @param id (required)
     * @return Success (status code 204)
     *         or Resource Not Found (status code 404)
     */
    @Operation(operationId = "deleteWorkplaceBooking", summary = "Delete workplace booking", responses = {
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Resource Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            })
    })
    @RequestMapping(method = RequestMethod.DELETE, value = "/workplaceBookings/{id}", produces = { "application/json" })

    ResponseEntity<Void> deleteWorkplaceBooking(
            @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id);

    /**
     * GET /workplaceBookings/{id} : Get workplace booking
     *
     * @param id (required)
     * @return Success (status code 200)
     *         or Resource Not Found (status code 404)
     */
    @Operation(operationId = "getWorkplaceBooking", summary = "Get workplace booking", responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = WorkplaceBooking.class))
            }),
            @ApiResponse(responseCode = "404", description = "Resource Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            })
    })
    @RequestMapping(method = RequestMethod.GET, value = "/workplaceBookings/{id}", produces = { "application/json" })

    ResponseEntity<WorkplaceBooking> getWorkplaceBooking(
            @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id);

    /**
     * GET /workplaceBookings : Get workplace bookings
     * Get list of workplace bookings.
     *
     * @param employeeId  Employee ID (optional)
     * @param workplaceId Workplace ID (optional)
     * @param date        Date (optional)
     * @return Success (status code 200)
     */
    @Operation(operationId = "getWorkplaceBookings", summary = "Get workplace bookings", description = "Get list of workplace bookings. ", responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = WorkplaceBooking.class)))
            })
    })
    @RequestMapping(method = RequestMethod.GET, value = "/workplaceBookings", produces = { "application/json" })

    ResponseEntity<List<WorkplaceBooking>> getWorkplaceBookings(
            @Parameter(name = "employeeId", description = "Employee ID", in = ParameterIn.QUERY) @Valid @RequestParam(value = "employeeId", required = false) Long employeeId,
            @Parameter(name = "workplaceId", description = "Workplace ID", in = ParameterIn.QUERY) @Valid @RequestParam(value = "workplaceId", required = false) Long workplaceId,
            @Parameter(name = "date", description = "Date", in = ParameterIn.QUERY) @Valid @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @ParameterObject final Pageable pageable);

    /**
     * PUT /workplaceBookings/{id} : Update workplace booking
     *
     * @param id                     (required)
     * @param workplaceBookingUpdate (required)
     * @return Success (status code 200)
     *         or Invalid request (status code 400)
     *         or Resource Not Found (status code 404)
     */
    @Operation(operationId = "updateWorkplaceBooking", summary = "Update workplace booking", responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = WorkplaceBooking.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))
            }),
            @ApiResponse(responseCode = "404", description = "Resource Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            })
    })
    @RequestMapping(method = RequestMethod.PUT, value = "/workplaceBookings/{id}", produces = {
            "application/json" }, consumes = { "application/json" })

    ResponseEntity<WorkplaceBooking> updateWorkplaceBooking(
            @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id,
            @Parameter(name = "WorkplaceBookingUpdate", description = "", required = true) @Valid @RequestBody WorkplaceBookingUpdate workplaceBookingUpdate);

}
