package com.jakubowski.clinic.repository;

import com.jakubowski.clinic.model.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
