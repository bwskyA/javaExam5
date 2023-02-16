package com.jakubowski.clinic.model.patient.command;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreatePatientCommand {
    @NotEmpty(message = "NAME_NOT_EMPTY")
    private String name;
    @NotEmpty(message = "LAST_NAME_NOT_EMPTY")
    private String lastname;
    @Column(unique = true)
    private String pesel;
    @Column(unique = true)
    private String email;
}
