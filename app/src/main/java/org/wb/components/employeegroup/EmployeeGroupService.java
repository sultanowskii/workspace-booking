package org.wb.components.employeegroup;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.wb.components.common.EntityService;
import org.wb.gen.model.EmployeeGroupCreateUpdate;

@Service
public class EmployeeGroupService extends
        EntityService<EmployeeGroup, org.wb.gen.model.EmployeeGroupWithAllowedOffices, org.wb.gen.model.EmployeeGroupWithAllowedOffices, EmployeeGroupCreateUpdate, EmployeeGroupCreateUpdate> {

    @Override
    protected boolean isListAllowed() {
        return true;
    }

    @Override
    protected boolean isCreateAllowed() {
        return isCurrentUserAdmin();
    }

    @Override
    protected boolean isReadAllowed(EmployeeGroup entity) {
        return true;
    }

    @Override
    protected boolean isUpdateAllowed(EmployeeGroup entity) {
        return isCurrentUserAdmin();
    }

    @Override
    protected boolean isDeleteAllowed(EmployeeGroup entity) {
        return isCurrentUserAdmin();
    }

    @Override
    protected Specification<EmployeeGroup> additionalListSpec() {
        return (root, query, builder) -> {
            root.fetch("allowedOffices");
            return builder.conjunction();
        };
    }

    @Override
    protected Specification<EmployeeGroup> additionalGetSpec() {
        return (root, query, builder) -> {
            root.fetch("allowedOffices");
            return builder.conjunction();
        };
    }

    @Override
    protected String entityName() {
        return "employee group";
    }
}
