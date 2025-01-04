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
        implements EntityMapper<Employee, org.wb.gen.model.Employee, EmployeeCreate, EmployeeUpdate> {
    @Mapping(source = "employee.user.username", target = "username")
    public abstract org.wb.gen.model.Employee toDto(Employee employee);

    public abstract Employee fromCreateDto(EmployeeCreate employeeCreate);

    public abstract Employee fromUpdateDto(EmployeeUpdate employeeUpdate);

    public abstract void update(@MappingTarget Employee employee, EmployeeUpdate employeeUpdate);
}
