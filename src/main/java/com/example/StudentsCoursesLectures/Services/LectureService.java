package com.example.StudentsCoursesLectures.Services;

import com.example.StudentsCoursesLectures.Controller.LectureController;
import com.example.StudentsCoursesLectures.Model.Lecture;
import com.example.StudentsCoursesLectures.Repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LectureService {
    private final LectureRepository lectureRepository;

    @Autowired
    public LectureService(LectureRepository lectureRepository){
        this.lectureRepository = lectureRepository;
    }

    public List<Lecture> getLectures() throws SQLException {
        return lectureRepository.printAllLectures();
    }

    public Lecture getLectureWithId(int lectureId) throws SQLException{
        return lectureRepository.getLectureAtIndex(lectureId);
    }

    public ArrayList<Lecture> getLecturesFromStudent(int studentId) throws SQLException{
        return lectureRepository.getLecturesFromStudent(studentId);
    }

}
