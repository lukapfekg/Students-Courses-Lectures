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

import static com.example.StudentsCoursesLectures.Repository.LectureRepository.deleteLecture;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getStudents() throws SQLException {
        return courseRepository.printAllCourses();
    }

    public void addNewCourse(Course course) throws SQLException {
        if (courseRepository.doesCourseExist(course)) return;
        courseRepository.addNewCourse(course);
        int id = CourseRepository.getCourseId(course.getCourseName());
        course.setId(id);
        CourseRepository.createLecturesForCourse(course);
    }

    public Course getCourseWithID(int courseID) throws SQLException {
        return CourseRepository.getCourseAtIndex(courseID);
    }

    public List<Student> getStudentsFromCourse(int courseId) throws SQLException {
        List<Integer> idList = StudentRepository.getStudentsIdFromCourse(courseId);
        List<Student> students = new ArrayList<>();

        for(Integer id : idList){
            students.add(StudentRepository.getStudentAtIndex(id));
        }

        return students;
    }

    public List<Lecture> getLecturesFromCourse(int courseId) throws SQLException {
        List<Integer> idList = LectureRepository.getLecturesIdFromCourse(courseId);
        List<Lecture> lectures = new ArrayList<>();

        for(Integer id : idList){
            lectures.add(LectureRepository.getLecture(id));
        }

        return lectures;
    }


    public void deleteCourse(int courseId) throws SQLException {
        List<Lecture> lectures = this.getLecturesFromCourse(courseId);
        for (Lecture lecture : lectures) {
            deleteLecture(lecture.getId());
        }
        courseRepository.deleteCourse(courseId);
        CourseRepository.deleteStudentCourseConnection(courseId);
    }
}
