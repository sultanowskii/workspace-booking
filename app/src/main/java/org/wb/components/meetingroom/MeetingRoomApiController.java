package org.wb.components.meetingroom;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wb.gen.api.MeetingRoomsApi;
import org.wb.gen.model.MeetingRoom;
import org.wb.gen.model.MeetingRoomCreateUpdate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Controller
@RequestMapping("${openapi.workplaceBooking.base-path:}")
public class MeetingRoomApiController implements MeetingRoomsApi {
    @Override
    public ResponseEntity<List<MeetingRoom>> getMeetingRooms(@NotNull @Valid Long roomId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Void> createMeetingRoom(@Valid MeetingRoomCreateUpdate meetingRoomCreateUpdate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<MeetingRoom> getMeetingRoom(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<MeetingRoom> updateMeetingRoom(Long id,
            @Valid MeetingRoomCreateUpdate meetingRoomCreateUpdate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteMeetingRoom(Long id) {
        // TODO Auto-generated method stub
        return null;
    }
}
