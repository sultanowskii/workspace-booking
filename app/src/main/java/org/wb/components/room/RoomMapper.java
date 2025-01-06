package org.wb.components.room;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wb.components.common.EntityMapper;
import org.wb.components.office.Office;
import org.wb.components.office.OfficeService;
import org.wb.components.room.wall.RoomWallMapper;
import org.wb.gen.model.RoomCreate;
import org.wb.gen.model.RoomUpdate;

@Service
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = RoomWallMapper.class)
public abstract class RoomMapper implements
        EntityMapper<Room, org.wb.gen.model.RoomWithWalls, org.wb.gen.model.Room, RoomCreate, RoomUpdate> {
    @Autowired
    private OfficeService officeService;

    @Override
    @Mapping(target = "officeId", source = "room.office.id")
    public abstract org.wb.gen.model.Room toListDto(Room room);

    @Override
    @Mapping(target = "officeId", source = "room.office.id")
    public abstract org.wb.gen.model.RoomWithWalls toDto(Room room);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "office", qualifiedByName = "mapOffice", source = "createDto.officeId")
    public abstract Room fromCreateDto(RoomCreate createDto);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "office", ignore = true)
    public abstract Room fromUpdateDto(RoomUpdate updateDto);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "office", ignore = true)
    public abstract void update(@MappingTarget Room room, RoomUpdate updateDto);

    @Named("mapOffice")
    protected Office mapOffice(long officeId) {
        return officeService.getRaw(officeId);
    }
}
