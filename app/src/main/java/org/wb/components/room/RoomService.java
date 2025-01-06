package org.wb.components.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wb.components.common.EntityService;
import org.wb.components.error.exception.PermissionDeniedException;
import org.wb.components.room.wall.RoomWall;
import org.wb.components.room.wall.RoomWallRepository;
import org.wb.gen.model.RoomCreateUpdate;
import org.wb.gen.model.RoomWithWalls;

import jakarta.transaction.Transactional;

@Service
public class RoomService
        extends EntityService<Room, RoomWithWalls, org.wb.gen.model.Room, RoomCreateUpdate, RoomCreateUpdate> {
    @Autowired
    protected RoomWallRepository wallRepo;

    @Override
    protected boolean isListAllowed() {
        return true;
    }

    @Override
    protected boolean isCreateAllowed() {
        return isCurrentUserAdmin();
    }

    @Override
    protected boolean isReadAllowed(Room entity) {
        return true;
    }

    @Override
    protected boolean isUpdateAllowed(Room entity) {
        // return isCurrentUserAdmin();
        return true;
    }

    @Override
    protected boolean isDeleteAllowed(Room entity) {
        return true;
    }

    @Override
    protected String entityName() {
        return "room";
    }

    @Override
    public RoomWithWalls get(long id) {
        var entity = getRaw(id);
        if (!isReadAllowed(entity)) {
            throw new PermissionDeniedException("You can't access this " + entityName());
        }
        return mapper.toDto(entity);
    }

    @Override
    @Transactional
    public RoomWithWalls update(long id, RoomCreateUpdate updateDto) {
        var room = getRaw(id);

        if (!isUpdateAllowed(room)) {
            throw new PermissionDeniedException("You can't change this " + entityName());
        }

        wallRepo.deleteAllInBatch(room.getWalls());
        room.getWalls().clear();

        mapper.update(room, updateDto);
        room.getWalls().replaceAll(w -> {
            return new RoomWall(
                    null,
                    room,
                    w.getX1(),
                    w.getY1(),
                    w.getX2(),
                    w.getY2());
        });
        wallRepo.saveAll(room.getWalls());

        repo.save(room);
        return mapper.toDto(room);
    }
}
