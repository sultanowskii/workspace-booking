package org.wb.components.workplace.visual;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.wb.components.workplace.Workplace;

@Entity
@Data
@Table(name = "workplace_visual")
public class WorkplaceVisual {
    @Id
    @Column(name = "workplace_id")
    private long workplaceId;

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @PrimaryKeyJoinColumn(name = "workplace_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Workplace workplace;

    @Column(name = "x", nullable = false)
    private double x;

    @Column(name = "y", nullable = false)
    private double y;

    @Column(name = "width", nullable = false)
    @Positive
    private double width;

    @Column(name = "height", nullable = false)
    @Positive
    private double height;
}
