package com.jakubowski.clinic.service;

import com.jakubowski.clinic.model.doctor.Doctor;
import com.jakubowski.clinic.model.patient.Patient;
import com.jakubowski.clinic.repository.PatientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PatientServiceTest {
    @Autowired
    private PatientRepository patientRepository;

    @Test
    void savePatient() {
       Patient patient = new Patient(2, "Michal", "Lewandowski", "1234567879", "dupa@gmail.com", false);
        Assertions.assertNotNull(patientRepository.save(patient));
    }

    @Test
    void findAllPatients() {
        Patient patient = new Patient(3, "Michal", "Lewandowski", "1234567879", "dupa@gmail.com", false);
        Patient patient2 = new Patient(4, "Ania", "Kowalska", "2233445566", "dupaaa@gmail.com", false);
        patientRepository.save(patient);
        patientRepository.save(patient2);

        assertEquals(2, patientRepository.findAll().size());
    }

    @Test
    void findPatient() {
        Patient patient = new Patient(3, "Michal", "Lewandowski", "1234567879", "dupa@gmail.com", false);
        Assertions.assertNotNull(patientRepository.findById(patient.getId()));
    }

    @Test
    void deletePatient() {
        Patient patient = new Patient(3, "Michal", "Lewandowski", "1234567879", "dupa@gmail.com", false);
        patient.setDeleted(true);
        Assertions.assertTrue(patient.isDeleted());
    }
}