package com.jakubowski.clinic.model.appointment.dto;

import com.jakubowski.clinic.model.doctor.dto.DoctorDto;
import com.jakubowski.clinic.model.patient.dto.PatientDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AppointmentDto {
    public DoctorDto doctor;
    public PatientDto patient;
    public LocalDateTime date;
    public int duration;
}
