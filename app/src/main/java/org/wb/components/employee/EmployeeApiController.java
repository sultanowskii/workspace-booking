package org.wb.components.employee;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wb.gen.api.EmployeesApi;
import org.wb.gen.model.Employee;
import org.wb.gen.model.EmployeeCreate;
import org.wb.gen.model.EmployeeUpdate;

import jakarta.validation.Valid;

@Controller
@RequestMapping("${openapi.workplaceBooking.base-path:}")
public class EmployeeApiController implements EmployeesApi {
    @Override
    public ResponseEntity<List<Employee>> getEmployees() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Void> createEmployee(@Valid EmployeeCreate employeeCreate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Employee> getEmployee(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Employee> updateEmployee(Long id, @Valid EmployeeUpdate employeeUpdate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteEmployee(Long id) {
        // TODO Auto-generated method stub
        return null;
    }
}
