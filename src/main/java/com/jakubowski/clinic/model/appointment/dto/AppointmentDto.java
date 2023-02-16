package com.jakubowski.clinic.model.appointment.dto;

import com.jakubowski.clinic.model.doctor.Doctor;
import com.jakubowski.clinic.model.patient.Patient;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class AppointmentDto {
    long id;
    Doctor doctor;
    Patient patient;
    LocalDateTime date;
    int duration;
}
