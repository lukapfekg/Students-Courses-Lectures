package com.example.StudentsCoursesLectures.Controller;

import com.example.StudentsCoursesLectures.Model.Course;
import com.example.StudentsCoursesLectures.Model.Lecture;
import com.example.StudentsCoursesLectures.Model.Student;
import com.example.StudentsCoursesLectures.Services.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping(path = "/lecture")
public class LectureController {
    private final LectureService lectureService;

    @Autowired
    public LectureController(LectureService lectureService){
        this.lectureService = lectureService;
    }

    @GetMapping
    public List<Lecture> getLectures() throws SQLException {
        return lectureService.getLectures();
    }

    @GetMapping(path = "/{lectureId}/students")
    public List<Student> getStudents(@PathVariable int lectureId) throws SQLException {
        return lectureService.getStudentsFromLecture(lectureId);
    }

    @GetMapping(path = "/{lectureId}/courses")
    public Course getCourses(@PathVariable int lectureId) throws SQLException {
        return lectureService.getCourseFromLecture(lectureId);
    }

}
