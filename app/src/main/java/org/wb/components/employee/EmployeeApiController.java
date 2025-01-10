package org.wb.components.employee;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wb.components.common.paging.Paginator;
import org.wb.gen.api.EmployeesApi;
import org.wb.gen.model.Employee;
import org.wb.gen.model.EmployeeCreate;
import org.wb.gen.model.EmployeeUpdate;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

@Controller
@RequestMapping("${openapi.workspaceBooking.base-path:}")
public class EmployeeApiController implements EmployeesApi {
    @Autowired
    private EmployeeService service;

    @Override
    public ResponseEntity<List<Employee>> getEmployees(
            @Parameter(name = "searchFieldName", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "searchFieldName", required = false) String searchFieldName,
            @Size(min = 1) @Parameter(name = "searchString", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "searchString", required = false) String searchString,
            @Parameter(name = "employeeGroupId", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "employeeGroupId", required = false) Long employeeGroupId,
            @ParameterObject final Pageable pageable) {
        var searchSpec = new EmployeeSpecificationBuilder()
                .withEmployeeGroupId(employeeGroupId)
                .withFieldContaining(searchFieldName, searchString)
                .build();
        var paginator = Paginator.from(pageable);

        var result = service.getAll(searchSpec, paginator);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Employee> createEmployee(@Valid EmployeeCreate employeeCreate) {
        var result = service.create(employeeCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    public ResponseEntity<Employee> getEmployee(Long id) {
        var result = service.get(id);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Employee> updateEmployee(Long id, @Valid EmployeeUpdate employeeUpdate) {
        var result = service.update(id, employeeUpdate);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Void> deleteEmployee(Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> grantAdmin(Long id) {
        service.grantAdmin(id);
        return ResponseEntity.ok().build();
    }
}
