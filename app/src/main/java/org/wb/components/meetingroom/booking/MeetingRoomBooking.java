package org.wb.components.meetingroom.booking;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.wb.components.user.Employee;
import org.wb.components.workplace.Workplace;

@Data
@Entity
@Table(name = "meeting_room_bookings")
public class MeetingRoomBooking implements org.wb.components.common.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meeting_room_booking_seq")
    @SequenceGenerator(name = "meeting_room_booking_seq", sequenceName = "meeting_room_booking_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Employee employee;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "workplace_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Workplace workplace;

    @NotNull
    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @NotBlank
    @NotNull
    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(name = "meeting_participant", joinColumns = @JoinColumn(name = "meeting_room_booking_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"), uniqueConstraints = @UniqueConstraint(columnNames = {
            "meeting_room_booking_id", "employee_id" }))
    private List<Employee> meetingParticipants;

    // TODO: same for other models
    @PrePersist
    @PreUpdate
    private void validateTime() {
        if (endTime != null && startTime != null && !endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("End time must be after start time");
        }
    }
}
