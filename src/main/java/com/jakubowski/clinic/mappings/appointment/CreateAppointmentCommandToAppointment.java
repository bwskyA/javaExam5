package com.jakubowski.clinic.mappings.appointment;

import com.jakubowski.clinic.model.appointment.Appointment;
import com.jakubowski.clinic.model.appointment.command.CreateAppointmentCommand;
import com.jakubowski.clinic.model.doctor.Doctor;
import com.jakubowski.clinic.model.patient.Patient;
import com.jakubowski.clinic.service.DoctorService;
import com.jakubowski.clinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAppointmentCommandToAppointment implements Converter<CreateAppointmentCommand, Appointment> {
    private final DoctorService doctorService;
    private final PatientService patientService;
    @Override
    public Appointment convert(MappingContext<CreateAppointmentCommand, Appointment> mappingContext) {
        CreateAppointmentCommand command = mappingContext.getSource();
        Doctor doctor =  doctorService.findDoctor(command.getDoctorId());
        Patient patient = patientService.findPatient(command.getPatientId());
        return Appointment.builder()
                .doctor(doctor)
                .patient(patient)
                .date(command.getDate())
                .duration(command.getDuration())
                .build();
    }
}
