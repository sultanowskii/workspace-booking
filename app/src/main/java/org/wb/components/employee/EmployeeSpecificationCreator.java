package org.wb.components.employee;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.wb.components.common.EntitySpecificationCreator;

@Service
public class EmployeeSpecificationCreator extends EntitySpecificationCreator<Employee> {
    private static final Set<String> VALID_SEARCH_FIELDS = Set.of("user.username", "fullName");

    @Override
    public boolean isFieldValid(String fieldName) {
        return VALID_SEARCH_FIELDS.contains(fieldName);
    }
}
