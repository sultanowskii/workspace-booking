package org.wb.components.meetingroom;

import java.util.Set;

import org.wb.components.common.EntitySpecificationBuilder;

public class MeetingRoomSpecificationBuilder extends EntitySpecificationBuilder<MeetingRoom> {
    private static final Set<String> VALID_SEARCH_FIELDS = Set.of("name");

    @Override
    public boolean isFieldValid(String fieldName) {
        return VALID_SEARCH_FIELDS.contains(fieldName);
    }

    public MeetingRoomSpecificationBuilder withRoomId(long roomId) {
        andOtherSpec((root, query, builder) -> {
            return builder.equal(root.get("room").get("id"), roomId);
        });
        return this;
    }
}
