package org.wb.components.workplace.booking;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wb.components.common.EntityMapper;
import org.wb.components.employee.Employee;
import org.wb.components.employee.EmployeeService;
import org.wb.components.workplace.Workplace;
import org.wb.components.workplace.WorkplaceService;
import org.wb.gen.model.WorkplaceBookingCreate;
import org.wb.gen.model.WorkplaceBookingUpdate;

@Service
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class WorkplaceBookingMapper implements
        EntityMapper<WorkplaceBooking, org.wb.gen.model.WorkplaceBooking, org.wb.gen.model.WorkplaceBooking, WorkplaceBookingCreate, WorkplaceBookingUpdate> {
    @Autowired
    protected EmployeeService employeeService;
    @Autowired
    protected WorkplaceService workplaceService;

    @Override
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "workplaceId", source = "workplace.id")
    @Mapping(target = "date", source = "bookingDate")
    public abstract org.wb.gen.model.WorkplaceBooking toDto(WorkplaceBooking workplaceBooking);

    @Override
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "workplaceId", source = "workplace.id")
    @Mapping(target = "date", source = "bookingDate")
    public abstract org.wb.gen.model.WorkplaceBooking toListDto(WorkplaceBooking entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employee", qualifiedByName = "mapEmployee", source = "employeeId")
    @Mapping(target = "workplace", qualifiedByName = "mapWorkplace", source = "workplaceId")
    @Mapping(target = "bookingDate", source = "date")
    public abstract WorkplaceBooking fromCreateDto(WorkplaceBookingCreate createDto);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "workplace", qualifiedByName = "mapWorkplace", source = "workplaceId")
    @Mapping(target = "bookingDate", source = "date")
    public abstract WorkplaceBooking fromUpdateDto(WorkplaceBookingUpdate updateDto);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "workplace", qualifiedByName = "mapWorkplace", source = "workplaceId")
    @Mapping(target = "bookingDate", source = "date")
    public abstract void update(@MappingTarget WorkplaceBooking entity, WorkplaceBookingUpdate updateDto);

    @Named("mapEmployee")
    protected Employee maEmployee(long employeeId) {
        return employeeService.getRaw(employeeId);
    }

    @Named("mapWorkplace")
    protected Workplace mapWorkplace(long workplaceId) {
        return workplaceService.getRaw(workplaceId);
    }
}
