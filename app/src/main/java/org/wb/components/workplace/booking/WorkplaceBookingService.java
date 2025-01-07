package org.wb.components.workplace.booking;

import java.time.LocalDate;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.wb.components.common.EntityService;
import org.wb.components.error.exception.InvalidBodyException;
import org.wb.gen.model.WorkplaceBookingCreate;
import org.wb.gen.model.WorkplaceBookingUpdate;

@Service
public class WorkplaceBookingService extends
        EntityService<WorkplaceBooking, org.wb.gen.model.WorkplaceBooking, org.wb.gen.model.WorkplaceBooking, WorkplaceBookingCreate, WorkplaceBookingUpdate> {
    @Override
    protected boolean isListAllowed() {
        return true;
    }

    @Override
    protected boolean isCreateAllowed() {
        return true;
    }

    @Override
    protected boolean isReadAllowed(WorkplaceBooking workplaceBooking) {
        return true;
    }

    @Override
    protected boolean isUpdateAllowed(WorkplaceBooking workplaceBooking) {
        return isCurrentUserOwnerOf(workplaceBooking);
    }

    @Override
    protected boolean isDeleteAllowed(WorkplaceBooking workplaceBooking) {
        return isCurrentUserOwnerOf(workplaceBooking);
    }

    protected boolean isCurrentUserOwnerOf(WorkplaceBooking workplaceBooking) {
        var currentUser = userService.getCurrentUser();
        return workplaceBooking.getEmployee().getUser().equals(currentUser);
    }

    @Override
    protected String entityName() {
        return "workplace booking";
    }

    @Override
    public org.wb.gen.model.WorkplaceBooking create(WorkplaceBookingCreate createDto) {
        try {
            return super.create(createDto);
        } catch (DataIntegrityViolationException e) {
            handleUniquenessViolation(e, createDto.getDate(), createDto.getWorkplaceId());
            return null;
        }
    }

    @Override
    public org.wb.gen.model.WorkplaceBooking update(long id, WorkplaceBookingUpdate updateDto) {
        try {
            return super.update(id, updateDto);
        } catch (DataIntegrityViolationException e) {
            handleUniquenessViolation(e, updateDto.getDate(), updateDto.getWorkplaceId());
            return null;
        }
    }

    public void handleUniquenessViolation(DataIntegrityViolationException e, LocalDate date, long workplaceId) {
        String message = e.getMessage();
        if (message.contains("workplace_booking_workplace_id_booking_date_key")) {
            throw new InvalidBodyException("Booking for workplace (id=" + workplaceId + ") at "
                    + date + " already exists");
        }
        if (message.contains("workplace_booking_employee_id_booking_date_key")) {
            throw new InvalidBodyException("Employee already has workplace booking at " + date);
        }
        throw e;
    }
}
