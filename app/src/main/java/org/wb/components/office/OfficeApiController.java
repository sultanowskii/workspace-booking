package org.wb.components.office;

import org.wb.components.common.paging.Paginator;
import org.wb.gen.api.OfficesApi;
import org.wb.gen.model.Office;
import org.wb.gen.model.OfficeCreateUpdate;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("${openapi.workspaceBooking.base-path:}")
public class OfficeApiController implements OfficesApi {
    @Autowired
    private OfficeService service;

    @Override
    public ResponseEntity<List<Office>> getOffices(
            @Parameter(name = "searchFieldName", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "searchFieldName", required = false) String searchFieldName,
            @Size(min = 1) @Parameter(name = "searchString", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "searchString", required = false) String searchString,
            @Parameter(name = "employeeGroupId", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "employeeGroupId", required = false) Long employeeGroupId,
            @ParameterObject final Pageable pageable) {
        var spec = new OfficeSpecificationBuilder()
                .withEmployeeGroupId(employeeGroupId)
                .withFieldContaining(searchFieldName, searchString)
                .build();
        var paginator = Paginator.from(pageable);
        var result = service.getAll(spec, paginator);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Office> createOffice(@Valid OfficeCreateUpdate officeCreateUpdate) {
        var result = service.create(officeCreateUpdate);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    public ResponseEntity<Office> getOffice(Long id) {
        var result = service.get(id);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Office> updateOffice(Long id, @Valid OfficeCreateUpdate officeCreateUpdate) {
        var result = service.update(id, officeCreateUpdate);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Void> deleteOffice(Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> addEmployeeGroupToOffice(Long employeeGroupId, Long officeId) {
        service.addEmployeeGroupToOffice(officeId, employeeGroupId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> removeEmployeeGroupFromOffice(Long employeeGroupId, Long officeId) {
        service.removeEmployeeGroupToOffice(officeId, employeeGroupId);
        return ResponseEntity.noContent().build();
    }
}
