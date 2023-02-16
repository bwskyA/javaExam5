package com.jakubowski.clinic.service;

import com.jakubowski.clinic.model.doctor.Doctor;
import com.jakubowski.clinic.repository.DoctorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
class DoctorServiceTest {
    @Autowired
    private DoctorRepository doctorRepository;


    @Test
    void saveDoctor() {
        Doctor doctor = new Doctor(2, "Adam", "Jakubowski"
                , "dentists", 123456789, false);
        Assertions.assertNotNull(doctorRepository.save(doctor));
    }

    @Test
    void shouldSizeOfListWillBe2() {
        Doctor doctor = new Doctor(2, "Adam", "Kwiatkowski"
                , "dentists", 123456789, false);
        Doctor doctor2 = new Doctor(2, "Michal", "Kowalski"
                , "dentists", 123456786, false);
        doctorRepository.save(doctor);
        doctorRepository.save(doctor2);

        assertEquals(2, this.doctorRepository.findAll().size());
    }

    @Test
    void findDoctor() {
        Doctor doctor = new Doctor(3, "Adam", "Jakubowski"
                , "dentists", 123456789, false);
        Assertions.assertNotNull(doctorRepository.findById(doctor.getId()));
    }

    @Test
    void deleteDoctor() {
        Doctor doctor = new Doctor(3, "Adam", "Jakubowski"
                , "dentists", 123456789, false);
        doctor.setDeleted(true);
        Assertions.assertTrue(doctor.isDeleted());
    }

}