package com.secilstore.task.repository;

import com.secilstore.task.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Transactional
    Optional<Doctor> findDoctorByNameAndSurname(String name, String surname);

    @Transactional
    Optional<Doctor> findDoctorByPhone(String phone);
}
