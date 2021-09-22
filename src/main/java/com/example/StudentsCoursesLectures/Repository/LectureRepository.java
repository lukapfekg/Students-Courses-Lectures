package com.example.StudentsCoursesLectures.Repository;

import com.example.StudentsCoursesLectures.Model.Lecture;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LectureRepository {

    private final String connectionString = "jdbc:postgresql://localhost:5432/studentSystem";

    public List<Lecture> getAllLectures() throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {

            final ResultSet resultSet = statement.executeQuery("SELECT * FROM students.lectures");
            List<Lecture> lectureList = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String lectureName = resultSet.getString(2);
                int maxNumOfStudents = resultSet.getInt(3);
                int numOfStudents = resultSet.getInt(4);
                int courseId = resultSet.getInt(5);
                Lecture lecture = new Lecture(id, lectureName, maxNumOfStudents, numOfStudents, courseId);
                lectureList.add(lecture);
            }

            connection.close();
            return lectureList;
        }
    }

    public void decrementLectureStudentNumber(int lectureID) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root")) {

            String query = "UPDATE students.lectures SET num_of_students=num_of_students-1 WHERE id=" + lectureID;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }

    public Lecture getLecture(int lectureID) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM students.lectures WHERE id=" + lectureID);
            resultSet.next();
            int id = resultSet.getInt(1);
            String lectureName = resultSet.getString(2);
            int maxNumOfStudents = resultSet.getInt(3);
            int numOfStudents = resultSet.getInt(4);
            int courseID = resultSet.getInt(5);

            return new Lecture(id, lectureName, maxNumOfStudents, numOfStudents, courseID);
        }
    }

    public List<Integer> getLecturesIdFromStudent(int studentId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT id_lectures FROM students.students_lectures WHERE id_students=" + studentId);
            List<Integer> idList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                idList.add(id);
            }

            return idList;
        }
    }

    public List<Integer> getLecturesIdFromCourse(int courseId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT id FROM students.lectures WHERE course_id=" + courseId);
            List<Integer> idList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                idList.add(id);
            }

            return idList;
        }
    }

    public int getCourseIdFromLecture(int lectureId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT course_id FROM students.lectures WHERE id=" + lectureId);
            resultSet.next();

            return resultSet.getInt(1);
        }
    }

    public int getFreeLectureFromCourse(int courseId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT id, max_num_of_students, num_of_students FROM students.lectures WHERE course_id=" + courseId);
            int lectureId = -1;

            while (resultSet.next()) {
                if (resultSet.getInt(2) > resultSet.getInt(3) &&
                        (lectureId > resultSet.getInt(1) || lectureId == -1)) lectureId = resultSet.getInt(1);
            }

            return lectureId;
        }
    }

    public void incrementLectureStudentNumber(int lectureId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root")) {
            String query = "UPDATE students.lectures SET num_of_students=num_of_students+1 WHERE id=" + lectureId;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }

    public void deleteLecture(int lectureId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root")) {

            String query = "DELETE FROM students.lectures WHERE id=" + lectureId;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }

    public void deleteStudentLectureConnection(int lectureId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root")) {

            String query = "DELETE FROM students.students_lectures WHERE id_lectures=" + lectureId;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }

    public boolean doesLectureExist(int lectureId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {

            final ResultSet resultSet = statement.executeQuery("SELECT * FROM students.lectures WHERE id=" + lectureId);
            return resultSet.next();
        }
    }

}
