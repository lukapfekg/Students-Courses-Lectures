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
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() throws SQLException {
        return studentRepository.getAllStudents();
    }

    public Student getStudent(int studentId) throws SQLException {
        return StudentRepository.getStudentAtIndex(studentId);
    }

    public void addStudent(Student student) throws SQLException {
        if (studentRepository.doesStudentExist(student)) return;
        studentRepository.addNewStudent(student);
    }

    public List<Course> getCoursesOfStudent(int studentId) throws SQLException {
        ArrayList<Integer> idList = CourseRepository.getCourseIDFromStudent(studentId);
        ArrayList<Course> courses = new ArrayList<>();

        for (Integer id : idList) {
            courses.add(CourseRepository.getCourseAtIndex(id));
        }

        return courses;
    }

    public List<Lecture> getLecturesOfStudent(int studentId) throws SQLException {
        List<Integer> lectureIds = LectureRepository.getLecturesIdFromStudent(studentId);
        List<Lecture> lectures = new ArrayList<>();
        // ArrayList<Lecture> lectures = lectureIds.stream().forEach(id -> LectureRepository.getLecture(id));

        for (Integer lectureId : lectureIds) {
            lectures.add(LectureRepository.getLecture(lectureId));
        }

        return lectures;
    }


    public void gradeStudent(int studentId, int courseId, int grade) throws SQLException {
        studentRepository.gradeStudentAtCourse(studentId, courseId, grade);
    }

    public void addStudentToClass(int studentId, int courseId) throws SQLException {
        if (CourseRepository.isCourseFull(courseId)) return;
        if (studentRepository.isStudentInCourse(studentId, courseId)) return;

        studentRepository.addStudentToClass(studentId, courseId);
        CourseRepository.incrementCourseCapacity(courseId);

        int lectureId = LectureRepository.getFreeLectureFromCourse(courseId);
        if (lectureId == -1) return;
        studentRepository.addStudentToLecture(studentId, lectureId);
        LectureRepository.incrementLectureCapacity(lectureId);
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
            CourseRepository.decrementCourseCapacity(course.getID());
        }
        studentRepository.deleteStudentFromCourses(studentId);

        ArrayList<Lecture> lectures = (ArrayList<Lecture>) this.getLecturesOfStudent(studentId);
        for (Lecture lecture : lectures) {
            CourseRepository.decrementCourseCapacity(lecture.getId());
        }
        studentRepository.deleteStudentFromLecture(studentId);

        studentRepository.deleteStudent(studentId);
    }

}
