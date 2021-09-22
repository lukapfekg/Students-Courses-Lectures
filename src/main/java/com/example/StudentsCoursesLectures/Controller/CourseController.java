package com.example.StudentsCoursesLectures.Controller;

import com.example.StudentsCoursesLectures.Model.Course;
import com.example.StudentsCoursesLectures.Model.Lecture;
import com.example.StudentsCoursesLectures.Model.Student;
import com.example.StudentsCoursesLectures.Services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
        return courseService.getCourses();
    }

    @GetMapping(path = "/{courseId}/attending-students")
    public List<Student> getStudentsFromCourse(@PathVariable int courseId) throws SQLException {
        return courseService.getStudentsFromCourse(courseId);
    }

    @GetMapping(path = "/{courseId}")
    public Course getCourse(@PathVariable int courseId) throws SQLException {
        return courseService.getCourse(courseId);
    }

    @GetMapping(path = "/{courseId}/lectures-of-course")
    public List<Lecture> getLectures(@PathVariable int courseId) throws SQLException {
        return courseService.getLecturesFromCourse(courseId);
    }

    @PostMapping
    public void createCourse(@RequestBody Map<String, String> body) throws SQLException {
        Course course = new Course(body.get("courseName"), Integer.parseInt(body.get("maxNumOfStudents")));
        courseService.addNewCourse(course);
    }

    @DeleteMapping(path = "/{courseId}")
    public void deleteCourse(@PathVariable int courseId) throws SQLException {
        courseService.deleteCourse(courseId);
    }

}
