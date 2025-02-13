package org.wb.components.employeegroup;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Service;
import org.wb.components.common.EntityMapper;
import org.wb.gen.model.EmployeeGroupCreateUpdate;

@Service
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class EmployeeGroupMapper implements
        EntityMapper<EmployeeGroup, org.wb.gen.model.EmployeeGroupWithAllowedOffices, org.wb.gen.model.EmployeeGroupWithAllowedOffices, EmployeeGroupCreateUpdate, EmployeeGroupCreateUpdate> {
    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "allowedOffices", ignore = true)
    public abstract EmployeeGroup fromCreateDto(EmployeeGroupCreateUpdate createDto);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "allowedOffices", ignore = true)
    public abstract EmployeeGroup fromUpdateDto(EmployeeGroupCreateUpdate updateDto);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "allowedOffices", ignore = true)
    public abstract void update(@MappingTarget EmployeeGroup entity, EmployeeGroupCreateUpdate updateDto);
}
