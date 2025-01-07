package org.wb.components.workplace.booking;

import java.time.LocalDate;

import org.wb.components.common.EntitySpecificationBuilder;

public class WorkplaceBookingSpecificationBuilder extends EntitySpecificationBuilder<WorkplaceBooking> {
    @Override
    public boolean isFieldValid(String fieldName) {
        return false;
    }

    public WorkplaceBookingSpecificationBuilder withWorkplaceId(Long workplaceId) {
        if (workplaceId == null) {
            return this;
        }
        andOtherSpec((root, query, builder) -> {
            return builder.equal(root.get("workplace").get("id"), workplaceId);
        });
        return this;
    }

    public WorkplaceBookingSpecificationBuilder withEmployeeId(Long employeeId) {
        if (employeeId == null) {
            return this;
        }
        andOtherSpec((root, query, builder) -> {
            return builder.equal(root.get("employee").get("id"), employeeId);
        });
        return this;
    }

    public WorkplaceBookingSpecificationBuilder withDate(LocalDate date) {
        if (date == null) {
            return this;
        }
        andOtherSpec((root, query, builder) -> {
            return builder.equal(root.get("bookingDate"), date);
        });
        return this;
    }
}
