package org.uclh.uclhadmission.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.uclh.uclhadmission.model.PatientAdmission;
import org.uclh.uclhadmission.model.PatientInfo;
import org.uclh.uclhadmission.model.entity.Admission;
import org.uclh.uclhadmission.model.entity.Patient;
import org.uclh.uclhadmission.repo.PatientRepository;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
public class AdmissionService {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdmissionService.class);

    @Autowired
    private PatientRepository patientRepository;

    /**
     * Fetch Patient Data from Datasource with overlapped(fake) filter
     * @param yearOfBirth {@link String}
     * @param sexAtBirth {@link String}
     * @param ethnicity {@link String}
     * @param isFake {@link Boolean} True - show any overlapped admission record ,
     *               {@link Boolean} False - without filter overlapped admission record(Full Report)
     * @return {@link List}<{@link PatientInfo}>
     */
    public List<PatientInfo> getAdmissionReport(String yearOfBirth,String sexAtBirth,String ethnicity,Boolean isFake){
        List<Patient> patients = patientRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(!ObjectUtils.isEmpty(yearOfBirth) && !"ALL".equalsIgnoreCase(yearOfBirth))
                predicates.add(criteriaBuilder.equal(root.get("yearOfBirth"), yearOfBirth));

            if(!ObjectUtils.isEmpty(sexAtBirth)  && !"ALL".equalsIgnoreCase(sexAtBirth))
                predicates.add(criteriaBuilder.equal(root.get("sexAtBirth"), sexAtBirth));

            if(!ObjectUtils.isEmpty(ethnicity) && !"ALL".equalsIgnoreCase(ethnicity))
                predicates.add(criteriaBuilder.equal(root.get("ethnicity"), ethnicity));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
       return getAdmissionListByPatientsAndisOverlapped(patients, isFake);
    }

    /**
     * Get Admission List By Patient List with overlapped filtering checking
     * @param patients {@link List}<{@link Patient}>
     * @param isOverlapped {@link Boolean} True - Trigger isOverlapped Logic filtering,
     *                     {@link Boolean} False- without isOverlapped filtering
     * @return {@link List}<{@link PatientInfo}>
     */
    public List<PatientInfo> getAdmissionListByPatientsAndisOverlapped(List<Patient> patients, boolean isOverlapped){
        List<PatientInfo> patientInfoList = ofNullable(patients).orElse(new ArrayList<>()).stream()
                .filter(isOverlapped ? p -> isAdmissionOverlapped(p.getAdmissions()): s -> true)
                .map(p1 -> new PatientInfo(
                        p1.getId(),
                        p1.getYearOfBirth(),
                        p1.getSexAtBirth(),
                        p1.getEthnicity(),
                        p1.getAdmissions().stream()
                                .map(a-> new PatientAdmission(
                                        a.getVisitStartDatetime().toLocalDateTime(),
                                        a.getVisitEndDatetime().toLocalDateTime(),
                                        a.getAdmissionSource(),
                                        a.getDischargeTo())).collect(Collectors.toList()))
                ).collect(Collectors.toList());
        LOGGER.info("patientAdmissionList: " + patientInfoList.size());
        return patientInfoList;
    }

    /**
     * Check provided Admission List has any overlapped data
     * @param admissionList {@link List}<{@link Admission} > admissionList
     * @return {@link Boolean} ture - Admission List contains overlap record
     *         {@link Boolean} false - Admission List without overlap record
     */
    public Boolean isAdmissionOverlapped(List<Admission> admissionList){
        Integer startDateOverlaps = admissionList.stream()
                .mapToInt(a -> overlap(a.getVisitStartDatetime().toLocalDateTime(), admissionList))
                .max().orElse(0);
        Integer endDateOverlaps = admissionList.stream()
                .mapToInt(a -> overlap(a.getVisitEndDatetime().toLocalDateTime(), admissionList))
                .max().orElse(0);
        return Integer.max(startDateOverlaps, endDateOverlaps) > 1;
    }

    /**
     * Check provided datetime have any overlapped data by datetime level
     * @param date {@link LocalDateTime} datetime to compare provided admissionList
     * @param admissionList {@link List}<{@link Admission} > admissionList
     * @return {@link Integer} overlap counter
     */
    public Integer overlap(LocalDateTime date, List<Admission> admissionList) {
        return admissionList.stream()
                .mapToInt(admission ->
                        (!(date.isBefore(admission.getVisitStartDatetime().toLocalDateTime()) || date.isAfter(admission.getVisitEndDatetime().toLocalDateTime()))
                        ) ? 1 : 0).sum();
    }

}
