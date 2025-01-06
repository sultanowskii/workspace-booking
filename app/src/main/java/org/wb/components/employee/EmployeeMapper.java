package org.wb.components.employee;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Service;
import org.wb.components.common.EntityMapper;
import org.wb.gen.model.EmployeeCreate;
import org.wb.gen.model.EmployeeUpdate;

@Service
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class EmployeeMapper
        implements
        EntityMapper<Employee, org.wb.gen.model.Employee, org.wb.gen.model.Employee, EmployeeCreate, EmployeeUpdate> {
    @Override
    @Mapping(target = "username", source = "employee.user.username")
    public abstract org.wb.gen.model.Employee toDto(Employee employee);

    @Override
    @Mapping(target = "username", source = "employee.user.username")
    public abstract org.wb.gen.model.Employee toListDto(Employee employee);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "employeeGroup", ignore = true)
    @Mapping(target = "meetings", ignore = true)
    public abstract Employee fromCreateDto(EmployeeCreate employeeCreate);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "employeeGroup", ignore = true)
    @Mapping(target = "meetings", ignore = true)
    public abstract Employee fromUpdateDto(EmployeeUpdate employeeUpdate);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "employeeGroup", ignore = true)
    @Mapping(target = "meetings", ignore = true)
    public abstract void update(@MappingTarget Employee employee, EmployeeUpdate employeeUpdate);
}
