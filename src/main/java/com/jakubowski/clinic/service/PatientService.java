package com.jakubowski.clinic.service;

import com.jakubowski.clinic.model.patient.Patient;
import com.jakubowski.clinic.repository.PatientRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {
    @Autowired
    private EntityManager entityManager;
    private final PatientRepository patientRepository;

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> findAllPatients() {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedPatientFilter");
        filter.setParameter("isDeleted", false);
        List<Patient> patientList = patientRepository.findAll();
        session.disableFilter("deletedPatientFilter");

        return patientList;
    }

    public Patient findPatient(long id) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedPatientFilter");
        filter.setParameter("isDeleted", false);
        Patient selectedPatient = patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Patient with id: %s not found!", id)));
        session.disableFilter("deletedPatientFilter");

        return selectedPatient;
    }

    public void deletePatient(long id) {
        patientRepository.deleteById(id);
    }

    @Transactional
    public Patient editPatient(long id, Patient patient) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedPatientFilter");
        filter.setParameter("isDeleted", false);
        Patient editedPatient = patientRepository.findById(id).map(patientEdit -> {
            patientEdit.setName(patient.getName());
            patientEdit.setLastname(patient.getLastname());
            patientEdit.setPesel(patient.getPesel());
            patientEdit.setEmail(patient.getEmail());

            return patientEdit;
        }).orElseThrow(() -> new ResourceNotFoundException(String.format("Patient with id: %s not found!", id)));
        session.disableFilter("deletedPatientFilter");

        return editedPatient;
    }

    @Transactional
    public Patient editPatientPartial(long id, Patient patient) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedPatientFilter");
        filter.setParameter("isDeleted", false);
        Patient editedPatient = patientRepository.findById(id).map(patientEdit -> {
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
        session.disableFilter("deletedPatientFilter");

        return editedPatient;
    }
}
