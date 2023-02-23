package com.jakubowski.clinic.service;

import com.jakubowski.clinic.error.IllegalDateRangeExeption;
import com.jakubowski.clinic.model.appointment.Appointment;
import com.jakubowski.clinic.model.doctor.Doctor;
import com.jakubowski.clinic.model.patient.Patient;
import com.jakubowski.clinic.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public Appointment addAppointment(Appointment appointment) {
        countEndDate(appointment);
        if (checkAppointmentDatesForDoctorAndPatient(appointment.getDoctor(), appointment.getPatient(), appointment.getDate(), appointment.getEndAppointmend())) {
            throw new IllegalDateRangeExeption("Incorrect date range used, dates overlap with another visit");
        }
        return appointmentRepository.save(appointment);
    }

    public void confirmAppointment(long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow();
        appointment.setConfirmed(true);
    }

    public void cancelAppointment(long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow();
        appointment.setCancelled(true);
    }

    public boolean checkAppointmentDoctor(Doctor doctor, LocalDateTime start, LocalDateTime end) {
        List<Appointment> appointmentListWithDoctor = appointmentRepository.findAll()
                .stream().filter(visit -> visit.getDoctor().equals(doctor)).toList();

        return appointmentListWithDoctor.stream().anyMatch(v -> end.isBefore(v.getDate()) || (v.getEndAppointmend().isAfter(start)));
    }

    public boolean checkAppointmentDatesForDoctorAndPatient(Doctor doctor, Patient patient, LocalDateTime start, LocalDateTime end) {
        List<Appointment> allAppointments = appointmentRepository.findAll();
        List<Appointment> appointmentListWithDoctor = allAppointments
                .stream().filter(visit -> visit.getDoctor().equals(doctor)).toList();
        List<Appointment> appointmentListWithPatients = allAppointments
                .stream().filter(visit -> visit.getPatient().equals(patient)).toList();

        if (appointmentListWithPatients.stream().anyMatch(v -> end.isBefore(v.getDate()) || (v.getEndAppointmend().isAfter(start))) ||
                appointmentListWithDoctor.stream().anyMatch(v -> end.isBefore(v.getDate()) || (v.getEndAppointmend().isAfter(start)))) {
            return true;
        }
        return false;
    }

    public boolean checkAppointmentPatient(Patient patient, LocalDateTime start, LocalDateTime end) {
        List<Appointment> appointmentListWithPatients = appointmentRepository.findAll()
                .stream().filter(visit -> visit.getPatient().equals(patient)).toList();

        return appointmentListWithPatients.stream()
                .anyMatch(v -> end.isBefore(v.getDate()) || (v.getEndAppointmend().isAfter(start)));
    }

    public void countEndDate(Appointment appointment) {
        appointment.setEndAppointmend(appointment.getDate().plusHours(appointment.getDuration()));
    }
}
