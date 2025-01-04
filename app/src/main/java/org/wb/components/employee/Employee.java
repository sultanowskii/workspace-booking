package org.wb.components.employee;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.wb.components.employeegroup.EmployeeGroup;
import org.wb.components.meetingroom.booking.MeetingRoomBooking;
import org.wb.components.user.User;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name = "employee")
public class Employee implements org.wb.components.common.Entity {
    @Id
    @SequenceGenerator(name = "employee_seq", sequenceName = "employee_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_group_id", nullable = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private EmployeeGroup employeeGroup;

    @NotNull
    @NotBlank
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @ManyToMany(mappedBy = "meetingParticipants")
    private List<MeetingRoomBooking> meetings;
}
