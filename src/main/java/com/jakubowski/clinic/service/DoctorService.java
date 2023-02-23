package com.jakubowski.clinic.service;

import com.jakubowski.clinic.model.doctor.Doctor;
import com.jakubowski.clinic.repository.DoctorRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorService {
    @Autowired
    private EntityManager entityManager;
    private final DoctorRepository doctorRepository;

    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public List<Doctor> findAllDoctors() {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedDoctorFilter");
        filter.setParameter("isDeleted", false);
        List<Doctor> doctors = doctorRepository.findAll();
        session.disableFilter("deletedDoctorFilter");

        return doctors;
    }

    public Doctor findDoctor(long id) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedDoctorFilter");
        filter.setParameter("isDeleted", false);
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Doctor with id: %s not found!", id)));
        session.disableFilter("deletedDoctorFilter");

        return doctor;
    }

    public void deleteDoctor(long id) {
        doctorRepository.deleteById(id);
    }

    @Transactional
    public Doctor editDoctorFull(long id, Doctor doctor) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedDoctorFilter");
        filter.setParameter("isDeleted", false);
        Doctor editedDoctor = doctorRepository.findById(id).map(docEdit -> {
            docEdit.setName(doctor.getName());
            docEdit.setLastname(doctor.getLastname());
            docEdit.setSpeciality(doctor.getSpeciality());
            docEdit.setNip(doctor.getNip());

            return docEdit;
        }).orElseThrow(() -> new ResourceNotFoundException(String.format("Doctor with id: %s not found!", id)));
        session.disableFilter("deletedDoctorFilter");

        return editedDoctor;
    }

    @Transactional
    public Doctor editDoctorParts(long id, Doctor doctor) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedDoctorFilter");
        filter.setParameter("isDeleted", false);
        Doctor editedDoctor = doctorRepository.findById(id).map(docEdit -> {
            Optional.ofNullable(doctor.getName()).ifPresent(docEdit::setName);
            Optional.ofNullable(doctor.getLastname()).ifPresent(docEdit::setLastname);
            Optional.ofNullable(doctor.getSpeciality()).ifPresent(docEdit::setSpeciality);
            if (doctor.getNip().length() != 0) {
                Optional.of(doctor.getNip()).ifPresent(docEdit::setNip);
            }

            return docEdit;
        }).orElseThrow(() -> new ResourceNotFoundException(String.format("Doctor with id: %s not found!", id)));
        session.disableFilter("deletedDoctorFilter");

        return editedDoctor;
    }
}
