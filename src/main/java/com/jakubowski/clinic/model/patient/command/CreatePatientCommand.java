package com.jakubowski.clinic.model.patient.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreatePatientCommand {
    private String name;
    private String lastname;
    private String pesel;
    private String email;
}
