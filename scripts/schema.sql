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
