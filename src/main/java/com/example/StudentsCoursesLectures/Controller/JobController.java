package com.example.StudentsCoursesLectures.Controller;

import com.example.StudentsCoursesLectures.Services.PlaygroundService;
import com.example.StudentsCoursesLectures.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping(path = "/job")
public class JobController {
    private final PlaygroundService service;

    @Autowired
    public JobController(PlaygroundService service, StudentService studentService) {
        this.service = service;
    }

    @PostMapping(path = "/student-average")
    public void getAverageGrade() {
        service.runAverageGradeJob();
    }

    @PostMapping(path = "/crypto/get-Values")
    public void getValues() {
        service.runGetCryptoValue();
    }


}
