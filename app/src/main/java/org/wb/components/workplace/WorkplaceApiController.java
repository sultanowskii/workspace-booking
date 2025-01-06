package org.wb.components.workplace;

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
import org.wb.gen.api.WorkplacesApi;
import org.wb.gen.model.Workplace;
import org.wb.gen.model.WorkplaceCreate;
import org.wb.gen.model.WorkplaceUpdate;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Controller
@RequestMapping("${openapi.workspaceBooking.base-path:}")
public class WorkplaceApiController implements WorkplacesApi {
    @Autowired
    private WorkplaceService service;

    @Override
    public ResponseEntity<List<Workplace>> getWorkplaces(
            @NotNull @Parameter(name = "roomId", description = "Room ID", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "roomId", required = true) Long roomId,
            @Parameter(name = "searchFieldName", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "searchFieldName", required = false) String searchFieldName,
            @Size(min = 1) @Parameter(name = "searchString", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "searchString", required = false) String searchString,
            @ParameterObject final Pageable pageable) {
        var spec = new WorkplaceSpecificationBuilder()
                .withRoomId(roomId)
                .withFieldContaining(searchFieldName, searchString)
                .build();
        var paginator = Paginator.from(pageable);
        var result = service.getAll(spec, paginator);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Workplace> createWorkplace(@Valid WorkplaceCreate workplaceCreate) {
        var result = service.create(workplaceCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    public ResponseEntity<Workplace> getWorkplace(Long id) {
        var result = service.get(id);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Workplace> updateWorkplace(Long id, @Valid WorkplaceUpdate workplaceUpdate) {
        var result = service.update(id, workplaceUpdate);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Void> deleteWorkplace(Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
