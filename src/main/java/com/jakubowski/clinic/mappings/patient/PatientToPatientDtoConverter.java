package com.jakubowski.clinic.mappings.patient;

import com.jakubowski.clinic.model.patient.Patient;
import com.jakubowski.clinic.model.patient.dto.PatientDto;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
public class PatientToPatientDtoConverter implements Converter<Patient, PatientDto> {
    @Override
    public PatientDto convert(MappingContext<Patient, PatientDto> mappingContext) {
        Patient patient = mappingContext.getSource();
        return PatientDto.builder()
                .id(patient.getId())
                .name(patient.getName())
                .lastname(patient.getLastname())
                .email(patient.getEmail())
                .pesel(patient.getPesel()).build();
    }
}
