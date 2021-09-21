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
public class CourseService {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final LectureRepository lectureRepository;


    @Autowired
    public CourseService(CourseRepository courseRepository, StudentRepository studentRepository, LectureRepository lectureRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.lectureRepository = lectureRepository;
    }

    public List<Course> getStudents() throws SQLException {
        return courseRepository.printAllCourses();
    }

    public void addNewCourse(Course course) throws SQLException {
        if (courseRepository.doesCourseExist(course)) return;
        courseRepository.addNewCourse(course);
        int id = courseRepository.getCourseId(course.getCourseName());
        course.setId(id);
        courseRepository.createLecturesForCourse(course);
    }

    public Course getCourseWithID(int courseID) throws SQLException {
        return courseRepository.getCourseAtIndex(courseID);
    }

    public List<Student> getStudentsFromCourse(int courseId) throws SQLException {
        List<Integer> idList = studentRepository.getStudentsIdFromCourse(courseId);
        List<Student> students = new ArrayList<>();

        for (Integer id : idList) {
            students.add(studentRepository.getStudentAtIndex(id));
        }

        return students;
    }

    public List<Lecture> getLecturesFromCourse(int courseId) throws SQLException {
        List<Integer> idList = lectureRepository.getLecturesIdFromCourse(courseId);
        List<Lecture> lectures = new ArrayList<>();

        for (Integer id : idList) {
            lectures.add(lectureRepository.getLecture(id));
        }

        return lectures;
    }


    public void deleteCourse(int courseId) throws SQLException {
        List<Lecture> lectures = this.getLecturesFromCourse(courseId);
        for (Lecture lecture : lectures) {
            lectureRepository.deleteLecture(lecture.getId());
            lectureRepository.deleteStudentLectureConnection(lecture.getId());
        }
        courseRepository.deleteCourse(courseId);
        courseRepository.deleteStudentCourseConnection(courseId);
    }
}
