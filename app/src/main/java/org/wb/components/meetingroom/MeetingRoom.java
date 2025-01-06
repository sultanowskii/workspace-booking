package org.wb.components.meetingroom;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.wb.components.meetingroom.visual.MeetingRoomVisual;
import org.wb.components.room.Room;

@Entity
@Data
public class MeetingRoom implements org.wb.components.common.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meeting_room_seq")
    @SequenceGenerator(name = "meeting_room_seq", sequenceName = "meeting_room_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Room room;

    @NotBlank
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @OneToOne(mappedBy = "meetingRoom", fetch = FetchType.LAZY)
    private MeetingRoomVisual visual;
}
