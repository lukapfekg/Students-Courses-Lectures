package com.example.StudentsCoursesLectures.Services;

import com.example.StudentsCoursesLectures.Model.Course;
import com.example.StudentsCoursesLectures.Model.Lecture;
import com.example.StudentsCoursesLectures.Model.Student;
import com.example.StudentsCoursesLectures.Repository.CourseRepository;
import com.example.StudentsCoursesLectures.Repository.LectureRepository;
import com.example.StudentsCoursesLectures.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

    public List<Student> getStudentsFromLecture(int lectureId) throws SQLException {
        List<Integer> idList = StudentRepository.getStudentsIdFromLecture(lectureId);
        List<Student> students = new ArrayList<>();

        for(Integer id : idList){
            students.add(StudentRepository.getStudentAtIndex(id));
        }

        return students;
    }

    public Course getCourseFromLecture(int lectureId) throws SQLException {
        return CourseRepository.getCourseAtIndex(LectureRepository.getCourseIdFromLecture(lectureId));
    }


}
