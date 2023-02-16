package com.jakubowski.clinic.model.doctor.command;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateDoctorCommand {
    @NotEmpty(message = "NAME_NOT_EMPTY")
    private String name;
    @NotEmpty(message = "LAST_NAME_NOT_EMPTY")
    private String lastname;
    @NotEmpty(message = "SPECIALITY_NOT_EMPTY")
    private String speciality;
    private long nip;
}
