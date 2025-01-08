package org.wb.db;

import lombok.Getter;

@Getter
public enum DBError {
    VISUAL_OBJECTS_COLLIDE("U0001", "Visual objects collide"),
    WORKPLACE_BOOKING_DATE_OUTSIDE_RANGE("U0002", "Workplace booking date must be within 5 days"),
    MEETING_ROOM_BOOKING_DATE_OUTSIDE_RANGE("U0003", "Meeting room booking date must be within 5 days");

    private final String code;
    private final String description;

    DBError(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static DBError valueFrom(String code) {
        for (var v : values()) {
            if (v.getCode().equals(code)) {
                return v;
            }
        }
        return null;
    }
}
