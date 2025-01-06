package org.wb.components.workplace;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import lombok.Data;

import org.wb.components.room.Room;
import org.wb.components.workplace.visual.WorkplaceVisual;

@Data
@Entity
public class Workplace implements org.wb.components.common.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workplace_seq")
    @SequenceGenerator(name = "workplace_seq", sequenceName = "workplace_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Room room;

    @PositiveOrZero
    @Column(name = "number_of_monitors", nullable = false)
    private int numberOfMonitors;

    @NotNull
    @OneToOne(mappedBy = "workplace")
    private WorkplaceVisual visual;
}
