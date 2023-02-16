package com.jakubowski.clinic.repository;

import com.jakubowski.clinic.model.doctor.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
