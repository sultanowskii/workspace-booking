CREATE TABLE IF NOT EXISTS office (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE CHECK (length(name) > 0),
    address TEXT NOT NULL CHECK (length(address) > 0)
);

CREATE TABLE IF NOT EXISTS room (
    id SERIAL PRIMARY KEY,
    office_id INTEGER NOT NULL REFERENCES office(id) ON DELETE CASCADE,
    name TEXT NOT NULL CHECK (length(name) > 0) -- TODO: unique in the office
);

CREATE TABLE IF NOT EXISTS room_wall (
    id SERIAL PRIMARY KEY,
    room_id INTEGER NOT NULL REFERENCES room(id) ON DELETE CASCADE,
    x1 DOUBLE PRECISION NOT NULL,
    y1 DOUBLE PRECISION NOT NULL,
    x2 DOUBLE PRECISION NOT NULL,
    y2 DOUBLE PRECISION NOT NULL
);

CREATE TABLE IF NOT EXISTS meeting_room (
    id SERIAL PRIMARY KEY,
    room_id INTEGER NOT NULL REFERENCES room(id) ON DELETE CASCADE,
    name TEXT NOT NULL CHECK (length(name) > 0) -- TODO: unique in the office
);

CREATE TABLE IF NOT EXISTS meeting_room_visual (
    meeting_room_id INTEGER PRIMARY KEY REFERENCES meeting_room(id) ON DELETE CASCADE,
    x DOUBLE PRECISION NOT NULL,
    y DOUBLE PRECISION NOT NULL,
    width DOUBLE PRECISION NOT NULL CHECK (width > 0),
    height DOUBLE PRECISION NOT NULL CHECK (height > 0)
);

CREATE TABLE IF NOT EXISTS workplace (
    id SERIAL PRIMARY KEY,
    room_id INTEGER NOT NULL REFERENCES room(id) ON DELETE CASCADE,
    number_of_monitors INTEGER NOT NULL CHECK (number_of_monitors >= 0)
);
ALTER SEQUENCE room_wall_id_seq INCREMENT BY 16;

CREATE TABLE IF NOT EXISTS workplace_visual (
    workplace_id INTEGER PRIMARY KEY REFERENCES workplace(id) ON DELETE CASCADE,
    x DOUBLE PRECISION NOT NULL,
    y DOUBLE PRECISION NOT NULL, 
    width DOUBLE PRECISION NOT NULL CHECK (width > 0),
    height DOUBLE PRECISION NOT NULL CHECK (height > 0)
);

CREATE TABLE IF NOT EXISTS employee_group (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE CHECK (length(name) > 0)
);

CREATE TABLE IF NOT EXISTS office_employee_group (
    id SERIAL PRIMARY KEY,
    office_id INTEGER NOT NULL REFERENCES office(id) ON DELETE CASCADE,
    employee_group_id INTEGER NOT NULL REFERENCES employee_group(id) ON DELETE CASCADE,

    UNIQUE (office_id, employee_group_id)
);

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY, 
    username TEXT NOT NULL UNIQUE CHECK (length(username) BETWEEN 3 AND 32), 
    password TEXT NOT NULL UNIQUE CHECK (length(password) BETWEEN 8 AND 64), 
    role TEXT NOT NULL CHECK (role in ('EMPLOYEE', 'ADMIN'))
);

CREATE TABLE IF NOT EXISTS employee (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    employee_group_id INTEGER REFERENCES employee_group(id) ON DELETE SET NULL,
    full_name TEXT NOT NULL CHECK (length(full_name) > 0)
);

CREATE TABLE IF NOT EXISTS admin (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    full_name TEXT NOT NULL CHECK (length(full_name) > 0)
);

CREATE TABLE IF NOT EXISTS workplace_booking (
    id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES employee(id) ON DELETE CASCADE,
    workplace_id INTEGER NOT NULL REFERENCES workplace(id) ON DELETE CASCADE,
    booking_date DATE NOT NULL,

    UNIQUE (workplace_id, booking_date),
    UNIQUE (employee_id, booking_date)
);

CREATE TABLE IF NOT EXISTS meeting_room_booking (
    id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES employee(id) ON DELETE CASCADE,
    meeting_room_id INTEGER NOT NULL REFERENCES meeting_room(id) ON DELETE CASCADE,
    booking_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    description TEXT NOT NULL CHECK (length(description) > 0),

    CHECK (start_time < end_time)
);

CREATE TABLE IF NOT EXISTS meeting_participant (
    id SERIAL PRIMARY KEY,
    meeting_room_booking_id INTEGER NOT NULL REFERENCES meeting_room_booking(id) ON DELETE CASCADE,
    employee_id INTEGER NOT NULL REFERENCES employee(id) ON DELETE CASCADE,

    UNIQUE (meeting_room_booking_id, employee_id)
);
CREATE INDEX IF NOT EXISTS idx_workplace_room_id ON workplace(room_id);
CREATE INDEX IF NOT EXISTS idx_workplace_booking_workplace_id ON workplace_booking(workplace_id);

CREATE INDEX IF NOT EXISTS idx_meeting_room_room_id ON meeting_room (room_id);
CREATE INDEX IF NOT EXISTS idx_meeting_room_booking_meeting_room_id ON meeting_room_booking (meeting_room_id);
CREATE OR REPLACE FUNCTION get_room_occupancy(target_room_id INTEGER, target_date DATE)
RETURNS DOUBLE PRECISION
AS '
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
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_office_occupancy(target_office_id INTEGER, target_date DATE)
RETURNS DOUBLE PRECISION
AS '
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
' LANGUAGE plpgsql;
CREATE OR REPLACE FUNCTION check_workplace_booking_date_in_range()
RETURNS TRIGGER AS '
BEGIN
    IF NEW.booking_date < CURRENT_DATE OR NEW.booking_date >= (CURRENT_DATE + INTERVAL ''5'' day) THEN
        RAISE EXCEPTION ''Workplace booking date must be within 5 days'' USING ERRCODE = ''U0002'';
    END IF;

    RETURN NEW;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_workplace_booking_date_in_range_trigger
BEFORE INSERT ON workplace_booking
FOR EACH ROW
EXECUTE PROCEDURE check_workplace_booking_date_in_range();


CREATE OR REPLACE FUNCTION check_meeting_room_booking_date_in_range()
RETURNS TRIGGER AS '
BEGIN
    IF NEW.booking_date < CURRENT_DATE OR NEW.booking_date >= (CURRENT_DATE + INTERVAL ''5'' day) THEN
        RAISE EXCEPTION ''Meeting room booking date must be within 5 days'' USING ERRCODE = ''U0003'';
    END IF;

    RETURN NEW;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_meeting_room_booking_date_in_range_trigger
BEFORE INSERT ON meeting_room_booking
FOR EACH ROW
EXECUTE PROCEDURE check_meeting_room_booking_date_in_range();


CREATE OR REPLACE FUNCTION check_meeting_room_booking_overlap()
RETURNS TRIGGER AS '
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
        RAISE EXCEPTION ''Meeting room booking overlaps with existing booking'' USING ERRCODE = ''U0004'';
    END IF;

    RETURN NEW;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_meeting_room_booking_overlap_trigger
BEFORE INSERT ON meeting_room_booking
FOR EACH ROW
EXECUTE PROCEDURE check_meeting_room_booking_overlap();


CREATE OR REPLACE FUNCTION check_room_walls_closed()
RETURNS TRIGGER AS '
DECLARE
    point_counts JSONB[];
    point_count JSONB;
    point JSONB;
    count INTEGER;
BEGIN
    SELECT ARRAY (
        SELECT jsonb_build_object(
            ''point'', result.point,
            ''count'', SUM(result.count)
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
        point := point_count->''point'';
        count := point_count->>''count'';

        IF count < 2 THEN
            RAISE EXCEPTION ''Point % in room with ID=% must be shared by at least 2 wall, found: %'', point, NEW.room_id, count;
        END IF;
    END LOOP;

    RETURN NEW;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_room_walls_closed_trigger
BEFORE INSERT OR UPDATE ON room_wall
FOR EACH STATEMENT
EXECUTE PROCEDURE check_room_walls_closed();


CREATE OR REPLACE FUNCTION check_visual_overlap()
RETURNS TRIGGER AS '
DECLARE
    obj_id INTEGER;
    obj_room_id INTEGER;
    obj_type TEXT;
    new_object JSONB;
    other_object JSONB;
    other_objects JSONB[];
BEGIN
    IF TG_TABLE_NAME = ''workplace_visual'' THEN
        obj_type := ''workplace'';
        obj_id := NEW.workplace_id;
        SELECT workplace.room_id INTO obj_room_id FROM workplace WHERE workplace.id = obj_id;
    ELSIF TG_TABLE_NAME = ''meeting_room_visual'' THEN
        obj_type := ''meeting_room'';
        obj_id := NEW.meeting_room_id;
        SELECT meeting_room.room_id INTO obj_room_id FROM meeting_room WHERE meeting_room.id = obj_id;
    END IF;

    new_object := jsonb_build_object(
        ''x'', NEW.x,
        ''y'', NEW.y,
        ''width'', NEW.width,
        ''height'', NEW.height,
        ''type'', obj_type,
        ''id'', obj_id
    );

    -- All other objects
    SELECT ARRAY(
        SELECT jsonb_build_object(
            ''x'', workplace_visual.x,
            ''y'', workplace_visual.y,
            ''width'', workplace_visual.width,
            ''height'', workplace_visual.height,
            ''type'', ''workplace'',
            ''id'', workplace_visual.workplace_id
        )
        FROM workplace_visual
        INNER JOIN workplace
        ON workplace.id = workplace_visual.workplace_id
        WHERE workplace.room_id = obj_room_id
        UNION ALL
        SELECT jsonb_build_object(
            ''x'', meeting_room_visual.x,
            ''y'', meeting_room_visual.y,
            ''width'', meeting_room_visual.width,
            ''height'', meeting_room_visual.height,
            ''type'', ''meeting_room'',
            ''id'', meeting_room_visual.meeting_room_id
        )
        FROM meeting_room_visual
        INNER JOIN meeting_room
        ON meeting_room.id = meeting_room_visual.meeting_room_id
        WHERE meeting_room.room_id = obj_room_id
    ) INTO other_objects;

    -- Check overlaps
    FOREACH other_object IN ARRAY other_objects LOOP
        IF (
            new_object->>''id'' != other_object->>''id'' AND -- ignore itself
            NOT (
                (new_object->>''x'')::DOUBLE PRECISION > (other_object->>''x'')::DOUBLE PRECISION + (other_object->>''width'')::DOUBLE PRECISION OR -- NEW is to the right
                (new_object->>''x'')::DOUBLE PRECISION + (new_object->>''width'')::DOUBLE PRECISION < (other_object->>''x'')::DOUBLE PRECISION OR -- NEW is to the left
                (new_object->>''y'')::DOUBLE PRECISION > (other_object->>''y'')::DOUBLE PRECISION + (other_object->>''height'')::DOUBLE PRECISION OR -- NEW is to the bottom
                (new_object->>''y'')::DOUBLE PRECISION + (new_object->>''height'')::DOUBLE PRECISION < (other_object->>''y'')::DOUBLE PRECISION -- NEW is to the top
            )
        ) THEN
            RAISE EXCEPTION ''Collision detected between objects: % and %'', new_object, other_object USING ERRCODE = ''U0001'';
        END IF;
    END LOOP;

    RETURN NEW;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_visual_overlap_workplace_trigger
BEFORE INSERT OR UPDATE ON workplace_visual
FOR EACH ROW
EXECUTE PROCEDURE check_visual_overlap();

CREATE OR REPLACE TRIGGER check_visual_overlap_meeting_room_trigger
BEFORE INSERT OR UPDATE ON meeting_room_visual
FOR EACH ROW
EXECUTE PROCEDURE check_visual_overlap();
