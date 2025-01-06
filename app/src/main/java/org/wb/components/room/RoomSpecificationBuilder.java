package org.wb.components.room;

import java.util.Set;

import org.wb.components.common.EntitySpecificationBuilder;

public class RoomSpecificationBuilder extends EntitySpecificationBuilder<Room> {
    private static final Set<String> VALID_SEARCH_FIELDS = Set.of("name");

    @Override
    public boolean isFieldValid(String fieldName) {
        return VALID_SEARCH_FIELDS.contains(fieldName);
    }

    public RoomSpecificationBuilder withOfficeId(long officeId) {
        andOtherSpec((root, query, builder) -> {
            return builder.equal(root.get("office").get("id"), officeId);
        });
        return this;
    }
}
