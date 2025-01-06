package org.wb.components.meetingroom;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wb.components.common.EntityMapper;
import org.wb.components.room.Room;
import org.wb.components.room.RoomService;
import org.wb.gen.model.MeetingRoomCreate;
import org.wb.gen.model.MeetingRoomUpdate;

@Service
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class MeetingRoomMapper implements
        EntityMapper<MeetingRoom, org.wb.gen.model.MeetingRoom, org.wb.gen.model.MeetingRoom, MeetingRoomCreate, MeetingRoomUpdate> {
    @Autowired
    private RoomService roomService;

    @Override
    @Mapping(target = "x", source = "visual.x")
    @Mapping(target = "y", source = "visual.y")
    @Mapping(target = "width", source = "visual.width")
    @Mapping(target = "height", source = "visual.height")
    @Mapping(target = "roomId", source = "room.id")
    public abstract org.wb.gen.model.MeetingRoom toDto(MeetingRoom meetingRoom);

    @Override
    @Mapping(target = "x", source = "visual.x")
    @Mapping(target = "y", source = "visual.y")
    @Mapping(target = "width", source = "visual.width")
    @Mapping(target = "height", source = "visual.height")
    @Mapping(target = "roomId", source = "room.id")
    public abstract org.wb.gen.model.MeetingRoom toListDto(MeetingRoom meetingRoom);

    @Override
    @Mapping(target = "visual.x", source = "x")
    @Mapping(target = "visual.y", source = "y")
    @Mapping(target = "visual.width", source = "width")
    @Mapping(target = "visual.height", source = "height")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "room", qualifiedByName = "mapRoom", source = "createDto.roomId")
    public abstract MeetingRoom fromCreateDto(MeetingRoomCreate createDto);

    @Override
    @Mapping(target = "visual.x", source = "x")
    @Mapping(target = "visual.y", source = "y")
    @Mapping(target = "visual.width", source = "width")
    @Mapping(target = "visual.height", source = "height")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "room", ignore = true)
    public abstract MeetingRoom fromUpdateDto(MeetingRoomUpdate updateDto);

    @Override
    @Mapping(target = "visual.x", source = "x")
    @Mapping(target = "visual.y", source = "y")
    @Mapping(target = "visual.width", source = "width")
    @Mapping(target = "visual.height", source = "height")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "room", ignore = true)
    public abstract void update(@MappingTarget MeetingRoom meetingRoom, MeetingRoomUpdate updateDto);

    @Named("mapRoom")
    protected Room mapRoom(long roomId) {
        return roomService.getRaw(roomId);
    }
}
