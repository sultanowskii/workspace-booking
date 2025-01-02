package org.wb.components.occupancy;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wb.gen.api.OccupancyApi;
import org.wb.gen.model.OfficeOccupancy;
import org.wb.gen.model.RoomOccupancy;

@Controller
@RequestMapping("${openapi.workplaceBooking.base-path:}")
public class OccupancyApiController implements OccupancyApi {
    @Override
    public ResponseEntity<List<OfficeOccupancy>> getOfficeOccupancies() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<OfficeOccupancy> getOfficeOccupancy(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<List<RoomOccupancy>> getRoomOccupancies() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<RoomOccupancy> getRoomOccupancy(Long id) {
        // TODO Auto-generated method stub
        return null;
    }
}
