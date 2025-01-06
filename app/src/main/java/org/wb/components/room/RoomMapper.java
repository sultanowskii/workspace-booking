package org.wb.components.room;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Service;
import org.wb.components.common.EntityMapper;
import org.wb.components.room.wall.RoomWallMapper;
import org.wb.gen.model.RoomCreateUpdate;

@Service
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = RoomWallMapper.class)
public abstract class RoomMapper implements
        EntityMapper<Room, org.wb.gen.model.RoomWithWalls, org.wb.gen.model.Room, RoomCreateUpdate, RoomCreateUpdate> {

    @Override
    @Mapping(source = "room.office.id", target = "officeId")
    public abstract org.wb.gen.model.Room toListDto(Room room);

    @Override
    @Mapping(source = "room.office.id", target = "officeId")
    public abstract org.wb.gen.model.RoomWithWalls toDto(Room room);
}
