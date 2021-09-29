package com.example.StudentsCoursesLectures.Jobs;

import com.example.StudentsCoursesLectures.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("student/timer")
public class JobController {
    private final PlaygroundService service;
    private final StudentService studentService;

    @Autowired
    public JobController(PlaygroundService service, StudentService studentService) {
        this.service = service;
        this.studentService = studentService;
    }

    @PostMapping("/average")
    public void getAverageGrade() throws SQLException {
        service.runAverageGradeJob();
    }


}
