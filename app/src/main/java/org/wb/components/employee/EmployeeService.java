package org.wb.components.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.wb.components.common.paging.Paginator;
import org.wb.components.common.paging.SmartPage;
import org.wb.components.user.User;
import org.wb.components.user.UserService;
import org.wb.gen.model.EmployeeCreate;
import org.wb.gen.model.EmployeeUpdate;

import jakarta.transaction.Transactional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository repo;
    @Autowired
    private EmployeeMapper mapper;
    @Autowired
    private UserService userService;

    public static Specification<Employee> getAllSpecification() {
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
            return builder.ge(root.get("id"), 0);
        };
    }

    public List<org.wb.gen.model.Employee> getAll(Specification<Employee> specification,
            Paginator paginator) {
        var result = repo
                .findAll(getAllSpecification().and(specification), paginator.getSort());

        var paged = new SmartPage<>(result, paginator);

        return paged
                .map(e -> mapper.toDto(e))
                .toList();
    }

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

    public org.wb.gen.model.Employee get(long id) {
        var employee = repo.findById(id).orElse(null); // TODO: error
        return mapper.toDto(employee);
    }

    @Transactional
    public org.wb.gen.model.Employee update(long id, EmployeeUpdate employeeUpdate) {
        var employee = repo
                .findById(id)
                .orElse(null); // TODO: error

        var currentUser = userService.getCurrentUser();

        if (!employee.getUser().equals(currentUser) && !currentUser.isAdmin()) {
            // TODO: error
            return null;
        }

        mapper.update(employee, employeeUpdate);
        repo.save(employee);
        return mapper.toDto(employee);
    }

    @Transactional
    public void delete(long id) {
        var employee = repo.findById(id).orElse(null); // TODO: error
        var currentUser = userService.getCurrentUser();

        if (!employee.getUser().equals(currentUser) && !currentUser.isAdmin()) {
            // TODO: error
            return;
        }

        repo.deleteById(id);
    }
}
