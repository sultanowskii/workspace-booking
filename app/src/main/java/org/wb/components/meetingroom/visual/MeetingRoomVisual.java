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
public class MeetingRoomVisual {
    @Id
    @Column(name = "meeting_room_id")
    private long meetingRoomId;

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @PrimaryKeyJoinColumn(name = "meeting_room_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private MeetingRoom meetingRoom;

    @Column(name = "x", nullable = false)
    private double x;

    @Column(name = "y", nullable = false)
    private double y;

    @Positive
    @Column(name = "width", nullable = false)
    private double width;

    @Positive
    @Column(name = "height", nullable = false)
    private double height;
}
