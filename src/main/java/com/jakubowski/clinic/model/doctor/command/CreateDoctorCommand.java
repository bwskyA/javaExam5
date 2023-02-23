package com.jakubowski.clinic.model.doctor.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateDoctorCommand {
    private String name;
    private String lastname;
    private String speciality;
    private String nip;
}
