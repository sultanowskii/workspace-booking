package org.wb.components.room;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.wb.components.occupancy.RoomOccupancy;

@Repository
public interface RoomRepository extends org.wb.components.common.Repository<Room> {
        @Query(value = "SELECT room.*, get_room_occupancy(CAST(:target_room_id AS INTEGER), CAST(:target_date AS DATE)) as rate FROM room WHERE room.id = :target_room_id", nativeQuery = true)
        RoomOccupancy getRoomOccupancy(@Param("target_room_id") long id,
                        @Param("target_date") LocalDate targetDate);

        @Query(value = "SELECT room.*, get_room_occupancy(room.id, CAST(:target_date AS DATE)) as rate FROM room WHERE room.office_id = :target_office_id", nativeQuery = true)
        List<RoomOccupancy> getRoomOccupancies(@Param("target_office_id") long officeId,
                        @Param("target_date") LocalDate targetDate);
}
