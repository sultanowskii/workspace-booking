CREATE INDEX IF NOT EXISTS idx_workplace_room_id ON workplace(room_id);
CREATE INDEX IF NOT EXISTS idx_workplace_booking_workplace_id ON workplace_booking(workplace_id);

CREATE INDEX IF NOT EXISTS idx_meeting_room_room_id ON meeting_room (room_id);
CREATE INDEX IF NOT EXISTS idx_meeting_room_booking_meeting_room_id ON meeting_room_booking (meeting_room_id);
