package com.jakubowski.clinic.model.patient;

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
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long id;
    private String name;
    private String lastname;
    @Column(unique = true)
    private String pesel;
    @Column(unique = true)
    private String email;

    private boolean deleted = false;
}
