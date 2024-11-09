package com.secilstore.task.repository;

import com.secilstore.task.model.Appointment;
import com.secilstore.task.model.Doctor;
import com.secilstore.task.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Transactional
    Optional<Appointment> findByPatientAndDoctor(Patient patient, Doctor doctor);

    @Transactional
    List<Appointment> findByDoctor(Doctor doctor);

    @Transactional
    List<Appointment> findByPatient(Patient patient);


}
