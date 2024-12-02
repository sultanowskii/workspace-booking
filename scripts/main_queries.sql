SELECT 
    w.*,
    wv.*,
    CASE
        WHEN wb.id IS NOT NULL
        THEN TRUE
        ELSE FALSE
    END AS booked
FROM workplace w
LEFT JOIN workplace_booking wb ON wb.workplace_id = w.id AND wb.booking_date = '2024-11-01'
INNER JOIN workplace_visual wv ON wv.workplace_id = w.id
WHERE w.room_id = 4;

SELECT
    mr.*,
    mrv.*,
    mrb.*,
    CASE
        WHEN mrb.id IS NOT NULL
        THEN TRUE
        ELSE FALSE
    END AS booked
FROM meeting_room mr
LEFT JOIN meeting_room_booking mrb
    ON mrb.meeting_room_id = mr.id
    AND mrb.booking_date = '2024-11-01'
    AND mrb.start_time <= '13:00:00'
    AND mrb.end_time > '13:00:00'
INNER JOIN meeting_room_visual mrv ON mrv.meeting_room_id = mr.id
WHERE mr.room_id = 4;
