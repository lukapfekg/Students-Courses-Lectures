package com.example.StudentsCoursesLectures.Services;

import com.example.StudentsCoursesLectures.Model.Course;
import com.example.StudentsCoursesLectures.Model.Lecture;
import com.example.StudentsCoursesLectures.Model.Student;
import com.example.StudentsCoursesLectures.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() throws SQLException {
        return studentRepository.getAllStudents();
    }

    public void addStudent(Student student) throws SQLException {
        studentRepository.addNewStudent(student);
    }

    public List<Course> getCoursesOfStudent(int studentId) throws SQLException {
        return studentRepository.getAllCoursesFromStudent(studentId);
    }

    public List<Lecture> getLecturesOfStudent(int studentId) throws SQLException {
        return studentRepository.getLecturesOfStudent(studentId);
    }


    public void gradeStudent(int studentId, int courseId, int grade) throws SQLException {
        studentRepository.gradeStudentAtCourse(studentId, courseId, grade);
    }
}
