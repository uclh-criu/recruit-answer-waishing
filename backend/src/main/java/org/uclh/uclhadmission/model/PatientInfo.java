package org.uclh.uclhadmission.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Pojo to generate the PatientInfo Json List
 */
public class PatientInfo{
    @Getter @Setter private Long patientId;
    @Getter @Setter private String yearOfBirth;
    @Getter @Setter private String sexAtBirth;
    @Getter @Setter private String ethnicity;
    @Getter @Setter private List<PatientAdmission> admissions;

    public PatientInfo(Long patientId, String yearOfBirth, String sexAtBirth, String ethnicity, List<PatientAdmission> admissions) {
        this.patientId = patientId;
        this.yearOfBirth = yearOfBirth;
        this.sexAtBirth = sexAtBirth;
        this.ethnicity = ethnicity;
        this.admissions = admissions;
    }

    @Override
    public String toString() {
        return "PatientAdmission{" +
                "patientId=" + patientId +
                ", yearOfBirth='" + yearOfBirth + '\'' +
                ", sexAtBirth='" + sexAtBirth + '\'' +
                ", ethnicity='" + ethnicity + '\'' +
                ", admissions=" + admissions +
                '}';
    }
}
