package org.wb.components.room;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.wb.components.office.Office;

@Entity
@Data
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
}
