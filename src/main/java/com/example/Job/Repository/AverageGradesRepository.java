package com.example.Job.Repository;

import com.example.StudentsCoursesLectures.Model.DBParameters;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AverageGradesRepository {
    private static final String connectionString = DBParameters.getInstance().getConnectionString();
    public static final String username = DBParameters.getInstance().getUsername();
    public static final String password = DBParameters.getInstance().getPassword();


    public static List<Double> getAllAverageGradesOfStudent(int studentId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password);
             Statement statement = connection.createStatement()) {

            final ResultSet resultSet = statement.executeQuery("SELECT average_grade FROM students.students_averagegrades WHERE id_students=" + studentId);
            List<Double> courseList = new ArrayList<>();

            while (resultSet.next()) {
                double grade = resultSet.getInt(1);
                courseList.add(grade);
            }

            return courseList;
        }
    }

    public static void deleteGrade(int id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password)) {

            String query = "DELETE FROM students.students_averagegrades WHERE id=" + id;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }

    public static void addGradeToStudent(int studentId, double grade) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password)) {

            String query = "INSERT INTO students.students_averagegrades VALUES (DEFAULT, " + studentId + ", " + grade + ")";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }

    public static int getId(int studentId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password);
             Statement statement = connection.createStatement()) {

            final ResultSet resultSet = statement.executeQuery("SELECT id FROM students.students_averagegrades WHERE id_students=" + studentId);
            resultSet.next();

            return resultSet.getInt(1);
        }
    }
}
