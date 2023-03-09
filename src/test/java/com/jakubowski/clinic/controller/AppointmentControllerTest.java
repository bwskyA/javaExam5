package com.jakubowski.clinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jakubowski.clinic.ClinicApplication;
import com.jakubowski.clinic.model.appointment.command.CreateAppointmentCommand;
import com.jakubowski.clinic.model.doctor.Doctor;
import com.jakubowski.clinic.model.patient.Patient;
import com.jakubowski.clinic.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = ClinicApplication.class)
@AutoConfigureMockMvc
class AppointmentControllerTest {

    private final MockMvc mockMvc;
    private final AppointmentService appointmentService;
    private final ObjectMapper objectMapper;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public AppointmentControllerTest(MockMvc mockMvc, AppointmentService appointmentService, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.appointmentService = appointmentService;
        this.objectMapper = objectMapper;
    }


    @Test
    void shouldSaveAppointment_thenReturn200() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        Doctor doctor = Doctor.builder()
                .id(1)
                .name("Michał")
                .lastname("Wisniewski")
                .speciality("dentist")
                .nip("1234567890")
                .build();
        Patient patient = Patient.builder()
                .id(1)
                .name("Adam")
                .lastname("Wisniewski")
                .email("m.wisniewski@gmail.com")
                .pesel("12345678901")
                .build();

        String createCommandJsonDoctor = objectMapper.writeValueAsString(doctor);
        String createCommandJsonPatient = objectMapper.writeValueAsString(patient);

        mockMvc.perform(post("/patient")
                        .contentType("application/json")
                        .content(createCommandJsonPatient))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastname").value("Wisniewski"));

        mockMvc.perform(post("/doctor")
                        .contentType("application/json")
                        .content(createCommandJsonDoctor))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastname").value("Wisniewski"));

        CreateAppointmentCommand appointmentCommand = CreateAppointmentCommand.builder()
                .doctorId(1)
                .patientId(1)
                .date(LocalDateTime.parse("2023-03-17 14:22:15", DATE_FORMATTER))
                .duration(2).build();

        String createCommandJson = objectMapper.writeValueAsString(appointmentCommand);

        mockMvc.perform(post("/appointment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createCommandJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.doctor.lastname").value(doctor.getLastname()))
                .andExpect(jsonPath("$.patient.lastname").value(patient.getLastname()))
                .andExpect(jsonPath("$.date").value(appointmentCommand.getDate().format(formatter)))
                .andExpect(jsonPath("$.duration").value(appointmentCommand.getDuration()));
    }

    @Test
    void shouldConfirmAppointment_andReturn200() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        Doctor doctor = Doctor.builder()
                .id(1)
                .name("Michał")
                .lastname("Wisniewski")
                .speciality("dentist")
                .nip("1234567890")
                .build();
        Patient patient = Patient.builder()
                .id(1)
                .name("Adam")
                .lastname("Wisniewski")
                .email("m.wisniewski@gmail.com")
                .pesel("12345678901")
                .build();

        String createCommandJsonDoctor = objectMapper.writeValueAsString(doctor);
        String createCommandJsonPatient = objectMapper.writeValueAsString(patient);

        mockMvc.perform(post("/patient")
                        .contentType("application/json")
                        .content(createCommandJsonPatient))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastname").value("Wisniewski"));

        mockMvc.perform(post("/doctor")
                        .contentType("application/json")
                        .content(createCommandJsonDoctor))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastname").value("Wisniewski"));

        CreateAppointmentCommand appointmentCommand = CreateAppointmentCommand.builder()
                .doctorId(1)
                .patientId(1)
                .date(LocalDateTime.parse("2023-03-17 14:22:15", DATE_FORMATTER))
                .duration(2).build();

        String createCommandJson = objectMapper.writeValueAsString(appointmentCommand);

        mockMvc.perform(post("/appointment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createCommandJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.doctor.lastname").value(doctor.getLastname()))
                .andExpect(jsonPath("$.patient.lastname").value(patient.getLastname()))
                .andExpect(jsonPath("$.date").value(appointmentCommand.getDate().format(formatter)))
                .andExpect(jsonPath("$.duration").value(appointmentCommand.getDuration()));

        mockMvc.perform(post("/appointment/1/confirm"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void shouldCancelAppointment_andReturn200() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        Doctor doctor = Doctor.builder()
                .id(1)
                .name("Michał")
                .lastname("Wisniewski")
                .speciality("dentist")
                .nip("1234567890")
                .build();
        Patient patient = Patient.builder()
                .id(1)
                .name("Adam")
                .lastname("Wisniewski")
                .email("m.wisniewski@gmail.com")
                .pesel("12345678901")
                .build();

        String createCommandJsonDoctor = objectMapper.writeValueAsString(doctor);
        String createCommandJsonPatient = objectMapper.writeValueAsString(patient);

        mockMvc.perform(post("/patient")
                        .contentType("application/json")
                        .content(createCommandJsonPatient))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastname").value("Wisniewski"));

        mockMvc.perform(post("/doctor")
                        .contentType("application/json")
                        .content(createCommandJsonDoctor))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastname").value("Wisniewski"));

        CreateAppointmentCommand appointmentCommand = CreateAppointmentCommand.builder()
                .doctorId(1)
                .patientId(1)
                .date(LocalDateTime.parse("2023-03-17 14:22:15", DATE_FORMATTER))
                .duration(2).build();

        String createCommandJson = objectMapper.writeValueAsString(appointmentCommand);

        mockMvc.perform(post("/appointment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createCommandJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.doctor.lastname").value(doctor.getLastname()))
                .andExpect(jsonPath("$.patient.lastname").value(patient.getLastname()))
                .andExpect(jsonPath("$.date").value(appointmentCommand.getDate().format(formatter)))
                .andExpect(jsonPath("$.duration").value(appointmentCommand.getDuration()));

        mockMvc.perform(post("/appointment/1/cancelled"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}