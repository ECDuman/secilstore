package com.secilstore.task.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.secilstore.task.dto.RegisterDto;
import com.secilstore.task.exception.DoctorException;
import com.secilstore.task.model.Doctor;
import com.secilstore.task.repository.DoctorRepository;

@Service
public class DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    public Set<Doctor> getAllDoctors(){
        Set<Doctor> doctors = new HashSet<>();
        doctors.addAll(doctorRepository.findAll());
        return doctors;
    }

    @Transactional
    public void changeExamFee(long userID, String examFee) throws DoctorException{

        Doctor doctor = doctorRepository.findById(userID).orElse(null);
        if(doctor != null){
            BigDecimal bd = new BigDecimal(examFee);
            long l = bd.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
            doctor.setExamFee(l);
            doctorRepository.save(doctor);
        }else
            throw new DoctorException("No doctor is found with the given userID" + userID);
    }

    @Transactional
    public Doctor createDoctor(RegisterDto dto) throws DoctorException {
        Doctor doctor = new Doctor();
        doctor.setName(dto.getName());
        doctor.setSurname(dto.getSurname());
        doctor.setPhone(dto.getPhone());
        if(doctorRepository.findDoctorByPhone(dto.getPhone()).isPresent()){
            throw new DoctorException("Phone number is already taken");
        }else{
            doctorRepository.save(doctor);
            return doctor;
        }
    }
}
