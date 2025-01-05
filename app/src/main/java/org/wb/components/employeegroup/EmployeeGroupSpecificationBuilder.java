package org.wb.components.employeegroup;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.wb.components.common.EntitySpecificationBuilder;

import jakarta.persistence.criteria.JoinType;

@Service
public class EmployeeGroupSpecificationBuilder extends EntitySpecificationBuilder<EmployeeGroup> {
    private static final Set<String> VALID_SEARCH_FIELDS = Set.of("name");

    @Override
    public boolean isFieldValid(String fieldName) {
        return VALID_SEARCH_FIELDS.contains(fieldName);
    }

    public EmployeeGroupSpecificationBuilder withOfficeId(Long officeId) {
        if (officeId == null) {
            return this;
        }

        andOtherSpec(
                (root, query, builder) -> {
                    var join = root.join("allowedOffices", JoinType.INNER);
                    return builder.equal(join.get("id"), officeId);
                });

        return this;
    }
}
