package com.jakubowski.clinic.model.patient;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@SQLDelete(sql = "UPDATE patient SET deleted = true WHERE id=?")
@FilterDef(name = "deletedPatientFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedPatientFilter", condition = "deleted = :isDeleted")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long id;
    @NotEmpty(message = "NAME_NOT_EMPTY")
    private String name;
    @NotEmpty(message = "LAST_NAME_NOT_EMPTY")
    private String lastname;
    @Column(unique = true)
    @Size(min = 11, max = 11)
    private String pesel;
    @Column(unique = true)
    private String email;

    private boolean deleted = Boolean.FALSE;
}
