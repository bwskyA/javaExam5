package com.jakubowski.clinic.service;

import com.jakubowski.clinic.model.appointment.Appointment;
import com.jakubowski.clinic.model.doctor.Doctor;
import com.jakubowski.clinic.model.patient.Patient;
import com.jakubowski.clinic.repository.AppointmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

@DataJpaTest
class AppointmentServiceTest {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    void addAppointment() {
        Patient patient = new Patient(3, "Michal", "Lewandowski", "1234567879", "dupa@gmail.com", false);
        Doctor doctor = new Doctor(3, "Adam", "Jakubowski"
                , "dentists", 123456789, false);
        LocalDateTime currentTime = LocalDateTime.now().plusHours(2);

        Appointment appointment = new Appointment(2, doctor, patient, currentTime, 3, currentTime.plusHours(3) ,false, false);

        Assertions.assertNotNull(appointmentRepository.save(appointment));
    }

    @Test
    void confirmAppointment() {
        Patient patient = new Patient(3, "Michal", "Lewandowski", "1234567879", "dupa@gmail.com", false);
        Doctor doctor = new Doctor(3, "Adam", "Jakubowski"
                , "dentists", 123456789, false);
        LocalDateTime currentTime = LocalDateTime.now();

        Appointment appointment = new Appointment(2, doctor, patient, currentTime, 3, currentTime.plusHours(3) ,false, false);
        appointment.setConfirmed(true);

        Assertions.assertTrue(appointment.isConfirmed());
    }

    @Test
    void cancellAppointment() {
        Patient patient = new Patient(3, "Michal", "Lewandowski", "1234567879", "dupa@gmail.com", false);
        Doctor doctor = new Doctor(3, "Adam", "Jakubowski"
                , "dentists", 123456789, false);
        LocalDateTime currentTime = LocalDateTime.now();

        Appointment appointment = new Appointment(2, doctor, patient, currentTime, 3, currentTime.plusHours(3) ,false, false);
        appointment.setCancelled(true);

        Assertions.assertTrue(appointment.isCancelled());
    }
}