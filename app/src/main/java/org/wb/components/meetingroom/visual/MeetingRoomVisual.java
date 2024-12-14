package org.wb.components.meetingroom.visual;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.wb.components.meetingroom.MeetingRoom;

@Data
@Entity
@Table(name = "meeting_room_visual")
public class MeetingRoomVisual implements org.wb.components.common.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meeting_room_visual_seq")
    @SequenceGenerator(name = "meeting_room_visual_seq", sequenceName = "meeting_room_visual_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "meeting_room_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MeetingRoom meetingRoom;

    @NotNull
    @Column(name = "x", nullable = false)
    private double x;

    @NotNull
    @Column(name = "y", nullable = false)
    private double y;

    @NotNull
    @Positive
    @Column(name = "width", nullable = false)
    private double width;

    @Positive
    @NotNull
    @Column(name = "height", nullable = false)
    private double height;
}
