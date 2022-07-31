package org.uclh.uclhadmission.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uclh.uclhadmission.model.entity.Admission;

public interface AdmissionRepository extends JpaRepository<Admission, Long> {
}
