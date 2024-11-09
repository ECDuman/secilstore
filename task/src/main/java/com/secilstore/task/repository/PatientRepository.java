package com.secilstore.task.repository;

import com.secilstore.task.model.Doctor;
import com.secilstore.task.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Transactional
    Optional<Doctor> findByPhone(String phone);
}
