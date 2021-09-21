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
public class StudentService {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final LectureRepository lectureRepository;

    @Autowired
    public StudentService(CourseRepository courseRepository, StudentRepository studentRepository, LectureRepository lectureRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.lectureRepository = lectureRepository;
    }

    public List<Student> getStudents() throws SQLException {
        return studentRepository.getAllStudents();
    }

    public Student getStudent(int studentId) throws SQLException {
        return studentRepository.getStudentAtIndex(studentId);
    }

    public void addStudent(Student student) throws SQLException {
        if (studentRepository.doesStudentExist(student)) return;
        studentRepository.addNewStudent(student);
    }

    public List<Course> getCoursesOfStudent(int studentId) throws SQLException {
        ArrayList<Integer> idList = courseRepository.getCourseIDFromStudent(studentId);
        ArrayList<Course> courses = new ArrayList<>();

        for (Integer id : idList) {
            courses.add(courseRepository.getCourseAtIndex(id));
        }

        return courses;
    }

    public List<Lecture> getLecturesOfStudent(int studentId) throws SQLException {
        List<Integer> lectureIds = lectureRepository.getLecturesIdFromStudent(studentId);
        List<Lecture> lectures = new ArrayList<>();
        // ArrayList<Lecture> lectures = lectureIds.stream().forEach(id -> LectureRepository.getLecture(id));

        for (Integer lectureId : lectureIds) {
            lectures.add(lectureRepository.getLecture(lectureId));
        }

        return lectures;
    }


    public void gradeStudent(int studentId, int courseId, int grade) throws SQLException {
        studentRepository.gradeStudentAtCourse(studentId, courseId, grade);
    }

    public void addStudentToClass(int studentId, int courseId) throws SQLException {
        if (courseRepository.isCourseFull(courseId)) return;
        if (studentRepository.isStudentInCourse(studentId, courseId)) return;

        studentRepository.addStudentToClass(studentId, courseId);
        courseRepository.incrementCourseCapacity(courseId);

        int lectureId = lectureRepository.getFreeLectureFromCourse(courseId);
        if (lectureId == -1) return;
        studentRepository.addStudentToLecture(studentId, lectureId);
        lectureRepository.incrementLectureCapacity(lectureId);
    }

    public double getAverageGrade(int studentId) throws SQLException {
        ArrayList<Integer> grades = studentRepository.getAllGradesFromStudent(studentId);
        if(grades.size() == 0) return -1;
        int average = grades.stream().mapToInt(x -> x).sum();
        return (double) average / grades.size();
    }

    public void deleteStudent(int studentId) throws SQLException {
        ArrayList<Course> courses = (ArrayList<Course>) this.getCoursesOfStudent(studentId);
        for (Course course : courses) {
            courseRepository.decrementCourseCapacity(course.getID());
        }
        studentRepository.deleteStudentFromCourses(studentId);

        ArrayList<Lecture> lectures = (ArrayList<Lecture>) this.getLecturesOfStudent(studentId);
        for (Lecture lecture : lectures) {
           lectureRepository.decrementLectureCapacity(lecture.getId());
        }
        studentRepository.deleteStudentFromLecture(studentId);

        studentRepository.deleteStudent(studentId);
    }

}
