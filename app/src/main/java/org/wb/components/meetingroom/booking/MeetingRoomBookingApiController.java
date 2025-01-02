package org.wb.components.meetingroom.booking;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wb.gen.api.MeetingRoomBookingsApi;
import org.wb.gen.model.MeetingRoomBooking;
import org.wb.gen.model.MeetingRoomBookingCreate;
import org.wb.gen.model.MeetingRoomBookingShort;
import org.wb.gen.model.MeetingRoomBookingUpdate;

import jakarta.validation.Valid;

@Controller
@RequestMapping("${openapi.workplaceBooking.base-path:}")
public class MeetingRoomBookingApiController implements MeetingRoomBookingsApi {
    @Override
    public ResponseEntity<List<MeetingRoomBookingShort>> getMeetingRoomBookings(@Valid Long organizerId,
            @Valid Long meetingRoomId, @Valid LocalDate date) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Void> createMeetingRoomBooking(@Valid MeetingRoomBookingCreate meetingRoomBookingCreate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<MeetingRoomBooking> getMeetingRoomBooking(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<MeetingRoomBooking> updateMeetingRoomBooking(Long id,
            @Valid MeetingRoomBookingUpdate meetingRoomBookingUpdate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteMeetingRoomBooking(Long id) {
        // TODO Auto-generated method stub
        return null;
    }
}
