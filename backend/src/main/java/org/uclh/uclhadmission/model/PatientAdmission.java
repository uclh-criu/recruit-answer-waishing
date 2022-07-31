package org.uclh.uclhadmission.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Pojo to generate the PatientAdmission Json List
 */
public class PatientAdmission {
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Getter @Setter private LocalDateTime startDateTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Getter @Setter private LocalDateTime endDateTime;
    @Getter @Setter private String source;
    @Getter @Setter private String outcome;

    public PatientAdmission(LocalDateTime startDateTime, LocalDateTime endDateTime, String source, String outcome) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.source = source;
        this.outcome = outcome;
    }

    @Override
    public String toString() {
        return "PatientAdmissionDetail{" +
                "startDateTime='" + startDateTime + '\'' +
                ", endDateTime='" + endDateTime + '\'' +
                ", source='" + source + '\'' +
                ", outcome='" + outcome + '\'' +
                '}';
    }
}
