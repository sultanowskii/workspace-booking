package org.wb.components.occupancy;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wb.components.office.OfficeRepository;
import org.wb.components.room.RoomRepository;
import org.wb.gen.model.OfficeOccupancy;
import org.wb.gen.model.RoomOccupancy;

@Service
public class OccupancyService {
    @Autowired
    protected OfficeRepository officeRepo;
    @Autowired
    protected RoomRepository roomRepo;

    public OfficeOccupancy getOfficeOccupancy(long officeId, LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        var result = officeRepo.getOfficeOccupancy(officeId, date);
        return new OfficeOccupancy()
                .id(result.getId())
                .name(result.getName())
                .address(result.getAddress())
                .rate(result.getRate());
    }

    public RoomOccupancy getRoomOccupancy(long roomId, LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        var result = roomRepo.getRoomOccupancy(roomId, date);
        return new RoomOccupancy()
                .id(result.getId())
                .name(result.getName())
                .rate(result.getRate());
    }

    public List<RoomOccupancy> getRoomOccupancies(long officeId, LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        var result = roomRepo.getRoomOccupancies(officeId, date);
        return result
                .stream()
                .map(o -> {
                    return new RoomOccupancy()
                            .id(o.getId())
                            .name(o.getName())
                            .rate(o.getRate());
                })
                .toList();
    }
}
