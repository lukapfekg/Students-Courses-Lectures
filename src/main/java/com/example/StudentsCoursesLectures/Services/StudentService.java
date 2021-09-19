package com.example.StudentsCoursesLectures.Services;

import com.example.StudentsCoursesLectures.Model.Course;
import com.example.StudentsCoursesLectures.Model.Lecture;
import com.example.StudentsCoursesLectures.Model.Student;
import com.example.StudentsCoursesLectures.Repository.CourseRepository;
import com.example.StudentsCoursesLectures.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.StudentsCoursesLectures.Repository.CourseRepository.isCourseFull;
import static com.example.StudentsCoursesLectures.Repository.LectureRepository.getFreeLectureFromCourse;

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

    public Student getStudent(int studentId) throws SQLException {
        return studentRepository.getStudentAtIndex(studentId);
    }

    public void addStudent(Student student) throws SQLException {
        if (studentRepository.doesStudentExist(student)) return;
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

    public void addStudentToClass(int studentId, int courseId) throws SQLException {
        if (isCourseFull(courseId)) return;
        if (studentRepository.isStudentInCourse(studentId, courseId)) return;
        studentRepository.addStudentToClass(studentId, courseId);
        int lectureId = getFreeLectureFromCourse(courseId);
        if (lectureId == -1) return;
        studentRepository.addStudentToLecture(studentId, lectureId);
    }

    public double getAverageGrade(int studentId) throws SQLException {
        ArrayList<Integer> grades = studentRepository.getAllGradesFromStudent(studentId);
        int avg = 0;
        for (Integer grade : grades) {
            avg += grade;
        }
        return (double) avg / grades.size();
    }

}
