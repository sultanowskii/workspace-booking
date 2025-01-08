package org.wb.components.meetingroom.booking;

import java.time.LocalDate;
import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wb.components.common.paging.Paginator;
import org.wb.gen.api.MeetingRoomBookingsApi;
import org.wb.gen.model.MeetingRoomBooking;
import org.wb.gen.model.MeetingRoomBookingCreate;
import org.wb.gen.model.MeetingRoomBookingShort;
import org.wb.gen.model.MeetingRoomBookingUpdate;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

@Controller
@RequestMapping("${openapi.workspaceBooking.base-path:}")
public class MeetingRoomBookingApiController implements MeetingRoomBookingsApi {
    @Autowired
    protected MeetingRoomBookingService service;

    @Override
    public ResponseEntity<List<MeetingRoomBookingShort>> getMeetingRoomBookings(
            @Parameter(name = "organizerId", description = "Organizer ID (employee)", in = ParameterIn.QUERY) @Valid @RequestParam(value = "organizerId", required = false) Long organizerId,
            @Parameter(name = "meetingRoomId", description = "Meeting Room ID", in = ParameterIn.QUERY) @Valid @RequestParam(value = "meetingRoomId", required = false) Long meetingRoomId,
            @Parameter(name = "date", description = "Date", in = ParameterIn.QUERY) @Valid @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @Parameter(name = "searchFieldName", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "searchFieldName", required = false) String searchFieldName,
            @Size(min = 1) @Parameter(name = "searchString", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "searchString", required = false) String searchString,
            @ParameterObject final Pageable pageable) {
        var spec = new MeetingRoomBookingSpecificationBuilder()
                .withEmployeeId(organizerId)
                .withMeetingRoomId(meetingRoomId)
                .withDate(date)
                .withFieldContaining(searchFieldName, searchString)
                .build();
        var paginator = Paginator.from(pageable);
        var result = service.getAll(spec, paginator);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<MeetingRoomBooking> createMeetingRoomBooking(
            @Valid MeetingRoomBookingCreate meetingRoomBookingCreate) {
        var result = service.create(meetingRoomBookingCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    public ResponseEntity<MeetingRoomBooking> getMeetingRoomBooking(Long id) {
        var result = service.get(id);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<MeetingRoomBooking> updateMeetingRoomBooking(Long id,
            @Valid MeetingRoomBookingUpdate meetingRoomBookingUpdate) {
        var result = service.update(id, meetingRoomBookingUpdate);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Void> deleteMeetingRoomBooking(Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
