package org.wb.components.workplace.visual;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.wb.components.workplace.Workplace;

@Entity
@Data
public class WorkplaceVisual implements org.wb.components.common.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workplace_visual_seq")
    @SequenceGenerator(name = "workplace_visual_seq", sequenceName = "workplace_visual_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "workplace_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Workplace workplace;

    @Column(name = "x", nullable = false)
    @NotNull
    private double x;

    @Column(name = "y", nullable = false)
    @NotNull
    private double y;

    @Column(name = "width", nullable = false)
    @NotNull
    @Positive
    private double width;

    @Column(name = "height", nullable = false)
    @NotNull
    @Positive
    private double height;
}
