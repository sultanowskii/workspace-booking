package org.wb.components.office;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.wb.components.common.EntityService;
import org.wb.components.employeegroup.EmployeeGroupService;
import org.wb.gen.model.OfficeCreateUpdate;

import jakarta.persistence.criteria.JoinType;

import org.springframework.transaction.annotation.Transactional;

@Service
public class OfficeService
        extends
        EntityService<Office, org.wb.gen.model.OfficeWithEmployeeGroups, org.wb.gen.model.OfficeWithEmployeeGroups, OfficeCreateUpdate, OfficeCreateUpdate> {
    @Autowired
    private EmployeeGroupService employeeGroupService;

    @Override
    protected boolean isListAllowed() {
        return true;
    }

    @Override
    protected boolean isCreateAllowed() {
        return isCurrentUserAdmin();
    }

    @Override
    protected boolean isReadAllowed(Office entity) {
        return true;
    }

    @Override
    protected boolean isUpdateAllowed(Office entity) {
        return isCurrentUserAdmin();
    }

    @Override
    protected boolean isDeleteAllowed(Office entity) {
        return isCurrentUserAdmin();
    }

    @Override
    protected Specification<Office> additionalListSpec() {
        return (root, query, builder) -> {
            root.fetch("employeeGroups", JoinType.LEFT);
            return builder.conjunction();
        };
    }

    @Override
    protected Specification<Office> additionalGetSpec() {
        return (root, query, builder) -> {
            root.fetch("employeeGroups", JoinType.LEFT);
            return builder.conjunction();
        };
    }

    @Override
    protected String entityName() {
        return "office";
    }

    @Transactional
    protected void addEmployeeGroupToOffice(Long officeId, Long employeeGroupId) {
        var employeeGroup = employeeGroupService.getRaw(employeeGroupId);
        var office = getRaw(officeId);

        office.getEmployeeGroups().add(employeeGroup);
        employeeGroup.getAllowedOffices().add(office);
        repo.save(office);
    }

    @Transactional
    protected void removeEmployeeGroupToOffice(Long officeId, Long employeeGroupId) {
        var employeeGroup = employeeGroupService.getRaw(employeeGroupId);
        var office = getRaw(officeId);

        office.getEmployeeGroups().remove(employeeGroup);
        employeeGroup.getAllowedOffices().remove(office);
        repo.save(office);
    }
}
