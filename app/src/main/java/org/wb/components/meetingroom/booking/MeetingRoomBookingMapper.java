package org.wb.components.meetingroom.booking;

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
import org.wb.components.meetingroom.MeetingRoom;
import org.wb.components.meetingroom.MeetingRoomService;
import org.wb.gen.model.MeetingRoomBookingCreate;
import org.wb.gen.model.MeetingRoomBookingShort;
import org.wb.gen.model.MeetingRoomBookingUpdate;

@Service
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class MeetingRoomBookingMapper implements
        EntityMapper<MeetingRoomBooking, org.wb.gen.model.MeetingRoomBooking, MeetingRoomBookingShort, MeetingRoomBookingCreate, MeetingRoomBookingUpdate> {
    @Autowired
    protected EmployeeService employeeService;
    @Autowired
    protected MeetingRoomService meetingRoomService;

    @Override
    @Mapping(target = "date", source = "bookingDate")
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "meetingRoomId", source = "meetingRoom.id")
    @Mapping(target = "participants", ignore = true)
    public abstract org.wb.gen.model.MeetingRoomBooking toDto(MeetingRoomBooking entity);

    @Override
    @Mapping(target = "date", source = "bookingDate")
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "meetingRoomId", source = "meetingRoom.id")
    public abstract MeetingRoomBookingShort toListDto(MeetingRoomBooking entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employee", qualifiedByName = "mapEmployee", source = "employeeId")
    @Mapping(target = "meetingRoom", qualifiedByName = "mapMeetingRoom", source = "meetingRoomId")
    @Mapping(target = "bookingDate", source = "date")
    @Mapping(target = "participants", ignore = true)
    public abstract MeetingRoomBooking fromCreateDto(MeetingRoomBookingCreate createDto);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "meetingRoom", qualifiedByName = "mapMeetingRoom", source = "meetingRoomId")
    @Mapping(target = "bookingDate", source = "date")
    @Mapping(target = "participants", ignore = true)
    public abstract MeetingRoomBooking fromUpdateDto(MeetingRoomBookingUpdate updateDto);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "meetingRoom", qualifiedByName = "mapMeetingRoom", source = "meetingRoomId")
    @Mapping(target = "bookingDate", source = "date")
    @Mapping(target = "participants", ignore = true)
    public abstract void update(@MappingTarget MeetingRoomBooking entity, MeetingRoomBookingUpdate updateDto);

    @Named("mapEmployee")
    protected Employee maEmployee(long employeeId) {
        return employeeService.getRaw(employeeId);
    }

    @Named("mapMeetingRoom")
    protected MeetingRoom mapMeetingRoom(long meetingRoomId) {
        return meetingRoomService.getRaw(meetingRoomId);
    }
}
