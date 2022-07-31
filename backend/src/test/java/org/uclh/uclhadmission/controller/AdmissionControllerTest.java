package org.uclh.uclhadmission.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.uclh.uclhadmission.model.PatientAdmission;
import org.uclh.uclhadmission.model.PatientInfo;
import org.uclh.uclhadmission.model.entity.Admission;
import org.uclh.uclhadmission.model.entity.Patient;
import org.uclh.uclhadmission.service.AdmissionService;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdmissionController.class)
class AdmissionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AdmissionController admissionController;

    @Test
    void getAdmissionReport() throws Exception {

        PatientInfo patientInfo = new PatientInfo(1L, "Rufus Hirst", "M", "Black African",  null);

        List<PatientInfo> patientInfoList = Collections.singletonList(patientInfo);

        given(admissionController.getAdmissionReport("ALL","ALL","ALL",Boolean.TRUE)).willReturn(patientInfoList);

        mvc.perform(get("/api/admission")
                        .param("yearOfBirth", "ALL")
                        .param("sexAtBirth", "ALL")
                        .param("ethnicity", "ALL")
                        .param("isFake", "true")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].patientId", is(1)));
    }
    @Test
    void getAdmissionReport_withoutPara() throws Exception {

        PatientInfo patientInfo = new PatientInfo(1L, "Rufus Hirst", "M", "Black African",  null);
        List<PatientInfo> patientInfoList = Collections.singletonList(patientInfo);
        given(admissionController.getAdmissionReport("ALL","ALL","ALL",Boolean.TRUE)).willReturn(patientInfoList);
        //given(admissionController.getAdmissionReport(null,null,null,null)).willReturn(patientInfoList);

        mvc.perform(get("/api/admission")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}