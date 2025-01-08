package org.wb.components.meetingroom.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wb.components.common.EntityService;
import org.wb.components.employee.EmployeeMapper;
import org.wb.components.employee.EmployeeService;
import org.wb.components.error.exception.PermissionDeniedException;
import org.wb.components.meetingroom.booking.participant.MeetingParticipant;
import org.wb.components.meetingroom.booking.participant.MeetingParticipantRepository;
import org.wb.gen.model.MeetingRoomBookingCreate;
import org.wb.gen.model.MeetingRoomBookingShort;
import org.wb.gen.model.MeetingRoomBookingUpdate;

import jakarta.transaction.Transactional;

@Service
public class MeetingRoomBookingService extends
        EntityService<MeetingRoomBooking, org.wb.gen.model.MeetingRoomBooking, MeetingRoomBookingShort, MeetingRoomBookingCreate, MeetingRoomBookingUpdate> {
    @Autowired
    protected MeetingParticipantRepository participantRepo;
    @Autowired
    protected EmployeeService employeeService;
    @Autowired
    protected EmployeeMapper employeeMapper;

    @Override
    protected boolean isListAllowed() {
        return true;
    }

    @Override
    protected boolean isCreateAllowed() {
        return true;
    }

    @Override
    protected boolean isReadAllowed(MeetingRoomBooking meetingRoomBooking) {
        return true;
    }

    @Override
    protected boolean isUpdateAllowed(MeetingRoomBooking meetingRoomBooking) {
        return isCurrentUserOwnerOf(meetingRoomBooking);
    }

    @Override
    protected boolean isDeleteAllowed(MeetingRoomBooking meetingRoomBooking) {
        return isCurrentUserOwnerOf(meetingRoomBooking);
    }

    protected boolean isCurrentUserOwnerOf(MeetingRoomBooking meetingRoomBooking) {
        var currentUser = userService.getCurrentUser();
        return meetingRoomBooking.getEmployee().getUser().equals(currentUser);
    }

    @Override
    protected String entityName() {
        return "meeting room booking";
    }

    @Override
    public org.wb.gen.model.MeetingRoomBooking get(long id) {
        var entity = getRaw(id);
        if (!isReadAllowed(entity)) {
            throw new PermissionDeniedException("You can't access this " + entityName());
        }
        var dto = mapper.toDto(entity);
        var employees = participantRepo
                .findAll((root, query, builder) -> {
                    root.fetch("employee");
                    root.fetch("employee").fetch("user");
                    return builder.equal(root.get("meetingRoomBooking").get("id"), id);
                })
                .stream()
                .map(p -> {
                    return employeeMapper.toDto(p.getEmployee());
                })
                .toList();
        dto.setParticipants(employees);
        return dto;
    }

    @Override
    @Transactional
    public org.wb.gen.model.MeetingRoomBooking create(MeetingRoomBookingCreate createDto) {
        if (!isCreateAllowed()) {
            throw new PermissionDeniedException("You can't create " + entityName());
        }
        var booking = mapper.fromCreateDto(createDto);
        var createdBooking = repo.save(booking);

        var participants = createDto
                .getParticipants()
                .stream()
                .map(employeeId -> {
                    return new MeetingParticipant(
                            null,
                            createdBooking,
                            employeeService.getRaw(employeeId));
                })
                .toList();

        for (var p : participants) {
            createdBooking.getParticipants().add(p);
        }
        participantRepo.saveAll(createdBooking.getParticipants());

        var dto = mapper.toDto(createdBooking);
        dto.setParticipants(booking
                .getParticipants()
                .stream()
                .map(p -> employeeMapper.toDto(p.getEmployee()))
                .toList());
        return dto;
    }

    @Override
    public org.wb.gen.model.MeetingRoomBooking update(long id, MeetingRoomBookingUpdate updateDto) {
        var booking = getRaw(id);

        if (!isUpdateAllowed(booking)) {
            throw new PermissionDeniedException("You can't change this " + entityName());
        }

        participantRepo.deleteAllInBatch(booking.getParticipants());
        booking.getParticipants().clear();

        var participants = updateDto
                .getParticipants()
                .stream()
                .map(employeeId -> new MeetingParticipant(
                        null,
                        booking,
                        employeeService.getRaw(employeeId)))
                .toList();

        mapper.update(booking, updateDto);
        for (var p : participants) {
            booking.getParticipants().add(p);
        }
        participantRepo.saveAll(booking.getParticipants());

        var updatedBooking = repo.save(booking);
        var dto = mapper.toDto(updatedBooking);
        dto.setParticipants(booking
                .getParticipants()
                .stream()
                .map(p -> employeeMapper.toDto(p.getEmployee()))
                .toList());
        return dto;
    }
}
