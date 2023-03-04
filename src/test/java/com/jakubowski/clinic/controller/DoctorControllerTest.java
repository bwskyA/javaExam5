package com.jakubowski.clinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jakubowski.clinic.ClinicApplication;
import com.jakubowski.clinic.model.doctor.Doctor;
import com.jakubowski.clinic.model.doctor.command.CreateDoctorCommand;
import com.jakubowski.clinic.service.DoctorService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ClinicApplication.class)
@AutoConfigureMockMvc
class DoctorControllerTest {

    private final MockMvc mockMvc;
    private final DoctorService doctorService;
    private final ObjectMapper objectMapper;

    @Autowired
    public DoctorControllerTest(MockMvc mockMvc, DoctorService doctorService, ObjectMapper objectMapper){
        this.mockMvc = mockMvc;
        this.doctorService = doctorService;
        this.objectMapper = objectMapper;
    }

    @Test
    void whenValidData_thenReturn200() throws Exception {
        Doctor doctor = Doctor.builder()
                .id(2)
                .name("Adam")
                .lastname("Kowalski")
                .speciality("dentist")
                .nip("1111111111").build();

        String createCommandJson = objectMapper.writeValueAsString(doctor);

        mockMvc.perform(get("/doctor/2"))
                .andDo(print())
                .andExpect(status().isNotFound());

        mockMvc.perform(post("/doctor")
                .contentType("application/json")
                .content(createCommandJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastname").value("Kowalski"));
    }

    @Test
    void shouldSaveDoctor_thenReturn200() throws Exception {
        CreateDoctorCommand createDoctorCommand = CreateDoctorCommand.builder()
                .name("Michał")
                .lastname("Wisniewski")
                .speciality("dentist")
                .nip("1234567890")
                .build();

        String createCommandJson = objectMapper.writeValueAsString(createDoctorCommand);

        mockMvc.perform(get("/doctor/1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Doctor with id: 1 not found!"))
                .andExpect(jsonPath("$.uri").value("/doctor/1"))
                .andExpect(jsonPath("$.method").value("GET"));

        mockMvc.perform(post("/doctor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createCommandJson))
                .andDo(print())
                .andExpect(jsonPath("$.name").value(createDoctorCommand.getName()))
                .andExpect(jsonPath("$.lastname").value(createDoctorCommand.getLastname()))
                .andExpect(jsonPath("$.speciality").value(createDoctorCommand.getSpeciality()))
                .andExpect(jsonPath("$.nip").value(createDoctorCommand.getNip()));

        mockMvc.perform(get("/doctor/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(createDoctorCommand.getName()))
                .andExpect(jsonPath("$.lastname").value(createDoctorCommand.getLastname()))
                .andExpect(jsonPath("$.speciality").value(createDoctorCommand.getSpeciality()))
                .andExpect(jsonPath("$.nip").value(createDoctorCommand.getNip()));
    }

    @Test
    void whenValueNull_return400() throws Exception {
        Doctor doctor = null;

        mockMvc.perform(post("/doctor")
                        .content(objectMapper.writeValueAsString(doctor))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteDoctor_thenReturnNoContent() throws Exception {
        CreateDoctorCommand createDoctorCommand = CreateDoctorCommand.builder()
                .name("Michał")
                .lastname("Kowalski")
                .speciality("dentist")
                .nip("1234567871")
                .build();
        String createCommandJson = objectMapper.writeValueAsString(createDoctorCommand);

        mockMvc.perform(post("/doctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createCommandJson))
                .andDo(print())
                .andExpect(jsonPath("$.name").value(createDoctorCommand.getName()))
                .andExpect(jsonPath("$.lastname").value(createDoctorCommand.getLastname()))
                .andExpect(jsonPath("$.speciality").value(createDoctorCommand.getSpeciality()))
                .andExpect(jsonPath("$.nip").value(createDoctorCommand.getNip()));

        mockMvc.perform(delete("/doctor/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/doctor/1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Doctor with id: 1 not found!"))
                .andExpect(jsonPath("$.uri").value("/doctor/1"))
                .andExpect(jsonPath("$.method").value("GET"));
    }

    @Test
    void shouldUpdateDoctor_thenReturn200() throws Exception {
        CreateDoctorCommand createDoctorCommand = CreateDoctorCommand.builder()
                .name("Michał")
                .lastname("Kowalski")
                .speciality("dentist")
                .nip("1234560891")
                .build();
        CreateDoctorCommand createUpdateDoctorcommand = CreateDoctorCommand.builder()
                .name("Jan")
                .lastname("Fast")
                .speciality("nurse")
                .nip("1111101111")
                .build();

        String createCommandJson = objectMapper.writeValueAsString(createDoctorCommand);
        String updateDoctorValue = objectMapper.writeValueAsString(createUpdateDoctorcommand);

        mockMvc.perform(post("/doctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createCommandJson))
                .andDo(print())
                .andExpect(jsonPath("$.name").value(createDoctorCommand.getName()))
                .andExpect(jsonPath("$.lastname").value(createDoctorCommand.getLastname()))
                .andExpect(jsonPath("$.speciality").value(createDoctorCommand.getSpeciality()))
                .andExpect(jsonPath("$.nip").value(createDoctorCommand.getNip()));

        mockMvc.perform(put("/doctor/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateDoctorValue))
                .andDo(print())
                .andExpect(jsonPath("$.name").value("Jan"))
                .andExpect(jsonPath("$.lastname").value("Fast"))
                .andExpect(jsonPath("$.speciality").value("nurse"))
                .andExpect(jsonPath("$.nip").value("1111101111"));
    }

    @Test
    void shouldUpdateDoctorName_theReturn200() throws Exception {
        CreateDoctorCommand createDoctorCommand = CreateDoctorCommand.builder()
                .name("Kuba")
                .lastname("Kowalski")
                .speciality("dentist")
                .nip("1234500891")
                .build();
        CreateDoctorCommand createUpdateDoctorNamecommand = CreateDoctorCommand.builder()
                .name("Jan")
                .build();

        String createCommandJson = objectMapper.writeValueAsString(createDoctorCommand);
        String updateDoctorValue = objectMapper.writeValueAsString(createUpdateDoctorNamecommand);

        mockMvc.perform(post("/doctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createCommandJson))
                .andDo(print())
                .andExpect(jsonPath("$.name").value(createDoctorCommand.getName()))
                .andExpect(jsonPath("$.lastname").value(createDoctorCommand.getLastname()))
                .andExpect(jsonPath("$.speciality").value(createDoctorCommand.getSpeciality()))
                .andExpect(jsonPath("$.nip").value(createDoctorCommand.getNip()));

        mockMvc.perform(patch("/doctor/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateDoctorValue))
                .andDo(print())
                .andExpect(jsonPath("$.name").value("Jan"))
                .andExpect(jsonPath("$.lastname").value("Kowalski"))
                .andExpect(jsonPath("$.speciality").value("dentist"))
                .andExpect(jsonPath("$.nip").value("1234500891"));
    }

}