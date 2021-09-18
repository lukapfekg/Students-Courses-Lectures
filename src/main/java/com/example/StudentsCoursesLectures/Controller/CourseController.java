package com.example.StudentsCoursesLectures.Controller;

import com.example.StudentsCoursesLectures.Model.Course;
import com.example.StudentsCoursesLectures.Model.Lecture;
import com.example.StudentsCoursesLectures.Model.Student;
import com.example.StudentsCoursesLectures.Services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping(path = "/course")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }


    @GetMapping
    public List<Course> getCourses() throws SQLException {
        return courseService.getStudents();
    }

    @GetMapping(path = "/{courseId}/attending-students")
    public List<Student> getStudentsFromCourse(@PathVariable int courseId) throws SQLException {
        return courseService.getStudentsFromCourse(courseId);
    }

    @GetMapping(path = "/{courseId}")
    public Course getCourse(@PathVariable int courseId) throws SQLException {
        return courseService.getCourseWithID(courseId);
    }

    @GetMapping(path = "/{courseId}/lectures-of-course")
    public List<Lecture> getLectures(@PathVariable int courseId) throws SQLException {
        return courseService.getLecturesFromCourse(courseId);
    }


}
