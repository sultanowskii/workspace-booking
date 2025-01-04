/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.10.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package org.wb.gen.api;

import org.springframework.format.annotation.DateTimeFormat;
import org.wb.gen.model.Error;
import java.time.LocalDate;
import org.wb.gen.model.MeetingRoomBooking;
import org.wb.gen.model.MeetingRoomBookingCreate;
import org.wb.gen.model.MeetingRoomBookingShort;
import org.wb.gen.model.MeetingRoomBookingUpdate;
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
import jakarta.validation.constraints.*;
import java.util.List;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
@Validated
@Controller
@Tag(name = "meetingRoomBookings", description = "the meetingRoomBookings API")
public interface MeetingRoomBookingsApi {

    /**
     * POST /meetingRoomBookings : Create meeting room booking
     *
     * @param meetingRoomBookingCreate  (optional)
     * @return Success (status code 201)
     */
    @Operation(
        operationId = "createMeetingRoomBooking",
        summary = "Create meeting room booking",
        responses = {
            @ApiResponse(responseCode = "201", description = "Success", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = MeetingRoomBooking.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/meetingRoomBookings",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    ResponseEntity<MeetingRoomBooking> createMeetingRoomBooking(
        @Parameter(name = "MeetingRoomBookingCreate", description = "") @Valid @RequestBody(required = false) MeetingRoomBookingCreate meetingRoomBookingCreate
    );


    /**
     * DELETE /meetingRoomBookings/{id} : Delete meeting room booking
     *
     * @param id  (required)
     * @return Success (status code 204)
     *         or Resource Not Found (status code 404)
     */
    @Operation(
        operationId = "deleteMeetingRoomBooking",
        summary = "Delete meeting room booking",
        responses = {
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Resource Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/meetingRoomBookings/{id}",
        produces = { "application/json" }
    )
    
    ResponseEntity<Void> deleteMeetingRoomBooking(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id
    );


    /**
     * GET /meetingRoomBookings/{id} : Get meeting room booking
     *
     * @param id  (required)
     * @return Success (status code 200)
     *         or Resource Not Found (status code 404)
     */
    @Operation(
        operationId = "getMeetingRoomBooking",
        summary = "Get meeting room booking",
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = MeetingRoomBooking.class))
            }),
            @ApiResponse(responseCode = "404", description = "Resource Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/meetingRoomBookings/{id}",
        produces = { "application/json" }
    )
    
    ResponseEntity<MeetingRoomBooking> getMeetingRoomBooking(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id
    );


    /**
     * GET /meetingRoomBookings : Get meeting room bookings
     *
     * @param organizerId Organizer ID (employee) (optional)
     * @param meetingRoomId Meeting Room ID (optional)
     * @param date Date (optional)
     * @return Success (status code 200)
     */
    @Operation(
        operationId = "getMeetingRoomBookings",
        summary = "Get meeting room bookings",
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MeetingRoomBookingShort.class)))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/meetingRoomBookings",
        produces = { "application/json" }
    )
    
    ResponseEntity<List<MeetingRoomBookingShort>> getMeetingRoomBookings(
        @Parameter(name = "organizerId", description = "Organizer ID (employee)", in = ParameterIn.QUERY) @Valid @RequestParam(value = "organizerId", required = false) Long organizerId,
        @Parameter(name = "meetingRoomId", description = "Meeting Room ID", in = ParameterIn.QUERY) @Valid @RequestParam(value = "meetingRoomId", required = false) Long meetingRoomId,
        @Parameter(name = "date", description = "Date", in = ParameterIn.QUERY) @Valid @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    );


    /**
     * PUT /meetingRoomBookings/{id} : Update meeting room booking
     *
     * @param id  (required)
     * @param meetingRoomBookingUpdate  (optional)
     * @return Success (status code 200)
     *         or Resource Not Found (status code 404)
     */
    @Operation(
        operationId = "updateMeetingRoomBooking",
        summary = "Update meeting room booking",
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = MeetingRoomBooking.class))
            }),
            @ApiResponse(responseCode = "404", description = "Resource Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.PUT,
        value = "/meetingRoomBookings/{id}",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    ResponseEntity<MeetingRoomBooking> updateMeetingRoomBooking(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id,
        @Parameter(name = "MeetingRoomBookingUpdate", description = "") @Valid @RequestBody(required = false) MeetingRoomBookingUpdate meetingRoomBookingUpdate
    );

}
