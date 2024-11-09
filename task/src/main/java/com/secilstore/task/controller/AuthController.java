package com.secilstore.task.controller;

import com.secilstore.task.dto.RegisterDto;
import com.secilstore.task.exception.DoctorException;
import com.secilstore.task.exception.PatientException;
import com.secilstore.task.model.Doctor;
import com.secilstore.task.model.Patient;
import com.secilstore.task.model.UserRole;
import com.secilstore.task.service.DoctorService;
import com.secilstore.task.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/secilstoreapi/auth")
public class AuthController {

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PatientService patientService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) throws DoctorException, PatientException {

        if(registerDto.getUserRole().equals(UserRole.DOCTOR)){
            Doctor doctor = doctorService.createDoctor(registerDto);
            return new ResponseEntity<>("Doctor registered successfully! userID:" + doctor.getUserID(), HttpStatus.OK );
        } else if (registerDto.getUserRole().equals(UserRole.PATIENT)) {
            Patient patient = patientService.createPatient(registerDto);
            return new ResponseEntity<>("Patient registered successfully! userID:" + patient.getUserID(),HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("You need to be DOCTOR or PATIENT at least.", HttpStatus.BAD_REQUEST);

    }
}
