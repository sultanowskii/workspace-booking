package org.wb.components.workplace;

import java.util.Set;

import org.wb.components.common.EntitySpecificationBuilder;

public class WorkplaceSpecificationBuilder extends EntitySpecificationBuilder<Workplace> {
    private static final Set<String> VALID_SEARCH_FIELDS = Set.of("numberOfMonitors");

    @Override
    public boolean isFieldValid(String fieldName) {
        return VALID_SEARCH_FIELDS.contains(fieldName);
    }

    public WorkplaceSpecificationBuilder withRoomId(long roomId) {
        andOtherSpec((root, query, builder) -> {
            return builder.equal(root.get("room").get("id"), roomId);
        });
        return this;
    }
}
