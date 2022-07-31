package org.uclh.uclhadmission.repo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.uclh.uclhadmission.model.entity.Admission;
import org.uclh.uclhadmission.model.entity.Patient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void findAll() {
        List<Patient> patients = patientRepository.findAll();
        assertThat(patients).hasSize(17);
    }
}