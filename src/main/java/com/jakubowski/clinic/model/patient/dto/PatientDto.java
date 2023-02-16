package com.jakubowski.clinic.model.patient.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PatientDto {
    long id;
    String name;
    String lastname;
    String pesel;
    String email;
}
