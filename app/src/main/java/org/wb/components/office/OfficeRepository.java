package org.wb.components.office;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.wb.components.occupancy.OfficeOccupancy;

@Repository
public interface OfficeRepository extends org.wb.components.common.Repository<Office> {
    @Query(value = "SELECT office.*, get_office_occupancy(CAST(:target_office_id AS INTEGER), CAST(:target_date AS DATE)) as rate FROM office WHERE office.id = :target_office_id", nativeQuery = true)
    OfficeOccupancy getOfficeOccupancy(@Param("target_office_id") long targetOfficeId,
            @Param("target_date") LocalDate targetDate);
}
