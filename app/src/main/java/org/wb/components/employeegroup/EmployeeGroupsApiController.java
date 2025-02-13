package org.wb.components.employeegroup;

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
import org.wb.gen.api.EmployeeGroupsApi;
import org.wb.gen.model.EmployeeGroupCreateUpdate;
import org.wb.gen.model.EmployeeGroupWithAllowedOffices;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

@Controller
@RequestMapping("${openapi.workspaceBooking.base-path:}")
public class EmployeeGroupsApiController implements EmployeeGroupsApi {
    @Autowired
    private EmployeeGroupService service;

    @Override
    public ResponseEntity<List<EmployeeGroupWithAllowedOffices>> getEmployeeGroups(
            @Parameter(name = "searchFieldName", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "searchFieldName", required = false) String searchFieldName,
            @Size(min = 1) @Parameter(name = "searchString", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "searchString", required = false) String searchString,
            @Parameter(name = "officeId", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "officeId", required = false) Long officeId,
            @ParameterObject final Pageable pageable) {
        var spec = new EmployeeGroupSpecificationBuilder()
                .withOfficeId(officeId)
                .withFieldContaining(searchFieldName, searchString)
                .build();
        var paginator = Paginator.from(pageable);
        var result = service.getAll(spec, paginator);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<EmployeeGroupWithAllowedOffices> createEmployeeGroup(
            @Valid EmployeeGroupCreateUpdate employeeGroupCreateUpdate) {
        var result = service.create(employeeGroupCreateUpdate);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    public ResponseEntity<EmployeeGroupWithAllowedOffices> getEmployeeGroup(Long id) {
        var result = service.get(id);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<EmployeeGroupWithAllowedOffices> updateEmployeeGroup(Long id,
            @Valid EmployeeGroupCreateUpdate employeeGroupCreateUpdate) {
        var result = service.update(id, employeeGroupCreateUpdate);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Void> deleteEmployeeGroup(Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
