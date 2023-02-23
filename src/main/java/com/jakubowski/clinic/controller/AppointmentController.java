package com.jakubowski.clinic.controller;

import com.jakubowski.clinic.model.appointment.Appointment;
import com.jakubowski.clinic.model.appointment.command.CreateAppointmentCommand;
import com.jakubowski.clinic.model.appointment.dto.AppointmentDto;
import com.jakubowski.clinic.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<AppointmentDto> addAppointment(@RequestBody CreateAppointmentCommand command) {
        Appointment appointment = modelMapper.map(command, Appointment.class);
        Appointment addedAppointment = appointmentService.addAppointment(appointment);


        return new ResponseEntity<>(modelMapper.map(addedAppointment, AppointmentDto.class), HttpStatus.OK);
    }

    @PostMapping("/{id}/confirm")
    public ResponseEntity confirmAppointment(@PathVariable long id) {
        appointmentService.confirmAppointment(id);

        return new ResponseEntity<>("Sucesfully confirmed your appointment", HttpStatus.OK);
    }

    @PostMapping("/{id}/cancelled")
    public ResponseEntity cancellAppointment(@PathVariable long id) {
        appointmentService.cancelAppointment(id);

        return new ResponseEntity<>("Appointment sucesfully cancelled", HttpStatus.OK);
    }
}
