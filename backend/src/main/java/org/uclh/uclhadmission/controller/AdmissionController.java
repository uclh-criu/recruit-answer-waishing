package org.uclh.uclhadmission.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.uclh.uclhadmission.model.PatientInfo;
import org.uclh.uclhadmission.service.AdmissionService;

import java.util.List;

@RestController
@RequestMapping("/api/admission")
public class AdmissionController {

    @Autowired
    private AdmissionService admissionService;

    @GetMapping(value = "", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:3000")
    /**
     * Get Admission Report Restful API Controller
     */
    public @ResponseBody
    List<PatientInfo>  getAdmissionReport(@RequestParam(name = "yearOfBirth",defaultValue = "ALL") String yearOfBirth,
                                     @RequestParam(name = "sexAtBirth",defaultValue = "All") String sexAtBirth,
                                     @RequestParam(name = "ethnicity",defaultValue = "All") String ethnicity,
                                     @RequestParam(name = "isFake" , defaultValue = "true") Boolean isFake) {
        return admissionService.getAdmissionReport(yearOfBirth,sexAtBirth,ethnicity,isFake);
    }
}
