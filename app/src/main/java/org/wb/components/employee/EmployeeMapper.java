package org.wb.components.employee;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wb.components.common.EntityMapper;
import org.wb.components.employeegroup.EmployeeGroup;
import org.wb.components.employeegroup.EmployeeGroupService;
import org.wb.gen.model.EmployeeCreate;
import org.wb.gen.model.EmployeeUpdate;

@Service
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class EmployeeMapper
        implements
        EntityMapper<Employee, org.wb.gen.model.Employee, org.wb.gen.model.Employee, EmployeeCreate, EmployeeUpdate> {
    @Autowired
    private EmployeeGroupService employeeGroupService;

    @Override
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "employeeGroupId", source = "employeeGroup.id")
    public abstract org.wb.gen.model.Employee toDto(Employee employee);

    @Override
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "employeeGroupId", source = "employeeGroup.id")
    public abstract org.wb.gen.model.Employee toListDto(Employee employee);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "employeeGroup", qualifiedByName = "mapEmployeeGroup", source = "employeeGroupId")
    @Mapping(target = "meetings", ignore = true)
    public abstract Employee fromCreateDto(EmployeeCreate employeeCreate);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "employeeGroup", qualifiedByName = "mapEmployeeGroup", source = "employeeGroupId")
    @Mapping(target = "meetings", ignore = true)
    public abstract Employee fromUpdateDto(EmployeeUpdate employeeUpdate);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "employeeGroup", qualifiedByName = "mapEmployeeGroup", source = "employeeGroupId")
    @Mapping(target = "meetings", ignore = true)
    public abstract void update(@MappingTarget Employee employee, EmployeeUpdate employeeUpdate);

    @Named("mapEmployeeGroup")
    protected EmployeeGroup mapEmployeeGroup(long employeeGroupId) {
        return employeeGroupService.getRaw(employeeGroupId);
    }
}
