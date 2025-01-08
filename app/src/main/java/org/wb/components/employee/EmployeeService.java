package org.wb.components.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.wb.components.admin.Admin;
import org.wb.components.admin.AdminRepository;
import org.wb.components.common.EntityService;
import org.wb.components.error.exception.PermissionDeniedException;
import org.wb.components.user.User;
import org.wb.gen.model.EmployeeCreate;
import org.wb.gen.model.EmployeeUpdate;

import jakarta.transaction.Transactional;

@Service
public class EmployeeService
        extends
        EntityService<Employee, org.wb.gen.model.Employee, org.wb.gen.model.Employee, EmployeeCreate, EmployeeUpdate> {
    @Autowired
    private EmployeeRepository repo;
    @Autowired
    private EmployeeMapper mapper;
    @Autowired
    private AdminRepository adminRepo;

    @Override
    protected boolean isListAllowed() {
        return true;
    }

    @Override
    protected boolean isCreateAllowed() {
        return true;
    }

    @Override
    protected boolean isReadAllowed(Employee entity) {
        return true;
    }

    protected boolean isCurrentUserAdmin() {
        var currentUser = userService.getCurrentUser();
        return currentUser.isAdmin();
    }

    @Override
    protected boolean isUpdateAllowed(Employee employee) {
        var currentUser = userService.getCurrentUser();
        return employee.getUser().equals(currentUser) || currentUser.isAdmin();
    }

    @Override
    protected boolean isDeleteAllowed(Employee employee) {
        return isCurrentUserAdmin();
    }

    @Override
    protected String entityName() {
        return "employee";
    }

    @Override
    protected Specification<Employee> additionalSpec() {
        return (root, query, builder) -> {
            // https://stackoverflow.com/a/64823302
            //
            // https://predictly.se/en/complex-jpa-queries-with-specifications/
            // (the 'n+1 loading child entities' section)
            //
            // - Paging uses count
            // - fetch and count don't work together for some reason
            if (query.getResultType() == Long.class) {
                root.join("user");
            } else {
                root.fetch("user");
            }
            return builder.conjunction();
        };
    }

    @Override
    @Transactional
    public org.wb.gen.model.Employee create(EmployeeCreate employeeCreate) {
        var e = mapper.fromCreateDto(employeeCreate);

        var user = User
                .builder()
                .username(employeeCreate.getUsername())
                .password(employeeCreate.getPassword())
                .role(User.Role.EMPLOYEE)
                .build();
        var createdUser = userService.create(user);
        e.setUser(createdUser);

        var createdEmployee = repo.save(e);
        return mapper.toDto(createdEmployee);
    }

    @Transactional
    public void grantAdmin(long id) {
        if (!isCurrentUserAdmin()) {
            throw new PermissionDeniedException("You can't access this resource");
        }

        var employee = getRaw(id);
        var user = employee.getUser();

        var admin = new Admin(null, user, employee.getFullName());
        adminRepo.save(admin);

        repo.delete(employee);

        user.setRole(User.Role.ADMIN);
    }
}
