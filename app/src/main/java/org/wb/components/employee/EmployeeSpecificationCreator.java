package org.wb.components.employee;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.wb.components.common.SpecificationCreator;

@Service
public class EmployeeSpecificationCreator extends SpecificationCreator<Employee> {
    private static final Set<String> VALID_SEARCH_FIELDS = Set.of("id", "user.username", "fullName");

    @Override
    public boolean isFieldValid(String fieldName) {
        return VALID_SEARCH_FIELDS.contains(fieldName);
    }
}
