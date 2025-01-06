package org.wb.components.meetingroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wb.components.common.EntityService;
import org.wb.components.error.exception.PermissionDeniedException;
import org.wb.components.meetingroom.visual.MeetingRoomVisualRepository;
import org.wb.gen.model.MeetingRoomCreate;
import org.wb.gen.model.MeetingRoomUpdate;

import jakarta.transaction.Transactional;

@Service
public class MeetingRoomService extends
        EntityService<MeetingRoom, org.wb.gen.model.MeetingRoom, org.wb.gen.model.MeetingRoom, MeetingRoomCreate, MeetingRoomUpdate> {
    @Autowired
    protected MeetingRoomVisualRepository visualRepo;

    @Override
    protected boolean isListAllowed() {
        return true;
    }

    @Override
    protected boolean isCreateAllowed() {
        // return isCurrentUserAdmin();
        return true;
    }

    @Override
    protected boolean isReadAllowed(MeetingRoom meetingRoom) {
        return true;
    }

    @Override
    protected boolean isUpdateAllowed(MeetingRoom meetingRoom) {
        // return isCurrentUserAdmin();
        return true;
    }

    @Override
    protected boolean isDeleteAllowed(MeetingRoom meetingRoom) {
        // return isCurrentUserAdmin();
        return true;
    }

    @Override
    protected String entityName() {
        return "meeting room";
    }

    @Override
    @Transactional
    public org.wb.gen.model.MeetingRoom create(MeetingRoomCreate createDto) {
        if (!isCreateAllowed()) {
            throw new PermissionDeniedException("You can't create " + entityName());
        }
        var meetingRoom = mapper.fromCreateDto(createDto);
        meetingRoom.setVisual(null);
        var createdMeetingRoom = repo.save(meetingRoom);

        var meetingRoomVisual = mapper.fromCreateDto(createDto).getVisual();
        meetingRoomVisual.setMeetingRoom(createdMeetingRoom);
        meetingRoomVisual.setMeetingRoomId(createdMeetingRoom.getId());
        createdMeetingRoom.setVisual(meetingRoomVisual);
        var createdMeetingRoomVisual = visualRepo.save(meetingRoomVisual);

        createdMeetingRoom.setVisual(createdMeetingRoomVisual);

        return mapper.toDto(createdMeetingRoom);
    }

    @Override
    @Transactional
    public org.wb.gen.model.MeetingRoom update(long id, MeetingRoomUpdate updateDto) {
        var meetingRoom = getRaw(id);

        if (!isUpdateAllowed(meetingRoom)) {
            throw new PermissionDeniedException("You can't change this " + entityName());
        }

        mapper.update(meetingRoom, updateDto);
        meetingRoom.getVisual().setMeetingRoom(meetingRoom);
        meetingRoom.getVisual().setMeetingRoomId(meetingRoom.getId());
        var updatedMeetingRoom = repo.save(meetingRoom);
        return mapper.toDto(updatedMeetingRoom);
    }

    @Override
    @Transactional
    public void delete(long id) {
        var meetingRoom = getRaw(id);

        if (!isDeleteAllowed(meetingRoom)) {
            throw new PermissionDeniedException("You can't delete this " + entityName());
        }

        visualRepo.delete(meetingRoom.getVisual());
        repo.deleteById(id);
    }
}
