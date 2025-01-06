package org.wb.components.room.wall;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoomWallMapper {
    org.wb.gen.model.RoomWall toDto(RoomWall roomWall);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "room", ignore = true)
    RoomWall fromDto(org.wb.gen.model.RoomWall roomWallDto);
}
