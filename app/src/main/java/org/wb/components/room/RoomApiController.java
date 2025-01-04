package org.wb.components.room;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wb.gen.api.RoomsApi;
import org.wb.gen.model.Room;
import org.wb.gen.model.RoomCreateUpdate;
import org.wb.gen.model.RoomWithWalls;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Controller
@RequestMapping("${openapi.workplaceBooking.base-path:}")
public class RoomApiController implements RoomsApi {
    @Override
    public ResponseEntity<List<Room>> getRooms(
            @NotNull @Parameter(name = "officeId", description = "Office ID", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "officeId", required = true) Long officeId,
            @Parameter(name = "searchFieldName", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "searchFieldName", required = false) String searchFieldName,
            @Size(min = 1) @Parameter(name = "searchString", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "searchString", required = false) String searchString,
            @ParameterObject final Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Room> createRoom(@Valid RoomCreateUpdate roomCreateUpdate) {
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
