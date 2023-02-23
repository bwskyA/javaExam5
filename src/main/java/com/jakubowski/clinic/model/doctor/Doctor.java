package com.jakubowski.clinic.model.doctor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@SQLDelete(sql = "UPDATE doctor SET deleted = true WHERE id=?")
@FilterDef(name = "deletedDoctorFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedDoctorFilter", condition = "deleted = :isDeleted")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long id;
    @NotEmpty(message = "NAME_NOT_EMPTY")
    private String name;
    @NotEmpty(message = "LAST_NAME_NOT_EMPTY")
    private String lastname;
    @NotEmpty(message = "SPECIALITY_NOT_EMPTY")
    private String speciality;
    @Column(unique = true)
    @Size(min = 10, max = 10)
    @Positive
    @NotEmpty(message = "NIP_NOT_EMPTY")
    private String nip;
    private boolean deleted = Boolean.FALSE;
}
