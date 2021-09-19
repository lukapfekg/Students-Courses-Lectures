package com.example.StudentsCoursesLectures.Repository;

import com.example.StudentsCoursesLectures.Model.Course;
import com.example.StudentsCoursesLectures.Model.Lecture;
import com.example.StudentsCoursesLectures.Model.Student;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.StudentsCoursesLectures.Repository.CourseRepository.getCourse;
import static com.example.StudentsCoursesLectures.Repository.CourseRepository.incrementCourseCapacity;
import static com.example.StudentsCoursesLectures.Repository.LectureRepository.getLecture;
import static com.example.StudentsCoursesLectures.Repository.LectureRepository.incrementLectureCapacity;

@Repository
public class StudentRepository {
    String connectionString = "jdbc:postgresql://localhost:5432/studentSystem";


    public List<Student> getAllStudents() throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {

            final ResultSet resultSet = statement.executeQuery("SELECT * FROM students.students");
            List<Student> studentList = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String yearEntered = resultSet.getString(4);

                Student student = new Student(id, firstName, lastName, yearEntered);
                studentList.add(student);
            }

            return studentList;
        }
    }

    public void addNewStudent(Student student) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root")) {

            String query = "INSERT INTO students.students VALUES (DEFAULT, '" + student.getFirstName() + "', '" + student.getLastName() + "', '" + student.getYearEntered() + "')";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }

    public boolean doesStudentExist(Student student) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {

            final ResultSet resultSet = statement.executeQuery("SELECT * FROM students.students WHERE first_name='"
                    + student.getFirstName() + "' AND last_name='" + student.getLastName() + "' AND year_entered='" + student.getYearEntered() + "'");

            return resultSet.next();
        }
    }

    public ArrayList<Course> getAllCoursesFromStudent(int studentId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT id_courses FROM students.students_courses WHERE id_students=" + studentId);
            ArrayList<Course> courseList = new ArrayList<>();
            while (resultSet.next()) {
                int courseID = resultSet.getInt(1);
                courseList.add(getCourseAtIndex(courseID));
            }

            return courseList;
        }
    }

    public Course getCourseAtIndex(int courseID) throws SQLException {
        return getCourse(courseID);
    }

    public void gradeStudentAtCourse(int studentID, int courseID, int grade) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root")) {

            String query = "UPDATE students.students_courses SET grade=" + grade + "WHERE id_students=" + studentID + " AND id_courses=" + courseID;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();

        }
    }

    public ArrayList<Integer> getAllGradesFromStudent(int studentId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT grade FROM students.students_courses WHERE id_students=" + studentId);
            ArrayList<Integer> grades = new ArrayList<>();

            while (resultSet.next())
                if (resultSet.getInt(1) > 5) grades.add(resultSet.getInt(1));

            return grades;
        }
    }

    public Student getStudentAtIndex(int studentID) throws SQLException {
        return getStudent(studentID, connectionString);
    }

    static Student getStudent(int studentID, String connectionString) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM students.students WHERE id=" + studentID);
            resultSet.next();
            int id = resultSet.getInt(1);
            String firstName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            String yearEntered = resultSet.getString(4);

            return new Student(id, firstName, lastName, yearEntered);
        }
    }

    public ArrayList<Lecture> getLecturesOfStudent(int studentId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT id_lectures FROM students.students_lectures WHERE id_students=" + studentId);
            ArrayList<Lecture> courseList = new ArrayList<>();
            while (resultSet.next()) {
                int lectureId = resultSet.getInt(1);
                courseList.add(getLecture(lectureId));
            }

            return courseList;
        }
    }

    public void addStudentToClass(int studentId, int courseId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root")) {

            String query = "INSERT INTO students.students_courses VALUES (" + studentId + ", " + courseId + ", " + "3)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            incrementCourseCapacity(courseId);
        }
    }

    public void addStudentToLecture(int studentId, int lectureId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root")) {

            String query = "INSERT INTO students.students_lectures VALUES (" + studentId + ", " + lectureId + ")";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            incrementLectureCapacity(lectureId);
        }
    }

    public boolean isStudentInCourse(int studentId, int courseId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM students.students_courses WHERE id_students="
                    + studentId + " AND id_courses=" + courseId);
            return resultSet.next();
        }
    }

    public void deleteStudentFromCourses(int studentId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root")) {

            String query = "DELETE FROM students.students_courses WHERE id_students=" + studentId;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }

    public void deleteStudentFromLecture(int studentId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root")) {

            String query = "DELETE FROM students.students_lectures WHERE id_students=" + studentId;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }


    public void deleteStudent(int studentId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root")) {

            String query = "DELETE FROM students.students WHERE id=" + studentId;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }
}

