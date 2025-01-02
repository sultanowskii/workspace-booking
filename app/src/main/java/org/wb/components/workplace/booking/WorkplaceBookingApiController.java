package org.wb.components.workplace.booking;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wb.gen.api.WorkplaceBookingsApi;
import org.wb.gen.model.WorkplaceBooking;
import org.wb.gen.model.WorkplaceBookingCreate;
import org.wb.gen.model.WorkplaceBookingUpdate;

import jakarta.validation.Valid;

@Controller
@RequestMapping("${openapi.workplaceBooking.base-path:}")
public class WorkplaceBookingApiController implements WorkplaceBookingsApi {
    @Override
    public ResponseEntity<List<WorkplaceBooking>> getWorkplaceBookings(@Valid Long employeeId, @Valid Long workplaceId,
            @Valid LocalDate date) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Void> createWorkplaceBooking(@Valid WorkplaceBookingCreate workplaceBookingCreate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<WorkplaceBooking> getWorkplaceBooking(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<WorkplaceBooking> updateWorkplaceBooking(Long id,
            @Valid WorkplaceBookingUpdate workplaceBookingUpdate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteWorkplaceBooking(Long id) {
        // TODO Auto-generated method stub
        return null;
    }
}
