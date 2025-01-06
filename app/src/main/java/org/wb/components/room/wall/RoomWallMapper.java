package org.wb.components.room.wall;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoomWallMapper {
    org.wb.gen.model.RoomWall toDto(RoomWall roomWall);

    RoomWall fromDto(org.wb.gen.model.RoomWall roomWallDto);
}
