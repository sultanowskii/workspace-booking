package org.wb.components.employee;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.wb.gen.model.EmployeeCreate;
import org.wb.gen.model.EmployeeUpdate;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class EmployeeMapper {
    @Mapping(source = "employee.user.username", target = "username")
    public abstract org.wb.gen.model.Employee toDto(Employee employee);

    public abstract Employee fromCreateDto(EmployeeCreate employeeCreate);

    public abstract Employee fromUpdateDto(EmployeeUpdate employeeUpdate);

    public abstract void update(@MappingTarget Employee employee, EmployeeUpdate employeeUpdate);
}
