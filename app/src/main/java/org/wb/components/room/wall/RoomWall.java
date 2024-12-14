package org.wb.components.room.wall;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import org.wb.components.room.Room;

@Entity
@Table(name = "room_wall")
@Data
public class RoomWall implements org.wb.components.common.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_wall_seq")
    @SequenceGenerator(name = "room_wall_seq", sequenceName = "room_wall_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Room room;

    @NotNull
    @Column(name = "x1", nullable = false)
    private double x1;

    @NotNull
    @Column(name = "y1", nullable = false)
    private double y1;

    @NotNull
    @Column(name = "x2", nullable = false)
    private double x2;

    @NotNull
    @Column(name = "y2", nullable = false)
    private double y2;
}
