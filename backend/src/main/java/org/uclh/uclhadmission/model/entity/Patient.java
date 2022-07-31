package org.uclh.uclhadmission.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "Patient")
public class Patient {
    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    @Getter @Setter private Long id;

    @Column(name = "FULL_NAME", length = 50)
    @Getter @Setter
    private String fullName;

    @Column(name = "SEX_AT_BIRTH", length = 1)
    @Getter @Setter
    private String sexAtBirth;

    @Column(name = "ETHNICITY", length = 20)
    @Getter @Setter
    private String ethnicity;

    @Column(name = "YEAR_OF_BIRTH", length = 4)
    @Getter @Setter
    private String yearOfBirth;

    @Column(name = "MONTH_OF_BIRTH", length = 2)
    @Getter @Setter
    private String monthOfBirth;

    @Column(name = "DAY_OF_BIRTH", length = 2)
    @Getter @Setter
    private String dayOfBirth;

    @Column(name = "BIRTH_DATETIME")
    @Getter @Setter
    private Timestamp birthDateTime;

    @Column(name = "DEATH_DATETIME")
    @Getter @Setter
    private Timestamp deathDateTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient", cascade = CascadeType.ALL)
    @JsonIgnore
    @Getter @Setter
    private List<Admission> admissions;

    public Patient() { }
    public Patient(Long id, String fullName, String sexAtBirth, String ethnicity, String yearOfBirth, String monthOfBirth, String dayOfBirth, String birthDateTime, String deathDateTime) {
        this.id = id;
        this.fullName = fullName;
        this.sexAtBirth = sexAtBirth;
        this.ethnicity = ethnicity;
        this.yearOfBirth = yearOfBirth;
        this.monthOfBirth = monthOfBirth;
        this.dayOfBirth = dayOfBirth;
        this.birthDateTime = ObjectUtils.isEmpty(birthDateTime)? null : Timestamp.valueOf(birthDateTime);
        this.deathDateTime = ObjectUtils.isEmpty(deathDateTime)? null : Timestamp.valueOf(deathDateTime);
    }


}
