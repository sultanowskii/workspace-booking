package org.wb.components.office;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.wb.components.common.EntitySpecificationBuilder;

import jakarta.persistence.criteria.JoinType;

@Service
public class OfficeSpecificationBuilder extends EntitySpecificationBuilder<Office> {
    private static final Set<String> VALID_SEARCH_FIELDS = Set.of("name", "address");

    @Override
    public boolean isFieldValid(String fieldName) {
        return VALID_SEARCH_FIELDS.contains(fieldName);
    }

    public OfficeSpecificationBuilder withEmployeeGroupId(Long employeeGroupId) {
        if (employeeGroupId == null) {
            return this;
        }
        andOtherSpec(
                (root, query, builder) -> {
                    var join = root.join("employeeGroups", JoinType.INNER);
                    return builder.equal(join.get("id"), employeeGroupId);
                });
        return this;
    }
}
