CREATE OR REPLACE FUNCTION check_workplace_booking_date_in_range()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.booking_date < CURRENT_DATE OR NEW.booking_date >= (CURRENT_DATE + INTERVAL '5' day) THEN
        RAISE EXCEPTION 'Workplace booking date must be within 5 days' USING ERRCODE = 'U0002';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_workplace_booking_date_in_range_trigger
BEFORE INSERT ON workplace_booking
FOR EACH ROW
EXECUTE PROCEDURE check_workplace_booking_date_in_range();


CREATE OR REPLACE FUNCTION check_meeting_room_booking_date_in_range()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.booking_date < CURRENT_DATE OR NEW.booking_date >= (CURRENT_DATE + INTERVAL '5' day) THEN
        RAISE EXCEPTION 'Meeting room booking date must be within 5 days' USING ERRCODE = 'U0003';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_meeting_room_booking_date_in_range_trigger
BEFORE INSERT ON meeting_room_booking
FOR EACH ROW
EXECUTE PROCEDURE check_meeting_room_booking_date_in_range();


CREATE OR REPLACE FUNCTION check_meeting_room_booking_overlap()
RETURNS TRIGGER AS $$
BEGIN
    -- Check for overlaps with existing bookings for the same meeting room on the same day.
    IF EXISTS (
        SELECT 1
        FROM meeting_room_booking
        WHERE meeting_room_booking.meeting_room_id = NEW.meeting_room_id
        AND meeting_room_booking.booking_date = NEW.booking_date
        AND (
            (NEW.start_time < meeting_room_booking.end_time AND NEW.end_time > meeting_room_booking.start_time) -- NEW is "inside"
            OR (NEW.start_time < meeting_room_booking.start_time AND NEW.end_time > meeting_room_booking.start_time) -- NEW ending overlaps
            OR (NEW.start_time < meeting_room_booking.end_time AND NEW.end_time > meeting_room_booking.end_time) -- NEW start overlaps
        )
    ) THEN
        RAISE EXCEPTION 'Meeting room booking overlaps with existing booking' USING ERRCODE = 'U0004';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_meeting_room_booking_overlap_trigger
BEFORE INSERT ON meeting_room_booking
FOR EACH ROW
EXECUTE PROCEDURE check_meeting_room_booking_overlap();


CREATE OR REPLACE FUNCTION check_room_walls_closed()
RETURNS TRIGGER AS $$
DECLARE
    point_counts JSONB[];
    point_count JSONB;
    point JSONB;
    count INTEGER;
BEGIN
    SELECT ARRAY (
        SELECT jsonb_build_object(
            'point', result.point,
            'count', SUM(result.count)
        )
        FROM (
            SELECT jsonb_build_array(room_wall.x1, room_wall.y1) AS point, COUNT(*) as count
            FROM room_wall
            WHERE room_wall.room_id = NEW.room_id
            GROUP BY room_wall.x1, room_wall.y1
            UNION ALL
            SELECT jsonb_build_array(room_wall.x2, room_wall.y2) AS point, COUNT(*) as count
            FROM room_wall
            WHERE room_wall.room_id = NEW.room_id
            GROUP BY room_wall.x2, room_wall.y2
        ) AS result
        GROUP BY result.point
    ) INTO point_counts;

    FOREACH point_count IN ARRAY point_counts
    LOOP
        point := point_count->'point';
        count := point_count->>'count';

        IF count < 2 THEN
            RAISE EXCEPTION 'Point % in room with ID=% must be shared by at least 2 wall, found: %', point, NEW.room_id, count;
        END IF;
    END LOOP;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_room_walls_closed_trigger
BEFORE INSERT OR UPDATE ON room_wall
FOR EACH STATEMENT
EXECUTE PROCEDURE check_room_walls_closed();


CREATE OR REPLACE FUNCTION check_visual_overlap()
RETURNS TRIGGER AS $$
DECLARE
    obj_id INTEGER;
    obj_room_id INTEGER;
    obj_type TEXT;
    new_object JSONB;
    other_object JSONB;
    other_objects JSONB[];
BEGIN
    IF TG_TABLE_NAME = 'workplace_visual' THEN
        obj_type := 'workplace';
        obj_id := NEW.workplace_id;
        SELECT workplace.room_id INTO obj_room_id FROM workplace WHERE workplace.id = obj_id;
    ELSIF TG_TABLE_NAME = 'meeting_room_visual' THEN
        obj_type := 'meeting_room';
        obj_id := NEW.meeting_room_id;
        SELECT meeting_room.room_id INTO obj_room_id FROM meeting_room WHERE meeting_room.id = obj_id;
    END IF;

    new_object := jsonb_build_object(
        'x', NEW.x,
        'y', NEW.y,
        'width', NEW.width,
        'height', NEW.height,
        'type', obj_type,
        'id', obj_id
    );

    -- All other objects
    SELECT ARRAY(
        SELECT jsonb_build_object(
            'x', workplace_visual.x,
            'y', workplace_visual.y,
            'width', workplace_visual.width,
            'height', workplace_visual.height,
            'type', 'workplace',
            'id', workplace_visual.workplace_id
        )
        FROM workplace_visual
        INNER JOIN workplace
        ON workplace.id = workplace_visual.workplace_id
        WHERE workplace.room_id = obj_room_id
        UNION ALL
        SELECT jsonb_build_object(
            'x', meeting_room_visual.x,
            'y', meeting_room_visual.y,
            'width', meeting_room_visual.width,
            'height', meeting_room_visual.height,
            'type', 'meeting_room',
            'id', meeting_room_visual.meeting_room_id
        )
        FROM meeting_room_visual
        INNER JOIN meeting_room
        ON meeting_room.id = meeting_room_visual.meeting_room_id
        WHERE meeting_room.room_id = obj_room_id
    ) INTO other_objects;

    -- Check overlaps
    FOREACH other_object IN ARRAY other_objects LOOP
        IF (
            new_object->>'id' != other_object->>'id' AND -- ignore itself
            NOT (
                (new_object->>'x')::DOUBLE PRECISION > (other_object->>'x')::DOUBLE PRECISION + (other_object->>'width')::DOUBLE PRECISION OR -- NEW is to the right
                (new_object->>'x')::DOUBLE PRECISION + (new_object->>'width')::DOUBLE PRECISION < (other_object->>'x')::DOUBLE PRECISION OR -- NEW is to the left
                (new_object->>'y')::DOUBLE PRECISION > (other_object->>'y')::DOUBLE PRECISION + (other_object->>'height')::DOUBLE PRECISION OR -- NEW is to the bottom
                (new_object->>'y')::DOUBLE PRECISION + (new_object->>'height')::DOUBLE PRECISION < (other_object->>'y')::DOUBLE PRECISION -- NEW is to the top
            )
        ) THEN
            RAISE EXCEPTION 'Collision detected between objects: % and %', new_object, other_object USING ERRCODE = 'U0001';
        END IF;
    END LOOP;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_visual_overlap_workplace_trigger
BEFORE INSERT OR UPDATE ON workplace_visual
FOR EACH ROW
EXECUTE PROCEDURE check_visual_overlap();

CREATE OR REPLACE TRIGGER check_visual_overlap_meeting_room_trigger
BEFORE INSERT OR UPDATE ON meeting_room_visual
FOR EACH ROW
EXECUTE PROCEDURE check_visual_overlap();
