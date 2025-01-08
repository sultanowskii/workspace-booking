package org.wb.components.meetingroom.booking;

import java.time.LocalDate;
import java.util.Set;

import org.wb.components.common.EntitySpecificationBuilder;

public class MeetingRoomBookingSpecificationBuilder extends EntitySpecificationBuilder<MeetingRoomBooking> {
    private static final Set<String> VALID_SEARCH_FIELDS = Set.of("description");

    @Override
    public boolean isFieldValid(String fieldName) {
        return VALID_SEARCH_FIELDS.contains(fieldName);
    }

    public MeetingRoomBookingSpecificationBuilder withMeetingRoomId(Long meetingRoomId) {
        if (meetingRoomId == null) {
            return this;
        }
        andOtherSpec((root, query, builder) -> {
            return builder.equal(root.get("meetingRoom").get("id"), meetingRoomId);
        });
        return this;
    }

    public MeetingRoomBookingSpecificationBuilder withEmployeeId(Long employeeId) {
        if (employeeId == null) {
            return this;
        }
        andOtherSpec((root, query, builder) -> {
            return builder.equal(root.get("employee").get("id"), employeeId);
        });
        return this;
    }

    public MeetingRoomBookingSpecificationBuilder withDate(LocalDate date) {
        if (date == null) {
            return this;
        }
        andOtherSpec((root, query, builder) -> {
            return builder.equal(root.get("bookingDate"), date);
        });
        return this;
    }
}
