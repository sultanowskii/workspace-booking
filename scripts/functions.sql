CREATE OR REPLACE FUNCTION get_room_occupancy(target_room_id INTEGER, target_date DATE)
RETURNS DOUBLE PRECISION
AS $$
DECLARE
    occupied_workplace_count DOUBLE PRECISION;
    total_workplace_count DOUBLE PRECISION;
BEGIN
    SELECT COUNT(workplace.*)
    INTO occupied_workplace_count
    FROM workplace
    INNER JOIN workplace_booking ON workplace_booking.workplace_id = workplace.id
    WHERE workplace.room_id = target_room_id
    AND workplace_booking.booking_date = target_date;

    SELECT COUNT(workplace.*)
    INTO total_workplace_count
    FROM workplace
    WHERE workplace.room_id = target_room_id;

    IF total_workplace_count = 0::DOUBLE PRECISION THEN
        RETURN 0;
    END IF;

    RETURN occupied_workplace_count / total_workplace_count;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_office_occupancy(target_office_id INTEGER, target_date DATE)
RETURNS DOUBLE PRECISION
AS $$
DECLARE
    occupied_workplace_count DOUBLE PRECISION;
    total_workplace_count DOUBLE PRECISION;
BEGIN
    SELECT COUNT(workplace.*)
    INTO occupied_workplace_count
    FROM workplace
    INNER JOIN room ON room.id = workplace.room_id
    INNER JOIN workplace_booking ON workplace_booking.workplace_id = workplace.id
    WHERE room.office_id = target_office_id
    AND workplace_booking.booking_date = target_date;

    SELECT COUNT(workplace.*)
    INTO total_workplace_count
    FROM workplace
    INNER JOIN room ON room.id = workplace.room_id
    WHERE room.office_id = target_office_id;

    IF total_workplace_count = 0::DOUBLE PRECISION THEN
        RETURN 0;
    END IF;

    RETURN occupied_workplace_count / total_workplace_count;
END;
$$ LANGUAGE plpgsql;
