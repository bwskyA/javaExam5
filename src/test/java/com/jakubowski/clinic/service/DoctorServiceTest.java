package com.jakubowski.clinic.service;

import com.jakubowski.clinic.model.doctor.Doctor;
import com.jakubowski.clinic.repository.DoctorRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@SpringBootTest
class DoctorServiceTest {
    @Autowired
    private DoctorService doctorService;
    @MockBean
    private DoctorRepository doctorRepository;


    @Test
    void testIfDoctorSave() {
        Doctor doctor = Doctor.builder()
                .id(1)
                .name("Michal")
                .lastname("Kowalski")
                .speciality("dentist")
                .nip("1234567890")
                .build();

        when(doctorRepository.save(doctor)).thenReturn(doctor);
        Assertions.assertThat(doctorService.saveDoctor(doctor)).isEqualTo(doctor);
    }

    @Test
    void testGetDoctorById() {
        Doctor doctor = Doctor.builder()
                .id(1)
                .name("Michal")
                .lastname("Kowalski")
                .speciality("dentist")
                .nip("1234567890")
                .build();

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        Assertions.assertThat(doctorService.findDoctor(1)).isEqualTo(doctor);
    }

    @Test
    void testGetAllDoctors() {
        Doctor doctor = Doctor.builder()
                .id(1)
                .name("Michal")
                .lastname("Kowalski")
                .speciality("dentist")
                .nip("1234567890")
                .build();

        Doctor doctor2 = Doctor.builder()
                .id(2)
                .name("Adam")
                .lastname("Kowalski")
                .speciality("dentist")
                .nip("1234567899")
                .build();

        List<Doctor> doctorList = new ArrayList<>();
        doctorList.add(doctor);
        doctorList.add(doctor2);

        when(doctorRepository.findAll()).thenReturn(doctorList);
        Assertions.assertThat(doctorService.findAllDoctors()).isEqualTo(doctorList);
    }

    @Test
    void testIfDoctorDelete() {
        Doctor doctor = Doctor.builder()
                .id(1)
                .name("Michal")
                .lastname("Wiśniewski")
                .speciality("dentist")
                .nip("0187654321")
                .build();

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        assertAll(() -> doctorService.deleteDoctor(1));
    }

    @Test
    void testUpdateDoctor() {
        Doctor doctor = Doctor.builder()
                .id(1)
                .name("Michal")
                .lastname("Wiśniewski")
                .speciality("dentist")
                .nip("0187654321")
                .build();

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(doctorRepository.save(Mockito.any(Doctor.class))).thenReturn(doctor);

        Doctor updatedDoctor = doctorService.editDoctorFull(1, doctor);
        Assertions.assertThat(updatedDoctor).isNotNull();
    }

    @Test
    void testParialUpdateDoctor() {
        Doctor doctor = Doctor.builder()
                .id(1)
                .name("Michal")
                .lastname("Wiśniewski")
                .speciality("dentist")
                .nip("0187654321")
                .build();

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(doctorRepository.save(Mockito.any(Doctor.class))).thenReturn(doctor);

        Doctor updatedDoctor = doctorService.editDoctorParts(1, doctor);
        Assertions.assertThat(updatedDoctor).isNotNull();
    }
}