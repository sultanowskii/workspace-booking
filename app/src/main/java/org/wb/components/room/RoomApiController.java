package org.wb.components.room;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wb.gen.api.RoomsApi;
import org.wb.gen.model.Room;
import org.wb.gen.model.RoomCreateUpdate;
import org.wb.gen.model.RoomWithWalls;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Controller
@RequestMapping("${openapi.workplaceBooking.base-path:}")
public class RoomApiController implements RoomsApi {
    @Override
    public ResponseEntity<List<Room>> getRooms(@NotNull @Valid Long officeId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Void> createRoom(@Valid RoomCreateUpdate roomCreateUpdate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<RoomWithWalls> getRoom(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<RoomWithWalls> updateRoom(Long id, @Valid RoomCreateUpdate roomCreateUpdate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteRoom(Long id) {
        // TODO Auto-generated method stub
        return null;
    }
}
