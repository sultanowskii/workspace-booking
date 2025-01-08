package org.wb.components.imports;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.wb.components.common.LoudValidator;
import org.wb.components.error.exception.InvalidBodyException;
import org.wb.components.imports.dto.RoomImportDto;
import org.wb.components.meetingroom.MeetingRoomService;
import org.wb.components.room.RoomService;
import org.wb.components.workplace.WorkplaceService;
import org.wb.gen.model.MeetingRoomCreate;
import org.wb.gen.model.RoomCreate;
import org.wb.gen.model.RoomWall;
import org.wb.gen.model.WorkplaceCreate;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import jakarta.transaction.Transactional;

@Service
public class ImportService {
    @Autowired
    protected RoomService roomService;
    @Autowired
    protected WorkplaceService workplaceService;
    @Autowired
    protected MeetingRoomService meetingRoomService;

    @Transactional
    public void createRoom(RoomImportDto dto) {
        var walls = dto.getWalls();

        var roomCreateForm = new RoomCreate()
                .name(dto.getName())
                .officeId(dto.getOfficeId())
                .walls(walls
                        .stream()
                        .map(w -> {
                            return new RoomWall()
                                    .x1(w.getX1())
                                    .y1(w.getY1())
                                    .x2(w.getX2())
                                    .y2(w.getY2());
                        })
                        .toList());
        var createdRoom = roomService.create(roomCreateForm);

        var workplaces = dto.getWorkplaces();
        var workplacesCreateForm = workplaces
                .stream()
                .map(w -> {
                    return new WorkplaceCreate()
                            .roomId(createdRoom.getId())
                            .numberOfMonitors(w.getNumberOfMonitors())
                            .x(w.getX())
                            .y(w.getY())
                            .width(w.getWidth())
                            .height(w.getHeight());
                })
                .toList();
        workplaceService.createAllRaw(workplacesCreateForm);

        var meetingRooms = dto.getMeetingRooms();
        var meetingRoomsCreateForm = meetingRooms
                .stream()
                .map(w -> {
                    return new MeetingRoomCreate()
                            .roomId(createdRoom.getId())
                            .name(w.getName())
                            .x(w.getX())
                            .y(w.getY())
                            .width(w.getWidth())
                            .height(w.getHeight());
                })
                .toList();
        meetingRoomService.createAllRaw(meetingRoomsCreateForm);
    }

    private void validate(RoomImportDto dto) {
        LoudValidator.validate(dto);
        for (var wall : dto.getWalls()) {
            LoudValidator.validate(wall);
        }
        for (var workplace : dto.getWorkplaces()) {
            LoudValidator.validate(workplace);
        }
        for (var meetingRoom : dto.getMeetingRooms()) {
            LoudValidator.validate(meetingRoom);
        }
    }

    public void extractAndCreateRoom(MultipartFile file) throws IOException {
        var gson = new Gson();

        RoomImportDto dto;
        try {
            dto = gson.fromJson(new String(file.getBytes()), RoomImportDto.class);
        } catch (JsonSyntaxException e) {
            throw new InvalidBodyException(e.getLocalizedMessage());
        }

        validate(dto);

        createRoom(dto);
    }
}
