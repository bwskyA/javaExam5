package com.jakubowski.clinic.model.doctor.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class DoctorDto {
    public String name;
    public String lastname;
    public String speciality;
    public String nip;
}
