package org.wb.components.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wb.components.meetingroom.MeetingRoomMapper;
import org.wb.components.meetingroom.MeetingRoomRepository;
import org.wb.components.room.wall.RoomWallMapper;
import org.wb.components.workplace.WorkplaceMapper;
import org.wb.components.workplace.WorkplaceRepository;
import org.wb.gen.model.RoomLayout;

@Service
public class RoomLayoutService {
    @Autowired
    protected RoomRepository repo;
    @Autowired
    protected WorkplaceRepository workplaceRepo;
    @Autowired
    protected MeetingRoomRepository meetingRoomRepo;
    @Autowired
    protected RoomWallMapper wallMapper;
    @Autowired
    protected WorkplaceMapper workplaceMapper;
    @Autowired
    protected MeetingRoomMapper meetingRoomMapper;

    public RoomLayout geRoomLayout(long id) {
        var room = repo
                .findAll((root, query, builder) -> {
                    root.fetch("walls");
                    return builder.equal(root.get("id"), id);
                })
                .getFirst();

        var workplaces = workplaceRepo.findAll((root, query, builder) -> {
            root.fetch("visual");
            root.fetch("room");
            return builder.equal(root.get("room").get("id"), id);
        });

        var meetingRooms = meetingRoomRepo.findAll((root, query, builder) -> {
            root.fetch("visual");
            root.fetch("room");
            return builder.equal(root.get("room").get("id"), id);
        });

        return new RoomLayout()
                .id(room.getId())
                .name(room.getName())
                .officeId(room.getOffice().getId())
                .walls(room.getWalls().stream().map(w -> wallMapper.toDto(w)).toList())
                .workplaces(workplaces.stream().map(w -> workplaceMapper.toDto(w)).toList())
                .meetingRooms(meetingRooms.stream().map(w -> meetingRoomMapper.toDto(w)).toList());
    }
}
