package com.jakubowski.clinic.mappings.doctor;

import com.jakubowski.clinic.model.doctor.Doctor;
import com.jakubowski.clinic.model.doctor.dto.DoctorDto;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
public class DoctorToDoctorDtoConverter implements Converter<Doctor, DoctorDto> {
    @Override
    public DoctorDto convert(MappingContext<Doctor, DoctorDto> mappingContext) {
        Doctor doctor =  mappingContext.getSource();
        return DoctorDto.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .lastname(doctor.getLastname())
                .speciality(doctor.getSpeciality())
                .nip(doctor.getNip()).build();
    }
}
