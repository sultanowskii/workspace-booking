package org.wb.components.occupancy;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wb.gen.api.OccupancyApi;
import org.wb.gen.model.OfficeOccupancy;
import org.wb.gen.model.RoomOccupancy;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Controller
@RequestMapping("${openapi.workplaceBooking.base-path:}")
public class OccupancyApiController implements OccupancyApi {
    @Autowired
    private OccupancyService service;

    @Override
    public ResponseEntity<OfficeOccupancy> getOfficeOccupancy(
            @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id,
            @Parameter(name = "date", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        var result = service.getOfficeOccupancy(id, date);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<List<RoomOccupancy>> getRoomOccupancies(
            @NotNull @Parameter(name = "officeId", description = "", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "officeId", required = true) Long officeId,
            @Parameter(name = "date", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        var result = service.getRoomOccupancies(officeId, date);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<RoomOccupancy> getRoomOccupancy(
            @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id,
            @Parameter(name = "date", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        var result = service.getRoomOccupancy(id, date);
        return ResponseEntity.ok(result);
    }
}
