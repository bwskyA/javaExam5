package com.jakubowski.clinic.model.doctor.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DoctorDto {
    long id;
    String name;
    String lastname;
    String speciality;
    long nip;

}
