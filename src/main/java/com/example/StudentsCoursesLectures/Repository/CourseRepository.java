package com.example.StudentsCoursesLectures.Repository;

import com.example.StudentsCoursesLectures.Model.Course;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class CourseRepository {
    String connectionString = "jdbc:postgresql://localhost:5432/studentSystem";

    public ArrayList<Course> printAllCourses() throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
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
}
