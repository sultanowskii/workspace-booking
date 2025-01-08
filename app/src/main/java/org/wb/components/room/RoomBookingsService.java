package org.wb.components.room;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wb.components.meetingroom.booking.MeetingRoomBookingMapper;
import org.wb.components.meetingroom.booking.MeetingRoomBookingRepository;
import org.wb.components.workplace.booking.WorkplaceBookingMapper;
import org.wb.components.workplace.booking.WorkplaceBookingRepository;
import org.wb.gen.model.RoomBookings;

@Service
public class RoomBookingsService {
    @Autowired
    protected WorkplaceBookingRepository workplaceBookingRepository;
    @Autowired
    protected MeetingRoomBookingRepository meetingRoomBookingRepository;
    @Autowired
    protected WorkplaceBookingMapper workplaceBookingMapper;
    @Autowired
    protected MeetingRoomBookingMapper meetingRoomBookingMapper;

    public RoomBookings getRoomBookings(long id, OffsetDateTime date) {
        var workplaceBookings = workplaceBookingRepository.findAll((root, query, builder) -> {
            root.fetch("workplace");
            return builder.and(
                    builder.equal(root.get("bookingDate"), date.toLocalDate()),
                    builder.equal(root.get("workplace").get("room").get("id"), id));
        });

        var meetingRoomBookings = meetingRoomBookingRepository.findAll((root, query, builder) -> {
            root.fetch("meetingRoom");
            return builder.and(
                    builder.equal(root.get("bookingDate"), date.toLocalDate()),
                    builder.lessThanOrEqualTo(root.get("startTime"), date.toLocalTime()),
                    builder.greaterThanOrEqualTo(root.get("endTime"), date.toLocalTime()),
                    builder.equal(root.get("meetingRoom").get("room").get("id"), id));
        });

        return new RoomBookings()
                .id(id)
                .workplaceBookings(workplaceBookings.stream().map(w -> workplaceBookingMapper.toDto(w)).toList())
                .meetingRoomBookings(
                        meetingRoomBookings.stream().map(m -> meetingRoomBookingMapper.toListDto(m)).toList());
    }
}
