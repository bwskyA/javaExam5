package com.jakubowski.clinic.service;

import com.jakubowski.clinic.model.patient.Patient;
import com.jakubowski.clinic.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }

    public Patient findPatient(long id) {
        return patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Patient with id: %s not found!", id)));
    }

    public void deletePatient(long id) {
        Patient patient = patientRepository.findById(id).orElseThrow();
        patient.setDeleted(true);
    }

    @Transactional
    public Patient editPatient(long id, Patient patient) {
        return patientRepository.findById(id).map(patientEdit -> {
            patientEdit.setName(patient.getName());
            patientEdit.setLastname(patient.getLastname());
            patientEdit.setPesel(patient.getPesel());
            patientEdit.setEmail(patient.getEmail());

            return patientEdit;
        }).orElseThrow(() -> new ResourceNotFoundException(String.format("Patient with id: %s not found!", id)));
    }

    @Transactional
    public Patient editPatientPartial(long id, Patient patient) {
        return patientRepository.findById(id).map(patientEdit -> {
            Optional.ofNullable(patient.getName()).ifPresent(patientEdit::setName);
            Optional.ofNullable(patient.getLastname()).ifPresent(patientEdit::setLastname);
            if (patient.getEmail() != null) {
                Optional.of(patient.getEmail()).ifPresent(patientEdit::setEmail);
            }
            if (patient.getPesel() != null) {
                Optional.of(patient.getPesel()).ifPresent(patientEdit::setPesel);
            }

            return patientEdit;
        }).orElseThrow(() -> new ResourceNotFoundException(String.format("Patient with id: %s not found!", id)));
    }
}
