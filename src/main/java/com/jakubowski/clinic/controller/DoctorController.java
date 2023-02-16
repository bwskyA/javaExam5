package com.jakubowski.clinic.controller;

import com.jakubowski.clinic.model.doctor.Doctor;
import com.jakubowski.clinic.model.doctor.command.CreateDoctorCommand;
import com.jakubowski.clinic.model.doctor.dto.DoctorDto;
import com.jakubowski.clinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<DoctorDto> saveDoctor(@RequestBody CreateDoctorCommand command) {
        Doctor doctor = modelMapper.map(command, Doctor.class);
        Doctor createdDoctor = doctorService.saveDoctor(doctor);

        return new ResponseEntity<>(modelMapper.map(createdDoctor, DoctorDto.class), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DoctorDto>> findAllDoctors() {
        List<Doctor> doctorsList = doctorService.findAllDoctors();
        List<DoctorDto> doctorDtosList = doctorsList.stream().map(doctor ->
                modelMapper.map(doctor, DoctorDto.class)).collect(Collectors.toList());

        return new ResponseEntity<>(doctorDtosList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> findDoctor(@PathVariable long id) {
        Doctor doctor = doctorService.findDoctor(id);

        return new ResponseEntity<>(modelMapper.map(doctor, DoctorDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDoctor(@PathVariable long id) {
        doctorService.deleteDoctor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDto> editDoctorAll(@PathVariable long id, @RequestBody Doctor doctor) {
        Doctor doctorToEdit = doctorService.editDoctorFull(id, doctor);

        return new ResponseEntity<>(modelMapper.map(doctorToEdit, DoctorDto.class), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DoctorDto> editDoctorParts(@PathVariable long id, @RequestBody Doctor doctor) {
        Doctor doctorToEdit = doctorService.editDoctorParts(id, doctor);

        return new ResponseEntity<>(modelMapper.map(doctorToEdit, DoctorDto.class), HttpStatus.OK);
    }
}
