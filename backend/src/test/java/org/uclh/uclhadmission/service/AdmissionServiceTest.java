package org.uclh.uclhadmission.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.jpa.domain.Specification;
import org.uclh.uclhadmission.model.PatientInfo;
import org.uclh.uclhadmission.model.entity.Admission;
import org.uclh.uclhadmission.model.entity.Patient;
import org.uclh.uclhadmission.repo.PatientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@RunWith(MockitoJUnitRunner.class)
public class AdmissionServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private AdmissionService admissionService;

    private List<Patient> patientList = new ArrayList<>();
    private List<Admission> admissionList = new ArrayList<>();

    @Before
    public void init() {
        openMocks(this);

        patientList.add(new Patient(1L, "Rufus Hirst", "M", "Black African", "1990", "12", "10", "1990-12-10 10:20:00", null));
        patientList.add(new Patient(2L, "Allana Farmer", "F", "Caribbean", "1958", "5", "10", "1958-05-10 08:10:00", null));
        patientList.add(new Patient(3L, "Paloma Barrow", "F", "White British", "1972", "6", "11", "1972-06-11 23:16:00", null));
        patientList.add(new Patient(4L, "Esmay Cross", "M", "White British", "1990", "2", "25", "1990-02-25 15:50:00", "2020-11-26 13:09:00"));
        patientList.add(new Patient(5L, "Wolf Yoder", "M", "Other Mixed", "1940", "8", "28", "1940-08-28 00:00:00", "2000-03-18 20:21:00"));
        patientList.add(new Patient(6L, "Rosa Eddison", "F", "Asian Mixed", "2003", "11", "30", "2003-11-30 20:18:00", null));
        patientList.add(new Patient(7L, "Bruno Wallis", null, "Asian Mixed", "2003", "10", "31", "2003-10-31 08:45:00", null));
        patientList.add(new Patient(8L, "Roberto Stokes", null, "Caribbean", "2019", "12", "1", "2019-12-01 17:30:00", null));
        patientList.add(new Patient(9L, "Joao Soto", "M", "Black African", "1996", "3", "21", "1996-03-21 12:30:00", null));
        patientList.add(new Patient(10L, "Jodi Serrano", "M", "Other White", "1996", "4", "4", "1996-04-04 13:00:00", null));
        patientList.add(new Patient(11L, "Fatima Dickinson", "F", null, "2000", "7", "16", "2000-07-16 05:00:00", "2001-12-16 05:30:00"));
        patientList.add(new Patient(12L, null, "F", "Chinese", "1980", "8", "16", "1980-08-16 08:00:00", null));
        patientList.add(new Patient(13L, "Cerys Ibarra", "F", "White British", "1985", "9", "13", "1985-09-13 10:05:00", null));
        patientList.add(new Patient(14L, "Marcus Millar", null, "Other Mixed", "1966", "10", "18", "1966-10-18 19:08:00", null));
        patientList.add(new Patient(15L, "Li Qiang-Wu", "M", "Chinese", "1935", "6", "15", "1935-06-15 17:30:00", null));
        patientList.add(new Patient(16L, "Percy Stone", "M", "Black African", "1942", "6", "7", "1942-06-07 21:45:00", null));
        patientList.add(new Patient(17L, "Jenny Kay-Wang", "F", "Chinese", "1981", "3", "15", "1981-03-15 21:46:00", "2018-08-11 09:30:00"));

        //extra case
        patientList.add(new Patient(18L, "Law", "M", "Chinese", "1981", "3", "15", "1981-03-15 21:46:00", "2018-08-11 09:30:00"));

        admissionList.add(new Admission(1L, getPatient(1L), "1990-12-10", "1990-12-10 11:20:00", "1990-12-11", "1990-12-11 05:30:00", "Inpatient", "Discharged Alive"));
        admissionList.add(new Admission(2L, getPatient(2L), "1990-12-10", "1990-12-10 10:20:00", "1990-12-11", "1990-12-11 06:45:00", "Inpatient", "Unknown"));
        admissionList.add(new Admission(3L, getPatient(2L), "1991-05-06", "1991-05-06 15:08:00", "1991-05-08", "1991-05-08 10:18:00", "Outpatient", "Discharged Alive"));
        admissionList.add(new Admission(4L, getPatient(3L), "2000-02-10", "2000-02-10 05:20:00", "2000-02-15", "2000-02-15 12:05:00", "Outpatient", "Unknown"));
        admissionList.add(new Admission(5L, getPatient(3L), "1991-05-06", "1991-05-06 15:08:00", "1991-05-08", "1991-05-08 19:15:00", "Outpatient", "Discharged Alive"));
        admissionList.add(new Admission(6L, getPatient(3L), "2003-01-15", "2003-01-15 20:18:00", "2003-01-20", "2003-01-20 23:02:00", "Psychiatric", "Inpatient"));
        admissionList.add(new Admission(7L, getPatient(4L), "2003-12-01", "2003-12-01 08:45:00", "2003-12-02", "2003-12-02 16:28:00", "Inpatient", "Inpatient"));
        admissionList.add(new Admission(8L, getPatient(4L), "2019-12-01", "2019-12-01 17:30:00", "2019-12-03", "2019-12-03 17:45:00", "Inpatient", "Unknown"));
        admissionList.add(new Admission(9L, getPatient(4L), "2020-11-21", "2020-11-21 12:30:00", "2020-11-26", "2020-11-26 13:09:00", "Inpatient", "Hospice"));
        admissionList.add(new Admission(10L, getPatient(5L), "2000-03-16", "2000-03-16 05:00:00", "2000-03-17", "2000-03-17 21:20:00", "Outpatient", "Discharged Alive"));
        admissionList.add(new Admission(11L, getPatient(5L), "2000-03-18", "2000-03-18 08:00:00", "2000-03-18", "2000-03-18 20:21:00", "Psychiatric", "Hospice"));
        admissionList.add(new Admission(12L, getPatient(6L), "2020-12-01", "2020-12-01 13:00:00", "2021-01-01", "2021-01-01 22:15:00", "Outpatient", "Discharged Alive"));
        admissionList.add(new Admission(13L, getPatient(6L), "2021-12-01", "2021-12-01 13:00:00", "2022-01-03", "2021-01-03 22:15:00", null, null));
        admissionList.add(new Admission(14L, getPatient(7L), "2001-03-16", "2001-03-16 10:00:00", "2001-03-21", "2001-03-21 00:01:00", "Outpatient", "Discharged Alive"));
        admissionList.add(new Admission(15L, getPatient(7L), "2010-03-22", "2016-03-22 15:00:00", "2010-04-16", "2010-04-16 13:13:00", "Outpatient", null));
        admissionList.add(new Admission(16L, getPatient(8L), "2020-03-16", "2020-03-16 17:00:00", "2020-05-16", "2020-05-16 23:00:00", "Inpatient", "Discharged Alive"));
        admissionList.add(new Admission(17L, getPatient(9L), "2015-03-16", "2015-03-16 17:00:00", "2015-05-16", "2015-05-16 23:00:00", "Psychiatric", "Psychiatric"));
        admissionList.add(new Admission(18L, getPatient(9L), "2016-03-16", "2016-03-16 17:00:00", "2016-05-16", "2016-05-16 23:00:00", "Outpatient", "Discharged Alive"));
        admissionList.add(new Admission(19L, getPatient(10L), "2015-03-16", "2015-03-16 17:00:00", "2015-05-16", "2015-05-16 23:00:00", "Inpatient", "ICU"));
        admissionList.add(new Admission(20L, getPatient(10L), "2015-03-25", "2015-03-25 17:00:00", "2015-05-25", "2015-05-25 23:00:00", null, "Discharged Alive"));
        admissionList.add(new Admission(21L, getPatient(11L), "2000-12-10", "2000-12-10 10:20:00", "2000-12-11", "2000-12-11 05:30:00", "Outpatient", "ICU"));
        admissionList.add(new Admission(22L, getPatient(11L), "2001-12-10", "2001-12-10 10:20:00", "2001-12-11", "2001-12-11 05:30:00", "Inpatient", "Discharged Alive"));
        admissionList.add(new Admission(23L, getPatient(11L), "2001-12-15", "2001-12-15 10:20:00", "2001-12-16", "2001-12-16 05:30:00", null, "Hospice"));
        admissionList.add(new Admission(24L, getPatient(12L), "1990-12-10", "1990-12-10 10:20:00", "1990-12-11", "1990-12-11 05:30:00", "Outpatient", "Discharged Alive"));
        admissionList.add(new Admission(25L, getPatient(12L), "1990-12-12", "1990-12-12 10:20:00", "1990-12-13", "1990-12-13 05:30:00", "ICU", "Discharged Alive"));
        admissionList.add(new Admission(26L, getPatient(13L), "1990-03-08", "1990-03-08 15:00:00", "1990-03-11", "1990-12-11 14:22:00", "Outpatient", null));
        admissionList.add(new Admission(27L, getPatient(14L), "2005-09-10", "2005-09-10 10:20:00", "2005-09-11", "2005-09-11 05:30:00", "Inpatient", "Unknown"));
        admissionList.add(new Admission(28L, getPatient(14L), "2014-06-02", "2014-06-02 10:20:00", "2014-06-05", "2014-06-05 05:30:00", "Psychiatric", "Inpatient"));
        admissionList.add(new Admission(29L, getPatient(15L), "1990-12-10", "1990-12-10 10:20:00", "1990-12-11", "1990-12-11 05:30:00", "Outpatient", "ICU"));
        admissionList.add(new Admission(30L, getPatient(15L), "2022-03-12", "2022-03-12 10:20:00", "2022-03-12", "2022-03-12 15:30:00", "Inpatient", "Psychiatric"));
        admissionList.add(new Admission(31L, getPatient(15L), "2022-06-10", "2022-06-10 10:20:00", "2022-06-12", "2022-06-12 05:30:00", "Outpatient", "Discharged Alive"));
        admissionList.add(new Admission(32L, getPatient(16L), "2016-12-10", "2016-12-10 10:20:00", "2016-12-11", "2016-12-11 05:30:00", "Outpatient", null));
        admissionList.add(new Admission(33L, getPatient(16L), "2017-12-10", "2017-12-10 10:20:00", "2017-12-11", "2017-12-11 05:30:00", "ICU", "Discharged Alive"));
        admissionList.add(new Admission(34L, getPatient(16L), "2018-12-10", "2018-12-10 10:20:00", "2018-12-11", "2018-12-11 05:30:00", "Inpatient", "Unknown"));
        admissionList.add(new Admission(35L, getPatient(16L), "2019-12-10", "2019-12-10 10:20:00", "2019-12-11", "2019-12-11 05:30:00", "Outpatient", "Discharged Alive"));
        admissionList.add(new Admission(36L, getPatient(17L), "2008-07-29", "2008-07-29 22:20:00", "2008-07-29", "2008-07-29 23:30:00", "Outpatient", "Discharged Alive"));
        admissionList.add(new Admission(37L, getPatient(17L), "2018-08-10", "2018-08-10 20:09:00", "2018-08-11", "2018-08-11 09:30:00", "ICU", "Hospice"));

        patientList.forEach(p -> p.setAdmissions(getAdmissionByPatientId(p.getId())));
    }

    private Patient getPatient(Long id) {
        return Optional.of(patientList.stream().filter(p -> p.getId().equals(id)).findAny()).get().orElse(null);
    }

    private List<Admission> getAdmissionByPatientId(Long id) {
        return admissionList.stream().filter(a -> a.getPatient().getId().equals(id)).collect(Collectors.toList());
    }

    @Test
    public void getFullAdmissionList() {
        when(patientRepository.findAll(any(Specification.class))).thenReturn(patientList);
        List<PatientInfo> resultList = admissionService.getAdmissionReport("ALL", "ALL", "ALL", Boolean.FALSE);
        assertThat(resultList).hasSize(18);
    }

    @Test
    public void getFakeAdmissionList() {
        when(patientRepository.findAll(any(Specification.class))).thenReturn(patientList);
        List<PatientInfo> resultList = admissionService.getAdmissionReport("ALL", "ALL", "ALL", Boolean.TRUE);
        assertThat(resultList).hasSize(1);
        assertEquals(1L, resultList.stream().filter(p -> p.getPatientId() == 10L).count());
    }

    @Test
    /*
     * No Duplicate Admission data
     * datetime1:         |------|
     * datetime2:                     |------|
     */
    public void isAdmissionOverlapped_noOverlap_case1() {
        admissionList.add(new Admission(40L, getPatient(18L), "2001-08-10", "2001-08-10 20:09:00", "2001-08-11", "2001-08-11 09:30:00", "ICU", "Hospice"));
        admissionList.add(new Admission(41L, getPatient(18L), "2002-08-10", "2002-08-10 20:09:00", "2002-08-11", "2002-08-11 09:30:00", "ICU", "Hospice"));
        patientList.forEach(p -> p.setAdmissions(getAdmissionByPatientId(p.getId())));


        assertEquals(false, admissionService.isAdmissionOverlapped(getAdmissionByPatientId(18L)));
    }

    @Test
    /*
     * No Duplicate Admission data
     * datetime1:         |------|
     * datetime2:                     |------|
     * datetime3:                                 |------|
     */
    public void isAdmissionOverlapped_noOverlap_case2() {
        admissionList.add(new Admission(40L, getPatient(18L), "2001-08-10", "2001-08-10 20:09:00", "2001-08-11", "2001-08-11 09:30:00", "ICU", "Hospice"));
        admissionList.add(new Admission(41L, getPatient(18L), "2002-08-10", "2002-08-10 20:09:00", "2002-08-11", "2002-08-11 09:30:00", "ICU", "Hospice"));
        admissionList.add(new Admission(42L, getPatient(18L), "2003-08-10", "2003-08-10 20:09:00", "2003-08-11", "2003-08-11 09:30:00", "ICU", "Hospice"));

        patientList.forEach(p -> p.setAdmissions(getAdmissionByPatientId(p.getId())));

        //when(admissionRepository.findAll()).thenReturn(admissionList);

        assertEquals(false, admissionService.isAdmissionOverlapped(getAdmissionByPatientId(18L)));
    }

    @Test
    /*
     * Duplicate Admission data
     * datetime1:         |------|
     * datetime2:         |------|
     */
    public void isAdmissionOverlapped_duplicate_case1() {
        admissionList.add(new Admission(38L, getPatient(18L), "2018-08-10", "2018-08-10 20:09:00", "2018-08-11", "2018-08-11 09:30:00", "ICU", "Hospice"));
        admissionList.add(new Admission(39L, getPatient(18L), "2018-08-10", "2018-08-10 20:09:00", "2018-08-11", "2018-08-11 09:30:00", "ICU", "Hospice"));
        patientList.forEach(p -> p.setAdmissions(getAdmissionByPatientId(p.getId())));

        //  when(admissionRepository.findAll()).thenReturn(admissionList);

        assertEquals(true, admissionService.isAdmissionOverlapped(getAdmissionByPatientId(18L)));
    }

    @Test
    /*
     * 2 Duplicate Admission data with one no overlap admission data
     * datetime1:         |------|
     * datetime2:                     |------|
     * datetime3:         |------|
     */
    public void isAdmissionOverlapped_duplicate_case2() {
        patientList.add(new Patient(18L, "Law", "M", "Chinese", "1981", "3", "15", "1981-03-15 21:46:00", "2018-08-11 09:30:00"));
        admissionList.add(new Admission(40L, getPatient(18L), "2018-08-10", "2018-08-10 20:09:00", "2018-08-11", "2018-08-11 09:30:00", "ICU", "Hospice"));
        admissionList.add(new Admission(41L, getPatient(18L), "2001-08-10", "2001-08-10 20:09:00", "2001-08-11", "2001-08-11 09:30:00", "ICU", "Hospice"));
        admissionList.add(new Admission(42L, getPatient(18L), "2018-08-10", "2018-08-10 20:09:00", "2018-08-11", "2018-08-11 09:30:00", "ICU", "Hospice"));
        patientList.forEach(p -> p.setAdmissions(getAdmissionByPatientId(p.getId())));


        assertEquals(true, admissionService.isAdmissionOverlapped(getAdmissionByPatientId(18L)));
    }

    @Test
    /*
     * 2 Duplicate Admission data with one overlap admission data
     */
    public void isAdmissionOverlapped_duplicate_case3() {
        patientList.add(new Patient(18L, "Law", "M", "Chinese", "1981", "3", "15", "1981-03-15 21:46:00", "2018-08-11 09:30:00"));
        admissionList.add(new Admission(40L, getPatient(18L), "2018-08-10", "2018-08-10 20:09:00", "2018-08-11", "2018-08-11 09:30:00", "ICU", "Hospice"));
        admissionList.add(new Admission(41L, getPatient(18L), "2018-08-15", "2018-08-15 20:09:00", "2018-08-16", "2018-08-16 09:30:00", "ICU", "Hospice"));
        admissionList.add(new Admission(42L, getPatient(18L), "2018-08-10", "2018-08-10 20:09:00", "2018-08-11", "2018-08-11 09:30:00", "ICU", "Hospice"));
        patientList.forEach(p -> p.setAdmissions(getAdmissionByPatientId(p.getId())));

        assertEquals(true, admissionService.isAdmissionOverlapped(getAdmissionByPatientId(18L)));
    }

    @Test
    /*
     * 2 Admission data and 1 admission data is within another time period
     * datetime1:         |-------------|
     * datetime2:            |------|
     */
    public void isAdmissionOverlapped_overlap_inperiod_case1() {
        patientList.add(new Patient(18L, "Law", "M", "Chinese", "1981", "3", "15", "1981-03-15 21:46:00", "2018-08-11 09:30:00"));
        admissionList.add(new Admission(43L, getPatient(18L), "2018-08-01", "2018-08-01 20:09:00", "2018-09-01", "2018-09-01 09:30:00", "ICU", "Hospice"));
        admissionList.add(new Admission(44L, getPatient(18L), "2018-08-15", "2018-08-15 20:09:00", "2018-08-20", "2018-08-20 09:30:00", "ICU", "Hospice"));
        patientList.forEach(p -> p.setAdmissions(getAdmissionByPatientId(p.getId())));

        assertEquals(true, admissionService.isAdmissionOverlapped(getAdmissionByPatientId(18L)));
    }

    @Test
    /*
     * 2 Admission data and 1 admission data is within another time period
     * datetime1:            |------|
     * datetime2:         |-------------|
     */
    public void isAdmissionOverlapped_overlap_inperiod_swap_case2() {
        patientList.add(new Patient(18L, "Law", "M", "Chinese", "1981", "3", "15", "1981-03-15 21:46:00", "2018-08-11 09:30:00"));
        admissionList.add(new Admission(45L, getPatient(18L), "2018-08-15", "2018-08-15 20:09:00", "2018-08-20", "2018-08-20 09:30:00", "ICU", "Hospice"));
        admissionList.add(new Admission(46L, getPatient(18L), "2018-08-01", "2018-08-01 20:09:00", "2018-09-01", "2018-09-01 09:30:00", "ICU", "Hospice"));
        patientList.forEach(p -> p.setAdmissions(getAdmissionByPatientId(p.getId())));

        assertEquals(true, admissionService.isAdmissionOverlapped(getAdmissionByPatientId(18L)));
    }

    @Test
    /*
     * 2 Admission data and 1 admission data is overlap another time period
     * datetime1:         |-------------|
     * datetime2:                |------------|
     */
    public void isAdmissionOverlapped_overlap_outperiod_case1() {
        patientList.add(new Patient(18L, "Law", "M", "Chinese", "1981", "3", "15", "1981-03-15 21:46:00", "2018-08-11 09:30:00"));
        admissionList.add(new Admission(43L, getPatient(18L), "2018-08-01", "2018-08-01 20:09:00", "2018-09-01", "2018-09-01 09:30:00", "ICU", "Hospice"));
        admissionList.add(new Admission(44L, getPatient(18L), "2018-08-15", "2018-08-15 20:09:00", "2018-09-20", "2018-09-20 09:30:00", "ICU", "Hospice"));
        patientList.forEach(p -> p.setAdmissions(getAdmissionByPatientId(p.getId())));

        assertEquals(true, admissionService.isAdmissionOverlapped(getAdmissionByPatientId(18L)));
    }

    @Test
    /*
     * 2 Admission data and 1 admission data is overlap another time period
     * datetime1:                |------------|
     * datetime2:         |-------------|
     */
    public void isAdmissionOverlapped_overlap_outperiod_swap_case2() {
        patientList.add(new Patient(18L, "Law", "M", "Chinese", "1981", "3", "15", "1981-03-15 21:46:00", "2018-08-11 09:30:00"));
        admissionList.add(new Admission(40L, getPatient(18L), "2018-08-15", "2001-08-15 20:09:00", "2001-08-16", "2018-09-16 09:30:00", "ICU", "Hospice"));
        admissionList.add(new Admission(40L, getPatient(18L), "2018-08-01", "2018-08-01 20:09:00", "2018-09-01", "2018-09-01 09:30:00", "ICU", "Hospice"));
        patientList.forEach(p -> p.setAdmissions(getAdmissionByPatientId(p.getId())));

        assertEquals(true, admissionService.isAdmissionOverlapped(getAdmissionByPatientId(18L)));
    }

    @Test
    /*
     * Sample Addmission Data
     * Patient 10 have fake admission only
     * This case is blind test run for any abnormal behavior
     */
    public void isAdmissionOverlapped_commonCase() {
        assertEquals(2, LongStream.rangeClosed(1, 17).mapToObj(ll -> admissionService.isAdmissionOverlapped(getAdmissionByPatientId(ll))).collect(Collectors.toSet()).size());
        assertEquals(1, LongStream.rangeClosed(1, 17).filter(l -> l != 10L).mapToObj(ll -> admissionService.isAdmissionOverlapped(getAdmissionByPatientId(ll))).collect(Collectors.toSet()).size());
        assertEquals(1, LongStream.rangeClosed(1, 17).filter(l -> l == 10L).mapToObj(ll -> admissionService.isAdmissionOverlapped(getAdmissionByPatientId(ll))).collect(Collectors.toSet()).size());
    }


}