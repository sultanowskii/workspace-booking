package org.wb.components.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User implements org.wb.components.common.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", sequenceName = "users_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    @NotBlank
    private String username;

    @Column(name = "password", nullable = false)
    @NotBlank
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        EMPLOYEE,
        ADMIN
    }
}
