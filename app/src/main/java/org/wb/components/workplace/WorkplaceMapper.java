package org.wb.components.workplace;

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
import org.wb.gen.model.WorkplaceCreate;
import org.wb.gen.model.WorkplaceUpdate;

@Service
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class WorkplaceMapper implements
        EntityMapper<Workplace, org.wb.gen.model.Workplace, org.wb.gen.model.Workplace, WorkplaceCreate, WorkplaceUpdate> {
    @Autowired
    private RoomService roomService;

    @Override
    @Mapping(target = "x", source = "visual.x")
    @Mapping(target = "y", source = "visual.y")
    @Mapping(target = "width", source = "visual.width")
    @Mapping(target = "height", source = "visual.height")
    @Mapping(target = "roomId", source = "room.id")
    public abstract org.wb.gen.model.Workplace toDto(Workplace workplace);

    @Override
    @Mapping(target = "x", source = "visual.x")
    @Mapping(target = "y", source = "visual.y")
    @Mapping(target = "width", source = "visual.width")
    @Mapping(target = "height", source = "visual.height")
    @Mapping(target = "roomId", source = "room.id")
    public abstract org.wb.gen.model.Workplace toListDto(Workplace workplace);

    @Override
    @Mapping(target = "visual.x", source = "x")
    @Mapping(target = "visual.y", source = "y")
    @Mapping(target = "visual.width", source = "width")
    @Mapping(target = "visual.height", source = "height")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "room", qualifiedByName = "mapRoom", source = "createDto.roomId")
    public abstract Workplace fromCreateDto(WorkplaceCreate createDto);

    @Override
    @Mapping(target = "visual.x", source = "x")
    @Mapping(target = "visual.y", source = "y")
    @Mapping(target = "visual.width", source = "width")
    @Mapping(target = "visual.height", source = "height")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "room", ignore = true)
    public abstract Workplace fromUpdateDto(WorkplaceUpdate updateDto);

    @Override
    @Mapping(target = "visual.x", source = "x")
    @Mapping(target = "visual.y", source = "y")
    @Mapping(target = "visual.width", source = "width")
    @Mapping(target = "visual.height", source = "height")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "room", ignore = true)
    public abstract void update(@MappingTarget Workplace entity, WorkplaceUpdate updateDto);

    @Named("mapRoom")
    protected Room mapOffice(long roomId) {
        return roomService.getRaw(roomId);
    }
}
