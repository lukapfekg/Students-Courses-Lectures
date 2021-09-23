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
        if (!studentRepository.doesStudentExist(studentId)) throw new IllegalArgumentException("Student doesnt exits!");
        return studentRepository.getStudent(studentId);
    }

    public void addStudent(String firstName, String lastName, String yearEntered) throws SQLException {
        Student student = new Student(firstName, lastName, yearEntered);
        if (!studentRepository.doesStudentExist(student)) throw new IllegalArgumentException("Student already exists!");
        studentRepository.addNewStudent(student);
    }

    public List<Course> getCoursesOfStudent(int studentId) throws SQLException {
        if (!studentRepository.doesStudentExist(studentId)) throw new IllegalArgumentException("Student doesnt exits!");

        ArrayList<Integer> idList = courseRepository.getCourseIdFromStudent(studentId);
        if (idList.size() == 0) throw new IllegalArgumentException("Student hasn't been added to any courses!");

        ArrayList<Course> courses = new ArrayList<>();
        for (Integer id : idList) {
            courses.add(courseRepository.getCourse(id));
        }
        return courses;
    }

    public List<Lecture> getLecturesOfStudent(int studentId) throws SQLException {
        if (!studentRepository.doesStudentExist(studentId)) throw new IllegalArgumentException("Student doesnt exits!");

        List<Integer> lectureIds = lectureRepository.getLecturesIdFromStudent(studentId);
        if (lectureIds.size() == 0) throw new IllegalArgumentException("Student hasn't been added to any courses!");

        List<Lecture> lectures = new ArrayList<>();
        // ArrayList<Lecture> lectures = lectureIds.stream().forEach(id -> LectureRepository.getLecture(id));

        for (Integer lectureId : lectureIds) {
            lectures.add(lectureRepository.getLecture(lectureId));
        }

        return lectures;
    }

    public void gradeStudent(int studentId, int courseId, int grade) throws SQLException {
        if (!studentRepository.doesStudentExist(studentId)) throw new IllegalArgumentException("Student doesnt exits!");
        if (!courseRepository.doesCourseExist(courseId)) throw new IllegalArgumentException("Course doesnt exits!");
        if (!studentRepository.isStudentInCourse(studentId, courseId))
            throw new IllegalArgumentException("Student is not in that course!");

        studentRepository.gradeStudentAtCourse(studentId, courseId, grade);
    }

    public void addStudentToCourse(int studentId, int courseId) throws SQLException {
        if (!studentRepository.doesStudentExist(studentId)) throw new IllegalArgumentException("Student doesnt exits!");
        if (!courseRepository.doesCourseExist(courseId)) throw new IllegalArgumentException("Course doesnt exits!");
        if (studentRepository.isStudentInCourse(studentId, courseId))
            throw new IllegalArgumentException("Student is already in course!");
        if (courseRepository.isCourseFull(courseId)) throw new IllegalArgumentException("Course is full!");

        studentRepository.addStudentToClass(studentId, courseId);
        courseRepository.incrementCourseStudentNumber(courseId);

        int lectureId = lectureRepository.getFreeLectureFromCourse(courseId);
        if (lectureId == -1) return;
        studentRepository.addStudentToLecture(studentId, lectureId);
        lectureRepository.incrementLectureStudentNumber(lectureId);
    }

    public double getAverageGrade(int studentId) throws SQLException {
        ArrayList<Integer> grades = studentRepository.getAllGradesFromStudent(studentId);
        if (grades.size() == 0) throw new IllegalArgumentException("Student doesn't have any grades!");

        int average = grades.stream().mapToInt(x -> x).sum();
        return (double) average / grades.size();
    }

    public void deleteStudent(int studentId) throws SQLException {
        if (!studentRepository.doesStudentExist(studentId)) throw new IllegalArgumentException("Student doesnt exist!");

        ArrayList<Course> courses = (ArrayList<Course>) this.getCoursesOfStudent(studentId);
        for (Course course : courses) {
            courseRepository.decrementCourseStudentNumber(course.getID());
        }
        studentRepository.deleteStudentFromCourses(studentId);

        ArrayList<Lecture> lectures = (ArrayList<Lecture>) this.getLecturesOfStudent(studentId);
        for (Lecture lecture : lectures) {
            lectureRepository.decrementLectureStudentNumber(lecture.getId());
        }
        studentRepository.deleteStudentFromLecture(studentId);

        studentRepository.deleteStudent(studentId);
    }

}
