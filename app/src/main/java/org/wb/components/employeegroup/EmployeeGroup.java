package org.wb.components.employeegroup;

import java.util.List;

import org.wb.components.office.Office;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "employee_group", uniqueConstraints = { @UniqueConstraint(columnNames = "name") })
public class EmployeeGroup implements org.wb.components.common.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_group_seq")
    @SequenceGenerator(name = "employee_group_seq", sequenceName = "employee_group_id_seq", allocationSize = 1)
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany
    @JoinTable(name = "office_employee_group", joinColumns = @JoinColumn(name = "employee_group_id"), inverseJoinColumns = @JoinColumn(name = "office_id"))
    private List<Office> allowedOffices;
}
