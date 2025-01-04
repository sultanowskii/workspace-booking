package org.wb.components.workplace;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wb.gen.api.WorkplacesApi;
import org.wb.gen.model.Workplace;
import org.wb.gen.model.WorkplaceCreateUpdate;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Controller
@RequestMapping("${openapi.workplaceBooking.base-path:}")
public class WorkplaceApiController implements WorkplacesApi {
    @Override
    public ResponseEntity<List<Workplace>> getWorkplaces(
            @NotNull @Parameter(name = "roomId", description = "Room ID", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "roomId", required = true) Long roomId,
            @Parameter(name = "searchFieldName", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "searchFieldName", required = false) String searchFieldName,
            @Size(min = 1) @Parameter(name = "searchString", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "searchString", required = false) String searchString,
            @ParameterObject final Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Workplace> createWorkplace(@Valid WorkplaceCreateUpdate workplaceCreateUpdate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Workplace> getWorkplace(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Workplace> updateWorkplace(Long id, @Valid WorkplaceCreateUpdate workplaceCreateUpdate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteWorkplace(Long id) {
        // TODO Auto-generated method stub
        return null;
    }
}
