package com.example.StudentsCoursesLectures.Repository;

import com.example.StudentsCoursesLectures.Model.DBParameters;
import com.example.StudentsCoursesLectures.Model.Student;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepository {

    private final String connectionString = DBParameters.getInstance().getConnectionString();
    private final String username = DBParameters.getInstance().getUsername();
    private final String password = DBParameters.getInstance().getPassword();

    public static List<Student> getAllStudents() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DBParameters.getInstance().getConnectionString(), DBParameters.getInstance().getUsername(), DBParameters.getInstance().getPassword());
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
        try (Connection connection = DriverManager.getConnection(connectionString, username, password)) {

            String query = "INSERT INTO students.students VALUES (DEFAULT, '" + student.getFirstName() + "', '" + student.getLastName() + "', '" + student.getYearEntered() + "')";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }

    public void addNewStudent(String firstName, String lastName, String yearEntered) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password)) {

            String query = "INSERT INTO students.students VALUES (DEFAULT, '" + firstName + "', '" + lastName + "', '" + yearEntered + "')";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }

    public boolean doesStudentExist(Student student) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password);
             Statement statement = connection.createStatement()) {

            final ResultSet resultSet = statement.executeQuery("SELECT * FROM students.students WHERE first_name='"
                    + student.getFirstName() + "' AND last_name='" + student.getLastName() + "' AND year_entered='" + student.getYearEntered() + "'");

            return resultSet.next();
        }
    }

    public boolean doesStudentExist(int studentId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password);
             Statement statement = connection.createStatement()) {

            final ResultSet resultSet = statement.executeQuery("SELECT * FROM students.students WHERE id=" + studentId);

            return resultSet.next();
        }
    }

    public void gradeStudentAtCourse(int studentID, int courseID, int grade) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password)) {

            String query = "UPDATE students.students_courses SET grade=" + grade + " WHERE id_students=" + studentID + " AND id_courses=" + courseID;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }

    public static ArrayList<Integer> getAllGradesFromStudent(int studentId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DBParameters.getInstance().getConnectionString(), DBParameters.getInstance().getUsername(), DBParameters.getInstance().getPassword());
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT grade FROM students.students_courses WHERE id_students=" + studentId);
            ArrayList<Integer> grades = new ArrayList<>();

            while (resultSet.next())
                if (resultSet.getInt(1) > 5) grades.add(resultSet.getInt(1));

            return grades;
        }
    }

    public Student getStudent(int studentID) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password);
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

    public void addStudentToClass(int studentId, int courseId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password)) {

            String query = "INSERT INTO students.students_courses VALUES (" + studentId + ", " + courseId + ", " + "3)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }

    public void addStudentToLecture(int studentId, int lectureId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password)) {

            String query = "INSERT INTO students.students_lectures VALUES (" + studentId + ", " + lectureId + ")";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }

    public boolean isStudentInCourse(int studentId, int courseId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM students.students_courses WHERE id_students="
                    + studentId + " AND id_courses=" + courseId);
            return resultSet.next();
        }
    }

    public void deleteStudentFromCourses(int studentId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password)) {

            String query = "DELETE FROM students.students_courses WHERE id_students=" + studentId;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }

    public void deleteStudentFromLecture(int studentId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password)) {

            String query = "DELETE FROM students.students_lectures WHERE id_students=" + studentId;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }

    public void deleteStudent(int studentId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password)) {

            String query = "DELETE FROM students.students WHERE id=" + studentId;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }

    public List<Integer> getStudentsIdFromCourse(int courseId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT id_students FROM students.students_courses WHERE id_courses=" + courseId);
            List<Integer> idList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                idList.add(id);
            }

            return idList;
        }
    }

    public List<Integer> getStudentsIdFromLecture(int lectureId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT id_students FROM students.students_lectures WHERE id_lectures=" + lectureId);
            List<Integer> idList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                idList.add(id);
            }

            return idList;
        }
    }

}

