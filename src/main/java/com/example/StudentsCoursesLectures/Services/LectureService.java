package com.example.StudentsCoursesLectures.Services;

import com.example.StudentsCoursesLectures.Model.Course;
import com.example.StudentsCoursesLectures.Model.Lecture;
import com.example.StudentsCoursesLectures.Model.Student;
import com.example.StudentsCoursesLectures.Repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LectureService {
    private final LectureRepository lectureRepository;

    @Autowired
    public LectureService(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    public List<Lecture> getLectures() throws SQLException {
        return lectureRepository.printAllLectures();
    }

    public Lecture getLectureWithId(int lectureId) throws SQLException {
        return lectureRepository.getLectureAtIndex(lectureId);
    }

    public ArrayList<Lecture> getLecturesFromStudent(int studentId) throws SQLException {
        return lectureRepository.getLecturesFromStudent(studentId);
    }

    public ArrayList<Student> getStudentsFromLecture(int lectureId) throws SQLException {
        return lectureRepository.getStudentsFromLecture(lectureId);
    }

    public Course getCourseFromLecture(int lectureId) throws SQLException {
        return lectureRepository.getCourseFromLecture(lectureId);
    }


}
