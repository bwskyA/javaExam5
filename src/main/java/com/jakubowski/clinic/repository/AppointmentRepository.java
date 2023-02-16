package com.jakubowski.clinic.repository;

import com.jakubowski.clinic.model.appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
