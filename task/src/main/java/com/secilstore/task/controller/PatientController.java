package com.secilstore.task.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.secilstore.task.exception.AppointmentException;
import com.secilstore.task.exception.DoctorException;
import com.secilstore.task.exception.PatientException;
import com.secilstore.task.model.Doctor;
import com.secilstore.task.service.DoctorService;
import com.secilstore.task.service.PatientService;

@RestController
@RequestMapping("/secilstoreapi/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/listAllDoctors")
    public Set<Doctor> getAllDoctors(){
        return doctorService.getAllDoctors();
    }

    @PostMapping("/increaseWallet")
    public String increaseWallet( @RequestParam("userID") long userID,@RequestParam("increaseAmount") String increaseAmount) throws PatientException {
        patientService.increaseWallet(userID, increaseAmount);
        return "Wallet increased.";
    }

    @PostMapping("/newAppointment")
    public String bookAppointment( @RequestParam("userID") long userID,@RequestParam("doctorName") String doctorName,
                                   @RequestParam("doctorSurname") String surName,@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) throws PatientException, AppointmentException, DoctorException {
        patientService.createAppointment(userID, doctorName, surName, startTime, endTime);
        return "Appointment is booked.";
    }

    @DeleteMapping("/cancelAppointment")
    public String cancelAppointment( @RequestParam("userID") long userID,@RequestParam("doctorName") String doctorName,
                                   @RequestParam("doctorSurname") String surName) throws PatientException, AppointmentException, DoctorException {
        patientService.cancelAppointment(userID, doctorName, surName);
        return "Appointment is cancelled.";
    }

}
