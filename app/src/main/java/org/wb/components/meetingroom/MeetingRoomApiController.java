package org.wb.components.meetingroom;

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
import org.wb.gen.api.MeetingRoomsApi;
import org.wb.gen.model.MeetingRoom;
import org.wb.gen.model.MeetingRoomCreate;
import org.wb.gen.model.MeetingRoomUpdate;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Controller
@RequestMapping("${openapi.workspaceBooking.base-path:}")
public class MeetingRoomApiController implements MeetingRoomsApi {
    @Autowired
    private MeetingRoomService service;

    @Override
    public ResponseEntity<List<MeetingRoom>> getMeetingRooms(
            @NotNull @Parameter(name = "roomId", description = "Room ID", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "roomId", required = true) Long roomId,
            @Parameter(name = "searchFieldName", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "searchFieldName", required = false) String searchFieldName,
            @Size(min = 1) @Parameter(name = "searchString", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "searchString", required = false) String searchString,
            @ParameterObject final Pageable pageable) {
        var spec = new MeetingRoomSpecificationBuilder()
                .withRoomId(roomId)
                .withFieldContaining(searchFieldName, searchString)
                .build();
        var paginator = Paginator.from(pageable);
        var result = service.getAll(spec, paginator);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<MeetingRoom> createMeetingRoom(@Valid MeetingRoomCreate meetingRoomCreate) {
        var result = service.create(meetingRoomCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    public ResponseEntity<MeetingRoom> getMeetingRoom(Long id) {
        var result = service.get(id);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<MeetingRoom> updateMeetingRoom(Long id,
            @Valid MeetingRoomUpdate meetingRoomUpdate) {
        var result = service.update(id, meetingRoomUpdate);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Void> deleteMeetingRoom(Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
