CREATE INDEX idx_workplace_room_id ON workplace(room_id);
CREATE INDEX idx_workplace_booking_date_workplace_id ON workplace_booking(booking_date, workplace_id);

CREATE INDEX idx_meeting_room_room_id ON meeting_room (room_id);
CREATE INDEX idx_meeting_room_booking_booking_date_range ON meeting_room_booking (booking_date, start_time, end_time, meeting_room_id);
