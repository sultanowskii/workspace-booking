package org.wb.components.employeegroup;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wb.gen.api.EmployeeGroupsApi;
import org.wb.gen.model.EmployeeGroup;
import org.wb.gen.model.EmployeeGroupCreateUpdate;
import org.wb.gen.model.EmployeeGroupOffice;

import jakarta.validation.Valid;

@Controller
@RequestMapping("${openapi.workplaceBooking.base-path:}")
public class EmployeeGroupsApiController implements EmployeeGroupsApi {
    @Override
    public ResponseEntity<List<EmployeeGroup>> getEmployeeGroups() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Void> createEmployeeGroup(@Valid EmployeeGroup employeeGroup) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<EmployeeGroup> getEmployeeGroup(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<EmployeeGroup> updateEmployeeGroups(Long id,
            @Valid EmployeeGroupCreateUpdate employeeGroupCreateUpdate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteEmployeeGroup(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<List<EmployeeGroupOffice>> getEmployeeGroupOffices() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Void> addEmployeeGroupToOffice(Long employeeGroupId, Long officeId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Void> removeEmployeeGroupFromOffice(Long employeeGroupId, Long officeId) {
        // TODO Auto-generated method stub
        return null;
    }
}
