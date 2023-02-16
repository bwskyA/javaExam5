package com.jakubowski.clinic.model.appointment;

import com.jakubowski.clinic.model.doctor.Doctor;
import com.jakubowski.clinic.model.patient.Patient;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long id;
    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Doctor doctor;
    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Patient patient;
    @Future
    private LocalDateTime date;
    @Positive
    private int duration;

    private LocalDateTime endAppointmend;
    private boolean confirmed = false;
    private boolean cancelled = false;
}
