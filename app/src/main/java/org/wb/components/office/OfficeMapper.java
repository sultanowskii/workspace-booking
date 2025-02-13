package org.wb.components.office;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Service;
import org.wb.components.common.EntityMapper;
import org.wb.gen.model.OfficeCreateUpdate;

@Service
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class OfficeMapper
        implements
        EntityMapper<Office, org.wb.gen.model.OfficeWithEmployeeGroups, org.wb.gen.model.OfficeWithEmployeeGroups, OfficeCreateUpdate, OfficeCreateUpdate> {
    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employeeGroups", ignore = true)
    public abstract Office fromCreateDto(OfficeCreateUpdate createDto);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employeeGroups", ignore = true)
    public abstract Office fromUpdateDto(OfficeCreateUpdate updateDto);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employeeGroups", ignore = true)
    public abstract void update(@MappingTarget Office entity, OfficeCreateUpdate updateDto);
}
