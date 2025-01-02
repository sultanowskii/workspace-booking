package org.wb.components.workplace;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wb.gen.api.WorkplacesApi;
import org.wb.gen.model.Workplace;
import org.wb.gen.model.WorkplaceCreateUpdate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Controller
@RequestMapping("${openapi.workplaceBooking.base-path:}")
public class WorkplaceApiController implements WorkplacesApi {
    @Override
    public ResponseEntity<List<Workplace>> getWorkplaces(@NotNull @Valid Long roomId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Void> createWorkplace(@Valid WorkplaceCreateUpdate workplaceCreateUpdate) {
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
