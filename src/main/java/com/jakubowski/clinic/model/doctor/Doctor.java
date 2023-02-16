package com.jakubowski.clinic.model.doctor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long id;
    private String name;
    private String lastname;
    private String speciality;
    @Column(unique = true)
    private long nip;
    private boolean deleted = false;
}
