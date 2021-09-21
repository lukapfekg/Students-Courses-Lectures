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

@Service
public class LectureService {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final LectureRepository lectureRepository;

    @Autowired
    public LectureService(CourseRepository courseRepository, StudentRepository studentRepository, LectureRepository lectureRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.lectureRepository = lectureRepository;
    }

    public List<Lecture> getLectures() throws SQLException {
        return lectureRepository.printAllLectures();
    }

    public Lecture getLectureWithId(int lectureId) throws SQLException {
        return lectureRepository.getLecture(lectureId);
    }

    public List<Student> getStudentsFromLecture(int lectureId) throws SQLException {
        List<Integer> idList = studentRepository.getStudentsIdFromLecture(lectureId);
        List<Student> students = new ArrayList<>();

        for(Integer id : idList){
            students.add(studentRepository.getStudentAtIndex(id));
        }

        return students;
    }

    public Course getCourseFromLecture(int lectureId) throws SQLException {
        return courseRepository.getCourseAtIndex(lectureRepository.getCourseIdFromLecture(lectureId));
    }


}
