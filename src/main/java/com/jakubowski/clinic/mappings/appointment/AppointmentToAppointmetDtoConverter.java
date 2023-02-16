package com.jakubowski.clinic.mappings.appointment;

import com.jakubowski.clinic.model.appointment.Appointment;
import com.jakubowski.clinic.model.appointment.dto.AppointmentDto;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
public class AppointmentToAppointmetDtoConverter implements Converter<Appointment,AppointmentDto> {
    @Override
    public AppointmentDto convert(MappingContext<Appointment, AppointmentDto> mappingContext) {
        Appointment appointment = mappingContext.getSource();
        return AppointmentDto.builder()
                .id(appointment.getId())
                .patient(appointment.getPatient())
                .doctor(appointment.getDoctor())
                .duration(appointment.getDuration())
                .date(appointment.getDate()).build();
    }
}
