package org.uclh.uclhadmission.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.uclh.uclhadmission.model.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient> {
}
