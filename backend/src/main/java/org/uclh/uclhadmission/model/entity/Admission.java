package org.uclh.uclhadmission.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "ADMISSION")
public class Admission {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    @Getter @Setter  private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PATIENT_ID",nullable = false)
    @JsonIgnore
    @Getter @Setter private Patient patient; // Foreign Key

    @Column(name = "VISIT_START_DATE")
    @Getter @Setter
    private Date visitStartDate;

    @Column(name = "VISIT_START_DATETIME")
    @Getter @Setter
    private Timestamp visitStartDatetime;

    @Column(name = "VISIT_END_DATE")
    @Getter @Setter
    private Date visitEndDate;

    @Column(name = "DEATH_DATETIME")
    @Getter @Setter
    private Timestamp visitEndDatetime;

    @Column(name = "ADMISSION_SOURCE", length = 50)
    @Getter @Setter
    private String admissionSource;

    @Column(name = "DISCHARGE_TO", length = 50)
    @Getter @Setter
    private String dischargeTo;

    public Admission(){}

    public Admission(Long id, Patient patient, String visitStartDate, String visitStartDatetime, String visitEndDate, String visitEndDatetime, String admissionSource, String dischargeTo) {
        this.id = id;
        this.patient = patient;
        this.visitStartDate = Date.valueOf(visitStartDate);
        this.visitStartDatetime = Timestamp.valueOf(visitStartDatetime);
        this.visitEndDate = Date.valueOf(visitEndDate);
        this.visitEndDatetime = Timestamp.valueOf(visitEndDatetime);
        this.admissionSource = admissionSource;
        this.dischargeTo = dischargeTo;
    }

    @Override
    public String toString() {
        return "Admission{" +
                "id=" + id +
                ", patient=" + patient +
                ", visitStartDate=" + visitStartDate +
                ", visitStartDatetime=" + visitStartDatetime +
                ", visitEndDate=" + visitEndDate +
                ", visitEndDatetime=" + visitEndDatetime +
                ", admissionSource='" + admissionSource + '\'' +
                ", dischargeTo='" + dischargeTo + '\'' +
                '}';
    }
}
