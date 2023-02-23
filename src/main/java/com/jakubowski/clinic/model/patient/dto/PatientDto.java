package com.jakubowski.clinic.model.patient.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class PatientDto {
    public String name;
    public String lastname;
    public String pesel;
    public String email;
}
