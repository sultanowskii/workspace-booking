package org.wb.components.workplace.booking;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.wb.components.user.Employee;
import org.wb.components.workplace.Workplace;

import java.time.LocalDate;

@Data
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "workplace_unique", columnNames = { "workplace_id", "booking_date" }),
        @UniqueConstraint(name = "employee_unique", columnNames = { "employee_id", "booking_date" })
})
public class WorkplaceBooking implements org.wb.components.common.Entity {

    @Id
    @SequenceGenerator(name = "workplace_booking_seq", sequenceName = "workplace_booking_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workplace_booking_seq")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "workplace_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Workplace workplace;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Employee employee;

    @NotNull
    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;
}