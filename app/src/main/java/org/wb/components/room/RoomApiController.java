package org.wb.components.room;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wb.components.common.paging.Paginator;
import org.wb.gen.api.RoomsApi;
import org.wb.gen.model.Room;
import org.wb.gen.model.RoomCreate;
import org.wb.gen.model.RoomLayout;
import org.wb.gen.model.RoomUpdate;
import org.wb.gen.model.RoomWithWalls;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Controller
@RequestMapping("${openapi.workspaceBooking.base-path:}")
public class RoomApiController implements RoomsApi {
    @Autowired
    private RoomService service;
    @Autowired
    private RoomLayoutService layoutService;

    @Override
    public ResponseEntity<List<Room>> getRooms(
            @NotNull @Parameter(name = "officeId", description = "Office ID", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "officeId", required = true) Long officeId,
            @Parameter(name = "searchFieldName", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "searchFieldName", required = false) String searchFieldName,
            @Size(min = 1) @Parameter(name = "searchString", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "searchString", required = false) String searchString,
            @ParameterObject final Pageable pageable) {
        var spec = new RoomSpecificationBuilder()
                .withOfficeId(officeId)
                .withFieldContaining(searchFieldName, searchString)
                .build();
        var paginator = Paginator.from(pageable);
        var result = service.getAll(spec, paginator);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<RoomWithWalls> createRoom(@Valid RoomCreate roomCreate) {
        var result = service.create(roomCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    public ResponseEntity<RoomWithWalls> getRoom(Long id) {
        var result = service.get(id);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<RoomWithWalls> updateRoom(Long id, @Valid RoomUpdate roomUpdate) {
        var result = service.update(id, roomUpdate);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Void> deleteRoom(Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<RoomLayout> getRoomLayout(Long id) {
        var result = layoutService.geRoomLayout(id);
        return ResponseEntity.ok(result);
    }
}
