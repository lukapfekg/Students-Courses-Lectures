package com.example.StudentsCoursesLectures.Repository;

import com.example.StudentsCoursesLectures.Model.Course;
import com.example.StudentsCoursesLectures.Model.DBParameters;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class CourseRepository {

    private final String connectionString = DBParameters.getInstance().getConnectionString();
    private final String username = DBParameters.getInstance().getUsername();
    private final String password = DBParameters.getInstance().getPassword();

    public ArrayList<Course> getAllCourses() throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password);
             Statement statement = connection.createStatement()) {

            final ResultSet resultSet = statement.executeQuery("SELECT * FROM students.courses");
            ArrayList<Course> courseList = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String courseName = resultSet.getString(2);
                int maxNumOfStudents = resultSet.getInt(3);
                int numOfStudents = resultSet.getInt(4);
                Course course = new Course(id, courseName, maxNumOfStudents, numOfStudents);
                courseList.add(course);
            }

            return courseList;
        }
    }

    public void addNewCourse(Course course) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password)) {

            String query = "INSERT INTO students.courses VALUES (DEFAULT, '" + course.getCourseName() + "', " + course.getMaxNumberOfStudents() + ", " + course.getNumberOfStudents() + ")";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }

    public void addNewCourse(String courseName, int maxNumOfStudents) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password)) {

            String query = "INSERT INTO students.courses VALUES (DEFAULT, '" + courseName + "', " + maxNumOfStudents + ", " + 0 + ")";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }

    public int getCourseId(String courseName) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT id FROM students.courses WHERE course_name='" + courseName + "'");
            resultSet.next();
            return resultSet.getInt(1);
        }
    }

    public void createLecturesForCourse(Course course) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password)) {
            int maxNumOfStudents = course.getMaxNumberOfStudents() / 3;

            for (int i = 1; i < 4; i++) {
                String query = "INSERT INTO students.lectures VALUES (DEFAULT, '" + course.getCourseName() + " L" + i + "', " + maxNumOfStudents + ", " + 0 + ", " + course.getID() + ")";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.execute();
            }
        }
    }

    public boolean doesCourseExist(Course course) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password);
             Statement statement = connection.createStatement()) {

            final ResultSet resultSet = statement.executeQuery("SELECT * FROM students.courses WHERE course_name='"
                    + course.getCourseName() + "'");
            return resultSet.next();
        }
    }

    public boolean doesCourseExist(int courseId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password);
             Statement statement = connection.createStatement()) {

            final ResultSet resultSet = statement.executeQuery("SELECT * FROM students.courses WHERE id=" + courseId);
            return resultSet.next();
        }
    }

    public Course getCourse(int courseID) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM students.courses WHERE id=" + courseID);
            resultSet.next();
            int id = resultSet.getInt(1);
            String courseName = resultSet.getString(2);
            int maxNumOfStudents = resultSet.getInt(3);
            int numOfStudents = resultSet.getInt(4);

            return new Course(id, courseName, maxNumOfStudents, numOfStudents);
        }
    }

    public void incrementCourseStudentNumber(int courseId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password)) {

            String query = "UPDATE students.courses SET num_of_students=num_of_students+1 WHERE id=" + courseId;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }

    public void decrementCourseStudentNumber(int courseId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password)) {

            String query = "UPDATE students.courses SET num_of_students=num_of_students-1 WHERE id=" + courseId;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }

    public boolean isCourseFull(int courseID) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT max_num_of_students, num_of_students FROM students.courses WHERE id=" + courseID);
            resultSet.next();

            int maxNumOfStudents = resultSet.getInt(1);
            int numOfStudents = resultSet.getInt(2);
            return maxNumOfStudents == numOfStudents;
        }
    }

    public void deleteCourse(int courseId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password)) {

            String query = "DELETE FROM students.courses WHERE id=" + courseId;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }

    public void deleteStudentCourseConnection(int courseId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password)) {

            String query = "DELETE FROM students.students_courses WHERE id_courses=" + courseId;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }

    public ArrayList<Integer> getCourseIdFromStudent(int studentId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT id_courses FROM students.students_courses WHERE id_students=" + studentId);
            ArrayList<Integer> courseList = new ArrayList<>();

            while (resultSet.next()) {
                int courseID = resultSet.getInt(1);
                courseList.add(courseID);
            }

            return courseList;
        }
    }

}
