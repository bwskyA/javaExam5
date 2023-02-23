package com.jakubowski.clinic.service;

import com.jakubowski.clinic.model.appointment.Appointment;
import com.jakubowski.clinic.model.doctor.Doctor;
import com.jakubowski.clinic.model.patient.Patient;
import com.jakubowski.clinic.repository.AppointmentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.time.Month;

import static org.mockito.Mockito.when;

@SpringBootTest
class AppointmentServiceTest {
    @Autowired
    private AppointmentService appointmentService;
    @MockBean
    private AppointmentRepository appointmentRepository;

    @Test
    void testAddAppointment() {
        Doctor doctor = Doctor.builder()
                .id(1)
                .name("Michal")
                .lastname("Kowalski")
                .speciality("dentist")
                .nip("1234567890")
                .build();

        Patient patient = Patient.builder()
                .id(1)
                .name("Michał")
                .lastname("Kowalski")
                .email("m.kowalski@gmail.com")
                .pesel("12345678901")
                .build();
        Appointment appointment = Appointment.builder()
                .id(1)
                .doctor(doctor)
                .patient(patient)
                .date(LocalDateTime.of(2023, Month.JANUARY, 3, 6, 30, 40, 50000))
                .duration(3)
                .build();

        when(appointmentRepository.save(appointment)).thenReturn(appointment);
        Assertions.assertThat(appointmentService.addAppointment(appointment)).isEqualTo(appointment);
    }
}