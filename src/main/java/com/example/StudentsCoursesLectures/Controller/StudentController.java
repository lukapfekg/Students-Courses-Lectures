package com.example.StudentsCoursesLectures.Controller;

import com.example.StudentsCoursesLectures.Model.Course;
import com.example.StudentsCoursesLectures.Model.Lecture;
import com.example.StudentsCoursesLectures.Model.Student;
import com.example.StudentsCoursesLectures.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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

    @GetMapping(path = "/{studentId}")
    public Student getStudent(@PathVariable int studentId) throws SQLException {
        return studentService.getStudent(studentId);
    }

    @GetMapping(path = "/{studentId}/attending-courses")
    public List<Course> getCourses(@PathVariable int studentId) throws SQLException {
        return studentService.getCoursesOfStudent(studentId);
    }

    @GetMapping(path = "/{studentId}/lectures")
    public List<Lecture> getLectures(@PathVariable int studentId) throws SQLException {
        return studentService.getLecturesOfStudent(studentId);
    }

    @GetMapping(path = "/{studentId}/average-grade")
    public double averageGrade(@PathVariable int studentId) throws SQLException {
        return studentService.getAverageGrade(studentId);
    }

    @PostMapping(path = "/{studentId}/course/{courseId}")
    public void gradeStudent(@PathVariable int studentId, @PathVariable int courseId, @RequestBody Map<String, String> body) throws SQLException {
        studentService.gradeStudent(studentId, courseId, Integer.parseInt(body.get("grade")));
    }

    @PostMapping
    public void addStudent(@RequestBody Map<String, String> body) throws SQLException {
        Student student = new Student(body.get("firstName"), body.get("lastName"), body.get("yearEntered"));
        studentService.addStudent(student);
    }

    @PostMapping(path = "/add-to-course")
    public void addStudentToClass(@RequestBody Map<String, String> body) throws SQLException {
        studentService.addStudentToClass(Integer.parseInt(body.get("studentId")), Integer.parseInt(body.get("courseId")));
    }

}
