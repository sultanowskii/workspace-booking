package org.wb.components.office;

import java.util.List;

import org.wb.components.employeegroup.EmployeeGroup;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Office implements org.wb.components.common.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "office_seq")
    @SequenceGenerator(name = "office_seq", sequenceName = "office_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @NotBlank
    @Column(name = "address", nullable = false)
    private String address;

    @ManyToMany(mappedBy = "allowedOffices")
    private List<EmployeeGroup> employeeGroups;
}
