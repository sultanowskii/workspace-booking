package org.wb.db;

import lombok.Getter;

@Getter
public enum DBError {
    VISUAL_OBJECTS_COLLIDE("U0001", "Visual objects collide");

    private final String code;
    private final String description;

    DBError(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
