package com.jakubowski.clinic.controller;

import com.jakubowski.clinic.model.patient.Patient;
import com.jakubowski.clinic.model.patient.command.CreatePatientCommand;
import com.jakubowski.clinic.model.patient.dto.PatientDto;
import com.jakubowski.clinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<PatientDto> savePatient(@RequestBody CreatePatientCommand command) {
        Patient patient = modelMapper.map(command, Patient.class);
        Patient createdPatient = patientService.savePatient(patient);

        return new ResponseEntity<>(modelMapper.map(createdPatient, PatientDto.class), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        List<Patient> patientsList = patientService.findAllPatients();
        List<PatientDto> patientsDtoList = patientsList.stream().
                map(patient -> modelMapper.map(patient, PatientDto.class)).collect(Collectors.toList());

        return new ResponseEntity<>(patientsDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> findPatient(@PathVariable long id) {
        Patient patient = patientService.findPatient(id);

        return new ResponseEntity<>(modelMapper.map(patient, PatientDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePatient(@PathVariable long id) {
        patientService.deletePatient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDto> editPatientAll(@PathVariable long id, @RequestBody CreatePatientCommand command) {
        Patient patient = modelMapper.map(command, Patient.class);
        Patient patientToEdit = patientService.editPatient(id, patient);

        return new ResponseEntity<>(modelMapper.map(patientToEdit, PatientDto.class), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PatientDto> editPatientParts(@PathVariable long id, @RequestBody CreatePatientCommand command) {
        Patient patient = modelMapper.map(command, Patient.class);
        Patient patientToEdit = patientService.editPatientPartial(id, patient);

        return new ResponseEntity<>(modelMapper.map(patientToEdit, PatientDto.class), HttpStatus.OK);
    }
}
