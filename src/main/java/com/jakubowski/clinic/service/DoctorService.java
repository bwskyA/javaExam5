package com.jakubowski.clinic.service;

import com.jakubowski.clinic.model.doctor.Doctor;
import com.jakubowski.clinic.repository.DoctorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor findDoctor(long id) {
        return doctorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Doctor with id: %s not found!", id)));
    }

    public void deleteDoctor(long id){
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Doctor with id: %s not found!", id)));
        doctor.setDeleted(true);
    }

    @Transactional
    public Doctor editDoctorFull(long id, Doctor doctor) {
        return doctorRepository.findById(id).map(docEdit -> {
            docEdit.setName(doctor.getName());
            docEdit.setLastname(doctor.getLastname());
            docEdit.setSpeciality(doctor.getSpeciality());
            docEdit.setNip(doctor.getNip());

            return docEdit;
        }).orElseThrow(() -> new ResourceNotFoundException(String.format("Doctor with id: %s not found!", id)));
    }

    @Transactional
    public Doctor editDoctorParts(long id, Doctor doctor) {
        return doctorRepository.findById(id).map(docEdit -> {
            Optional.ofNullable(doctor.getName()).ifPresent(docEdit::setName);
            Optional.ofNullable(doctor.getLastname()).ifPresent(docEdit::setLastname);
            Optional.ofNullable(doctor.getSpeciality()).ifPresent(docEdit::setSpeciality);
            if(doctor.getNip() != 0){
                Optional.of(doctor.getNip()).ifPresent(docEdit::setNip);
            }

            return docEdit;
        }).orElseThrow(() -> new ResourceNotFoundException(String.format("Doctor with id: %s not found!", id)));
    }
}
