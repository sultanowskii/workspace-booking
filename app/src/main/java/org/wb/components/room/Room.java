package org.wb.components.room;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.wb.components.meetingroom.MeetingRoom;
import org.wb.components.office.Office;
import org.wb.components.room.wall.RoomWall;
import org.wb.components.workplace.Workplace;

@Entity
@Data
@NoArgsConstructor
public class Room implements org.wb.components.common.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_seq")
    @SequenceGenerator(name = "room_seq", sequenceName = "room_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "office_id", nullable = false)
    private Office office;

    @NotBlank
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<RoomWall> walls = new ArrayList<>();

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<Workplace> workplaces = new ArrayList<>();

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<MeetingRoom> meetingRooms = new ArrayList<>();
}
