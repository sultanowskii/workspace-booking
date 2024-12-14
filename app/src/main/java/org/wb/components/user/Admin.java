package org.wb.components.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@Table(name = "admin")
public class Admin implements org.wb.components.common.Entity {
    @Id
    @SequenceGenerator(name = "admin_seq", sequenceName = "admin_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @NotNull
    @NotBlank
    @Column(name = "full_name", nullable = false)
    private String fullName;
}
