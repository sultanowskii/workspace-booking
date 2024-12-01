INSERT INTO office (name, address) VALUES
('Head Office', '123 Main St, Anytown'),
('Branch Office', '456 Oak Ave, Otherville');

INSERT INTO employee_group (name) VALUES
('Engineering'),
('Sales'),
('Marketing');

INSERT INTO users (username, password, role) VALUES
('john.doe', 'password123', 'EMPLOYEE'),  -- hashed passwords?
('jane.doe', 'securepass', 'EMPLOYEE'),
('admin', 'adminpass', 'ADMIN');

INSERT INTO employee (user_id, employee_group_id, full_name) VALUES
((SELECT id FROM users WHERE username = 'john.doe'), (SELECT id FROM employee_group WHERE name = 'Engineering'), 'John Doe'),
((SELECT id FROM users WHERE username = 'jane.doe'), (SELECT id FROM employee_group WHERE name = 'Sales'), 'Jane Doe');

INSERT INTO office_employee_group (office_id, employee_group_id) VALUES
((SELECT id FROM office WHERE name = 'Head Office'), (SELECT id FROM employee_group WHERE name = 'Engineering')),
((SELECT id FROM office WHERE name = 'Branch Office'), (SELECT id FROM employee_group WHERE name = 'Sales'));

INSERT INTO room (office_id, name) VALUES
((SELECT id FROM office WHERE name = 'Head Office'), 'Room A'),
((SELECT id FROM office WHERE name = 'Head Office'), 'Room B'),
((SELECT id FROM office WHERE name = 'Branch Office'), 'Room 1');

INSERT INTO meeting_room (room_id, name) VALUES
((SELECT id FROM room WHERE name = 'Room A'), 'Meeting room A'),
((SELECT id FROM room WHERE name = 'Room B'), 'Meeting room B');

INSERT INTO workplace (room_id, number_of_monitors) VALUES
((SELECT id FROM room WHERE name = 'Room 1'), 2),
((SELECT id FROM room WHERE name = 'Room A'), 0);      

INSERT INTO room_wall (room_id, x1, y1, x2, y2) VALUES
((SELECT id FROM room WHERE name = 'Room A'), 0, 0, 0, 10),
((SELECT id FROM room WHERE name = 'Room A'), 0, 10, 10, 10),
((SELECT id FROM room WHERE name = 'Room A'), 10, 10, 10, 0),
((SELECT id FROM room WHERE name = 'Room A'), 10, 0, 0, 0);

INSERT INTO meeting_room_visual (meeting_room_id, x, y, width, height) VALUES
((SELECT id FROM meeting_room WHERE name = 'Meeting room A'), 1, 1, 5, 4);

INSERT INTO workplace_visual (workplace_id, x, y, width, height) VALUES
((SELECT id FROM workplace WHERE number_of_monitors = 2), 2, 2, 3, 2);

INSERT INTO workplace_booking (employee_id, workplace_id, booking_date) VALUES
((SELECT id FROM employee WHERE full_name = 'John Doe'), (SELECT id FROM workplace WHERE number_of_monitors = 2), '2024-11-16');

INSERT INTO meeting_room_booking (employee_id, meeting_room_id, booking_date, start_time, end_time, description) VALUES
((SELECT id FROM employee WHERE full_name = 'Jane Doe'), (SELECT id FROM meeting_room WHERE name = 'Meeting room A'), '2024-11-16', '10:00', '11:00', 'Team Meeting');

INSERT INTO meeting_participant (meeting_room_booking_id, employee_id) VALUES
((SELECT id FROM meeting_room_booking WHERE description = 'Team Meeting'), (SELECT id FROM employee WHERE full_name = 'John Doe')),
((SELECT id FROM meeting_room_booking WHERE description = 'Team Meeting'), (SELECT id FROM employee WHERE full_name = 'Jane Doe'));

INSERT INTO admin (user_id, full_name) VALUES
((SELECT id FROM users WHERE username = 'admin'), 'Nick Hosh');
