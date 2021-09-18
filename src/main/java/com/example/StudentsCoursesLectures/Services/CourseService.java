package com.example.StudentsCoursesLectures.Services;

import com.example.StudentsCoursesLectures.Model.Course;
import com.example.StudentsCoursesLectures.Model.Lecture;
import com.example.StudentsCoursesLectures.Model.Student;
import com.example.StudentsCoursesLectures.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    private CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getStudents() throws SQLException {
        return courseRepository.printAllCourses();
    }

    public void addNewCourse(Course course) throws SQLException {
        if(courseRepository.doesCourseExist(course)) return;
        courseRepository.addNewCourse(course);
    }

    public Course getCourseWithID(int courseID) throws SQLException {
        return courseRepository.getCourseAtIndex(courseID);
    }

    public ArrayList<Student> getStudentsFromCourse(int courseId) throws SQLException {
        return courseRepository.getAllStudentsFromCourse(courseId);
    }

    public ArrayList<Lecture> getLecturesFromCourse(int courseId) throws SQLException {
       return courseRepository.getLecturesFromCourse(courseId);
    }



}
