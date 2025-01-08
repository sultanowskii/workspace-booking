package org.wb.components.meetingroom.booking;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.wb.components.employee.Employee;
import org.wb.components.error.exception.InvalidBodyException;
import org.wb.components.meetingroom.MeetingRoom;
import org.wb.components.meetingroom.booking.participant.MeetingParticipant;

@Data
@Entity
@Table(name = "meeting_room_booking")
public class MeetingRoomBooking implements org.wb.components.common.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meeting_room_booking_seq")
    @SequenceGenerator(name = "meeting_room_booking_seq", sequenceName = "meeting_room_booking_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Employee employee;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_room_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MeetingRoom meetingRoom;

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

    @OneToMany(mappedBy = "meetingRoomBooking", fetch = FetchType.LAZY)
    private List<MeetingParticipant> participants = new ArrayList<>();

    // TODO: same for other models
    @PrePersist
    @PreUpdate
    private void validateTime() {
        if (endTime != null && startTime != null && !endTime.isAfter(startTime)) {
            throw new InvalidBodyException("End time must be after start time");
        }
    }
}
