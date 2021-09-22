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
        List<Lecture> lectures = lectureRepository.getAllLectures();
        if (lectures.size() == 0) throw new IllegalArgumentException("No lectures in the system!");

        return lectures;
    }

    public Lecture getLectureWithId(int lectureId) throws SQLException {
        if (!lectureRepository.doesLectureExist(lectureId)) throw new IllegalArgumentException("Lecture doesnt exits!");
        return lectureRepository.getLecture(lectureId);
    }

    public List<Student> getStudentsFromLecture(int lectureId) throws SQLException {
        if (!lectureRepository.doesLectureExist(lectureId)) throw new IllegalArgumentException("Lecture doesnt exits!");

        List<Integer> idList = studentRepository.getStudentsIdFromLecture(lectureId);
        if (idList.size() == 0) throw new IllegalArgumentException("Lecture has no students!");

        List<Student> students = new ArrayList<>();
        for (Integer id : idList) {
            students.add(studentRepository.getStudent(id));
        }
        return students;
    }

    public Course getCourseFromLecture(int lectureId) throws SQLException {
        if (!lectureRepository.doesLectureExist(lectureId)) throw new IllegalArgumentException("Lecture doesnt exits!");
        return courseRepository.getCourse(lectureRepository.getCourseIdFromLecture(lectureId));
    }


}
