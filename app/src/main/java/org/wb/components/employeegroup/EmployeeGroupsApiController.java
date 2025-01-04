package org.wb.components.employeegroup;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wb.gen.api.EmployeeGroupsApi;
import org.wb.gen.model.EmployeeGroup;
import org.wb.gen.model.EmployeeGroupCreateUpdate;
import org.wb.gen.model.EmployeeGroupOffice;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

@Controller
@RequestMapping("${openapi.workplaceBooking.base-path:}")
public class EmployeeGroupsApiController implements EmployeeGroupsApi {
    @Override
    public ResponseEntity<List<EmployeeGroup>> getEmployeeGroups(
            @Parameter(name = "searchFieldName", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "searchFieldName", required = false) String searchFieldName,
            @Size(min = 1) @Parameter(name = "searchString", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "searchString", required = false) String searchString,
            @ParameterObject final Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<EmployeeGroup> createEmployeeGroup(@Valid EmployeeGroup employeeGroup) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<EmployeeGroup> getEmployeeGroup(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<EmployeeGroup> updateEmployeeGroup(Long id,
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
