package com.jakubowski.clinic.model.appointment.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class CreateAppointmentCommand {
    private long doctorId;
    private long patientId;
    private LocalDateTime date;
    private int duration;
}
