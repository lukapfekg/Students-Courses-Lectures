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

    public List<Course> getCourses() throws SQLException {
        List<Course> courses = courseRepository.getAllCourses();
        if (courses.size() == 0) throw new IllegalArgumentException("No courses in the system!");

        return courses;
    }

    public void addNewCourse(Course course) throws SQLException {
        if (courseRepository.doesCourseExist(course)) throw new IllegalArgumentException("Course already exists!");

        courseRepository.addNewCourse(course);
        courseRepository.getCourseId(course.getCourseName());
        course.setId(courseRepository.getCourseId(course.getCourseName()));
        courseRepository.createLecturesForCourse(course);
    }

    public Course getCourse(int courseId) throws SQLException {
        if (!courseRepository.doesCourseExist(courseId)) throw new IllegalArgumentException("Course doesnt exist!");

        return courseRepository.getCourse(courseId);
    }

    public List<Student> getStudentsFromCourse(int courseId) throws SQLException {
        if (!courseRepository.doesCourseExist(courseId)) throw new IllegalArgumentException("Course doesnt exist!");

        List<Integer> idList = studentRepository.getStudentsIdFromCourse(courseId);
        if (idList.size() == 0) throw new IllegalArgumentException("Course has no students!");

        List<Student> students = new ArrayList<>();
        for (Integer id : idList) {
            students.add(studentRepository.getStudent(id));
        }
        return students;
    }

    public List<Lecture> getLecturesFromCourse(int courseId) throws SQLException {
        if (!courseRepository.doesCourseExist(courseId)) throw new IllegalArgumentException("Course doesnt exist!");

        List<Integer> idList = lectureRepository.getLecturesIdFromCourse(courseId);
        List<Lecture> lectures = new ArrayList<>();
        for (Integer id : idList) {
            lectures.add(lectureRepository.getLecture(id));
        }
        return lectures;
    }


    public void deleteCourse(int courseId) throws SQLException {
        if (!courseRepository.doesCourseExist(courseId)) throw new IllegalArgumentException("Course doesnt exist!");

        List<Lecture> lectures = this.getLecturesFromCourse(courseId);
        for (Lecture lecture : lectures) {
            lectureRepository.deleteLecture(lecture.getId());
            lectureRepository.deleteStudentLectureConnection(lecture.getId());
        }
        courseRepository.deleteCourse(courseId);
        courseRepository.deleteStudentCourseConnection(courseId);
    }
}
