package com.jakubowski.clinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jakubowski.clinic.ClinicApplication;
import com.jakubowski.clinic.model.patient.Patient;
import com.jakubowski.clinic.model.patient.command.CreatePatientCommand;
import com.jakubowski.clinic.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ClinicApplication.class)
@AutoConfigureMockMvc
class PatientControllerTest {

    private final MockMvc mockMvc;
    private final PatientService patientService;

    private final ObjectMapper objectMapper;

    @Autowired
    public PatientControllerTest(MockMvc mockMvc, PatientService patientService, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.patientService = patientService;
        this.objectMapper = objectMapper;
    }

    @Test
    void whenValidData_thenReturn200() throws Exception {
        Patient patient = Patient.builder()
                .id(2)
                .name("Michal")
                .lastname("Wisniewski")
                .email("m.wisniewski@gmail.com")
                .pesel("12345678901")
                .build();

        String createCommandJson = objectMapper.writeValueAsString(patient);

        mockMvc.perform(get("/patient/2"))
                .andDo(print())
                .andExpect(status().isNotFound());

        mockMvc.perform(post("/patient")
                        .contentType("application/json")
                        .content(createCommandJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastname").value("Wisniewski"));
    }

    @Test
    void shouldSavePatient_thenReturn200() throws Exception {
        CreatePatientCommand patientCommand = CreatePatientCommand.builder()
                .name("Jan")
                .lastname("Wisniewski")
                .email("j.wisniewski@gmail.com")
                .pesel("12345678801")
                .build();

        String createCommandJson = objectMapper.writeValueAsString(patientCommand);

        mockMvc.perform(get("/patient/1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Patient with id: 1 not found!"))
                .andExpect(jsonPath("$.uri").value("/patient/1"))
                .andExpect(jsonPath("$.method").value("GET"));

        mockMvc.perform(post("/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createCommandJson))
                .andDo(print())
                .andExpect(jsonPath("$.name").value(patientCommand.getName()))
                .andExpect(jsonPath("$.lastname").value(patientCommand.getLastname()))
                .andExpect(jsonPath("$.email").value(patientCommand.getEmail()))
                .andExpect(jsonPath("$.pesel").value(patientCommand.getPesel()));

        mockMvc.perform(get("/patient/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(patientCommand.getName()))
                .andExpect(jsonPath("$.lastname").value(patientCommand.getLastname()))
                .andExpect(jsonPath("$.email").value(patientCommand.getEmail()))
                .andExpect(jsonPath("$.pesel").value(patientCommand.getPesel()));
    }

    @Test
    void whenValueNull_return400() throws Exception {
        Patient patient = null;

        mockMvc.perform(post("/patient")
                        .content(objectMapper.writeValueAsString(patient))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeletePatient_thenReturnNoContent() throws Exception {
        CreatePatientCommand createPatientCommand = CreatePatientCommand.builder()
                .name("Michał")
                .lastname("Kowalski")
                .email("m.kowalski23@gmail.com")
                .pesel("12345678901")
                .build();

        String createCommandJson = objectMapper.writeValueAsString(createPatientCommand);

        mockMvc.perform(post("/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createCommandJson))
                .andDo(print())
                .andExpect(jsonPath("$.name").value(createPatientCommand.getName()))
                .andExpect(jsonPath("$.lastname").value(createPatientCommand.getLastname()))
                .andExpect(jsonPath("$.email").value(createPatientCommand.getEmail()))
                .andExpect(jsonPath("$.pesel").value(createPatientCommand.getPesel()));

        mockMvc.perform(delete("/patient/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/patient/1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Patient with id: 1 not found!"))
                .andExpect(jsonPath("$.uri").value("/patient/1"))
                .andExpect(jsonPath("$.method").value("GET"));
    }

    @Test
    void shouldUpdatePatient_thenReturn200() throws Exception {
        CreatePatientCommand createPatientCommand = CreatePatientCommand.builder()
                .name("Jakub")
                .lastname("Misiakiewicz")
                .email("j.smieszny@o2.pl")
                .pesel("12344661103")
                .build();
        CreatePatientCommand createUpdatePatientCommand = CreatePatientCommand.builder()
                .name("Jan")
                .lastname("Fast")
                .email("j.fast@o2.pl")
                .pesel("22344661103")
                .build();

        String createCommandJson = objectMapper.writeValueAsString(createPatientCommand);
        String updatePatientValue = objectMapper.writeValueAsString(createUpdatePatientCommand);

        mockMvc.perform(post("/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createCommandJson))
                .andDo(print())
                .andExpect(jsonPath("$.name").value(createPatientCommand.getName()))
                .andExpect(jsonPath("$.lastname").value(createPatientCommand.getLastname()))
                .andExpect(jsonPath("$.email").value(createPatientCommand.getEmail()))
                .andExpect(jsonPath("$.pesel").value(createPatientCommand.getPesel()));

        mockMvc.perform(put("/patient/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatePatientValue))
                .andDo(print())
                .andExpect(jsonPath("$.name").value("Jan"))
                .andExpect(jsonPath("$.lastname").value("Fast"))
                .andExpect(jsonPath("$.email").value("j.fast@o2.pl"))
                .andExpect(jsonPath("$.pesel").value("22344661103"));
    }

    @Test
    void shouldUpdatePatientName_theReturn200() throws Exception {
        CreatePatientCommand createPatientCommand = CreatePatientCommand.builder()
                .name("Jakub")
                .lastname("Jankowski")
                .email("j.j@icloud.com")
                .pesel("12300061103")
                .build();
        CreatePatientCommand createUpdatePatientNamecommand = CreatePatientCommand.builder()
                .name("Jan")
                .build();

        String createCommandJson = objectMapper.writeValueAsString(createPatientCommand);
        String updatePatientValue = objectMapper.writeValueAsString(createUpdatePatientNamecommand);

        mockMvc.perform(post("/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createCommandJson))
                .andDo(print())
                .andExpect(jsonPath("$.name").value(createPatientCommand.getName()))
                .andExpect(jsonPath("$.lastname").value(createPatientCommand.getLastname()))
                .andExpect(jsonPath("$.email").value(createPatientCommand.getEmail()))
                .andExpect(jsonPath("$.pesel").value(createPatientCommand.getPesel()));

        mockMvc.perform(patch("/patient/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatePatientValue))
                .andDo(print())
                .andExpect(jsonPath("$.name").value("Jan"))
                .andExpect(jsonPath("$.lastname").value("Jankowski"))
                .andExpect(jsonPath("$.email").value("j.j@icloud.com"))
                .andExpect(jsonPath("$.pesel").value("12300061103"));
    }

}