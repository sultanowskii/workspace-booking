package org.wb.components.workplace.booking;

import java.time.LocalDate;
import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wb.components.common.paging.Paginator;
import org.wb.gen.api.WorkplaceBookingsApi;
import org.wb.gen.model.WorkplaceBooking;
import org.wb.gen.model.WorkplaceBookingCreate;
import org.wb.gen.model.WorkplaceBookingUpdate;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;

@Controller
@RequestMapping("${openapi.workspaceBooking.base-path:}")
public class WorkplaceBookingApiController implements WorkplaceBookingsApi {
    @Autowired
    private WorkplaceBookingService service;

    @Override
    public ResponseEntity<List<WorkplaceBooking>> getWorkplaceBookings(
            @Parameter(name = "employeeId", description = "Employee ID", in = ParameterIn.QUERY) @Valid @RequestParam(value = "employeeId", required = false) Long employeeId,
            @Parameter(name = "workplaceId", description = "Workplace ID", in = ParameterIn.QUERY) @Valid @RequestParam(value = "workplaceId", required = false) Long workplaceId,
            @Parameter(name = "date", description = "Date", in = ParameterIn.QUERY) @Valid @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @ParameterObject final Pageable pageable) {
        var spec = new WorkplaceBookingSpecificationBuilder()
                .withEmployeeId(employeeId)
                .withWorkplaceId(workplaceId)
                .withDate(date)
                .build();
        var paginator = Paginator.from(pageable);
        var result = service.getAll(spec, paginator);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<WorkplaceBooking> createWorkplaceBooking(
            @Valid WorkplaceBookingCreate workplaceBookingCreate) {
        var result = service.create(workplaceBookingCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    public ResponseEntity<WorkplaceBooking> getWorkplaceBooking(Long id) {
        var result = service.get(id);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<WorkplaceBooking> updateWorkplaceBooking(Long id,
            @Valid WorkplaceBookingUpdate workplaceBookingUpdate) {
        var result = service.update(id, workplaceBookingUpdate);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Void> deleteWorkplaceBooking(Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
