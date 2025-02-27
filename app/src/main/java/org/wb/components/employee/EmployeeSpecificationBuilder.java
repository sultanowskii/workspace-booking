package org.wb.components.employee;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.wb.components.common.EntitySpecificationBuilder;

@Service
public class EmployeeSpecificationBuilder extends EntitySpecificationBuilder<Employee> {
    private static final Set<String> VALID_SEARCH_FIELDS = Set.of("user.username", "fullName");

    @Override
    public boolean isFieldValid(String fieldName) {
        return VALID_SEARCH_FIELDS.contains(fieldName);
    }

    public EmployeeSpecificationBuilder withEmployeeGroupId(Long employeeGroupId) {
        if (employeeGroupId == null) {
            return this;
        }

        andOtherSpec((root, query, builder) -> {
            return builder.equal(root.get("employeeGroup").get("id"), employeeGroupId);
        });
        return this;
    }
}
