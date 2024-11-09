package com.secilstore.task.service;

import com.secilstore.task.dto.RegisterDto;
import com.secilstore.task.exception.AppointmentException;
import com.secilstore.task.exception.DoctorException;
import com.secilstore.task.exception.PatientException;
import com.secilstore.task.model.Appointment;
import com.secilstore.task.model.Doctor;
import com.secilstore.task.model.Patient;
import com.secilstore.task.repository.AppointmentRepository;
import com.secilstore.task.repository.DoctorRepository;
import com.secilstore.task.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Transactional
    public void createAppointment(long userID, String name, String surname, String startTime, String endTime) throws PatientException, DoctorException, AppointmentException {
        Patient patient = patientRepository.findById(userID).orElseThrow(PatientException::new);
        Doctor doctor = doctorRepository.findDoctorByNameAndSurname(name, surname).orElseThrow(DoctorException::new);
        LocalDateTime startTimeTemp = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime endTimeTemp = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        int diff = endTimeTemp.compareTo(startTimeTemp);

        if(diff > 0 && availabilityOfDoctor(startTimeTemp, endTimeTemp, doctor)){
            Duration d = Duration.between(startTimeTemp, endTimeTemp);
            long appTime = d.toMinutes();

            if(patient.getWallet() > (appTime * (doctor.getExamFee()/60))){
                Appointment appointment = new Appointment();
                appointment.setPatient(patient);
                appointment.setDoctor(doctor);
                appointment.setStartTime(startTimeTemp);
                appointment.setEndTime(endTimeTemp);
                appointment.setAppFee(appTime * (doctor.getExamFee()/60));
                appointmentRepository.save(appointment);
                patient.setWallet(patient.getWallet() - (appTime * (doctor.getExamFee()/60)));
                patientRepository.save(patient);
            }
            else throw new AppointmentException("You have no credit in your wallet to book the appointment!");
        }
        else throw new AppointmentException("Given Time is not valid.");


    }

    @Transactional
    public void cancelAppointment(long userID, String name, String surname) throws PatientException, DoctorException, AppointmentException {
        Patient patient = patientRepository.findById(userID).orElseThrow(PatientException::new);
        Doctor doctor = doctorRepository.findDoctorByNameAndSurname(name, surname).orElseThrow(DoctorException::new);
        Appointment appointment = appointmentRepository.findByPatientAndDoctor(patient, doctor).orElseThrow(AppointmentException::new);
        long refund = 0;
        LocalDateTime ldt = LocalDateTime.now();
        Duration d = Duration.between(ldt, appointment.getStartTime());
        long leftMinToApp = d.toMinutes();

        if(leftMinToApp <= 60 && leftMinToApp > 0){
            appointmentRepository.delete(appointment);
            refund = (appointment.getAppFee() * 75) / 100;
            patient.setWallet(patient.getWallet() + refund);
        } else if (leftMinToApp <= 0) {
            throw new AppointmentException("Impossible to cancel.");
        }else{
            appointmentRepository.delete(appointment);
            refund = appointment.getAppFee();
            patient.setWallet(patient.getWallet() + refund);
        }

    }

    @Transactional
    public void changeExamFee(long userID, long examFee) throws DoctorException {
        Doctor doctor = doctorRepository.findById(userID).orElse(null);
        if(doctor != null){
            doctor.setExamFee(examFee);
            doctorRepository.save(doctor);
        }else
            throw new DoctorException("No doctor is NOT found with the given userID" + userID);
    }

    public boolean availabilityOfDoctor(LocalDateTime startTime, LocalDateTime endTime, Doctor doctor) {
        boolean flag = false;
        List<Appointment> appointmentList = appointmentRepository.findByDoctor(doctor);
        appointmentList.sort(Comparator.comparing(Appointment::getStartTime));

        if(!appointmentList.isEmpty()){
            for (Appointment appointment : appointmentList) {
                int diff = appointment.getEndTime().compareTo(startTime);
                if (diff < 0) {
                    flag = true;
                }else flag = false;
            }
        }else flag = true;

        return flag;
    }

    @Transactional
    public Patient createPatient(RegisterDto dto) throws PatientException {
        Patient patient = new Patient();
        patient.setName(dto.getName());
        patient.setSurname(dto.getSurname());
        patient.setPhone(dto.getPhone());
        if(patientRepository.findByPhone(dto.getPhone()).isPresent()){
            throw new PatientException("Phone number is already taken");
        }else{
            patientRepository.save(patient);
            return patient;
        }
    }

    @Transactional
    public void increaseWallet(long userID, String increaseAmount) throws PatientException {
        Patient patient = patientRepository.findById(userID).orElseThrow(PatientException::new);

        BigDecimal bd = new BigDecimal(increaseAmount);
        long l = bd.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
        patient.setWallet(patient.getWallet() + l);
        patientRepository.save(patient);
    }
}
