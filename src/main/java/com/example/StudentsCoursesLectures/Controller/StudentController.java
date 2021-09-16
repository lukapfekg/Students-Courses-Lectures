package com.example.StudentsCoursesLectures.Controller;

import com.example.StudentsCoursesLectures.Model.Course;
import com.example.StudentsCoursesLectures.Model.Student;
import com.example.StudentsCoursesLectures.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping(path = "/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping
    public List<Student> getStudents() throws SQLException {
        return studentService.getStudents();
    }

    @GetMapping(path = "/{studentId}/attending-courses")
    public List<Course> getLectures(@PathVariable int studentId) throws SQLException {
        return studentService.getCoursesOfStudent(studentId);
    }

    @PostMapping(path = "/{studentId}/course/{courseId}")
    public void gradeStudent(@PathVariable int studentId, @PathVariable int courseId, @RequestParam int grade) throws SQLException {
        studentService.gradeStudent(studentId, courseId, grade);
    }

}
