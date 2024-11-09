package com.secilstore.task.controller;

import com.secilstore.task.exception.DoctorException;
import com.secilstore.task.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secilstoreapi/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @PostMapping("/changeExaminationFee")
    public String changeExaminationFee(@RequestParam("userID") long userID, @RequestParam("examinationFee") String examFee) throws DoctorException {
        doctorService.changeExamFee(userID,examFee);
        return "Examination Fee is changed.";
    }
}
