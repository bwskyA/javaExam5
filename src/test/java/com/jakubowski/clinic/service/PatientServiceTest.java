package com.jakubowski.clinic.service;

import com.jakubowski.clinic.model.patient.Patient;
import com.jakubowski.clinic.repository.PatientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class PatientServiceTest {

    @Autowired
    private PatientService patientService;
    @MockBean
    private PatientRepository patientRepository;
    @Test
    void testIfPatientSave() {
        Patient patient = Patient.builder()
                .id(1)
                .name("Michał")
                .lastname("Kowalski")
                .email("m.kowalski@gmail.com")
                .pesel("12345678901")
                .build();

        when(patientRepository.save(patient)).thenReturn(patient);
        Assertions.assertThat(patientService.savePatient(patient)).isEqualTo(patient);
    }

    @Test
    void testGetPatientById() {
        Patient patient = Patient.builder()
                .id(1)
                .name("Michał")
                .lastname("Kowalski")
                .email("m.kowalski@gmail.com")
                .pesel("12345678901")
                .build();

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        Assertions.assertThat(patientService.findPatient(1L)).isEqualTo(patient);
    }

    @Test
    void testGetAllPatients() {
        Patient patient = Patient.builder()
                .id(1)
                .name("Michał")
                .lastname("Kowalski")
                .email("m.kowalski@gmail.com")
                .pesel("12345678901")
                .build();

        Patient patient2 = Patient.builder()
                .id(2)
                .name("Jan")
                .lastname("Kowalski")
                .email("j.kowalski@gmail.com")
                .pesel("00987654321")
                .build();

        List<Patient> patientList = new ArrayList<>();
        patientList.add(patient);
        patientList.add(patient2);

        when(patientRepository.findAll()).thenReturn(patientList);
        Assertions.assertThat(patientService.findAllPatients()).isEqualTo(patientList);
    }

    @Test
    void testIfPatientDelete() {
        Patient patient = Patient.builder()
                .id(1)
                .name("Michał")
                .lastname("Kowalski")
                .email("m.kowalski@gmail.com")
                .pesel("12345678901")
                .build();

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        assertAll(() -> patientService.deletePatient(1L));
    }

    @Test
    void testUpdatePatient() {
        Patient patient = Patient.builder()
                .id(1)
                .name("Michał")
                .lastname("Kowalski")
                .email("m.kowalski@gmail.com")
                .pesel("12345678901")
                .build();

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(patientRepository.save(Mockito.any(Patient.class))).thenReturn(patient);

        Patient updatedPatient = patientService.editPatient(1, patient);
        Assertions.assertThat(updatedPatient).isNotNull();
    }

    @Test
    void testParialUpdatePatient() {
        Patient patient = Patient.builder()
                .id(1)
                .name("Michał")
                .lastname("Kowalski")
                .email("m.kowalski@gmail.com")
                .pesel("12345678901")
                .build();

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(patientRepository.save(Mockito.any(Patient.class))).thenReturn(patient);

        Patient updatedPatient = patientService.editPatientPartial(1L, patient);
        Assertions.assertThat(updatedPatient).isNotNull();
    }

}