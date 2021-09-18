package com.example.StudentsCoursesLectures.Repository;

import com.example.StudentsCoursesLectures.Model.Course;
import com.example.StudentsCoursesLectures.Model.Lecture;
import com.example.StudentsCoursesLectures.Model.Student;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

import static com.example.StudentsCoursesLectures.Repository.CourseRepository.getCourse;
import static com.example.StudentsCoursesLectures.Repository.StudentRepository.getStudent;

@Repository
public class LectureRepository {
    private static String connectionString = "jdbc:postgresql://localhost:5432/studentSystem";

    public ArrayList<Lecture> printAllLectures() throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM students.lectures");
            ArrayList<Lecture> lectureList = new ArrayList<>();

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

    private int addNewLecture(Course course) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root")) {
            int lectureNum = (course.getNumberOfStudents() == 1 ? 1 : 4 - course.getMaxNumberOfStudents() / course.getNumberOfStudents());
            int maxNumOfStudents = course.getMaxNumberOfStudents() / 3;
            String lectureName = course.getCourseName() + " L" + lectureNum;

            String query = "INSERT INTO students.lectures VALUES (DEFAULT, '" + lectureName + "', " + maxNumOfStudents + ", " + 0 + ", " + course.getID() + ")";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();

            return getLectureID(lectureName);
        }
    }

    private int getLectureID(String lectureName) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT id FROM students.lectures WHERE lecture_name='" + lectureName + "'");

            resultSet.next();
            return resultSet.getInt(1);
        }
    }


    public int getNumberOfStudentsInLecture(int lectureID) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT num_of_students FROM students.lectures WHERE id=" + lectureID);
            resultSet.next();
            return resultSet.getInt(1);
        }
    }



    private void decrementLectureCapacity(int lectureID) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {
            int numOfStudents = getNumberOfStudentsInLecture(lectureID);
            numOfStudents--;

            String query = "UPDATE students.lectures SET num_of_students=" + numOfStudents + " WHERE id=" + lectureID;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }

    public Lecture getLectureAtIndex(int lectureID) throws SQLException {
        return getLecture(lectureID, connectionString);
    }

    static Lecture getLecture(int lectureID, String connectionString) throws SQLException {
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

    private ArrayList<Integer> getLecturesIDFromStudent(int studentId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT id_lectures FROM students.students_lectures WHERE id_students=" + studentId);
            ArrayList<Integer> idList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                idList.add(id);
            }

            return idList;
        }
    }

    public ArrayList<Lecture> getLecturesFromStudent(int studentId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {
            ArrayList<Integer> lecturesID = getLecturesIDFromStudent(studentId);
            ArrayList<Lecture> lectures = new ArrayList<>();
            for (Integer integer : lecturesID) {
                ResultSet resultSet = statement.executeQuery("SELECT id_lectures FROM students.students_lectures WHERE id_students=" + integer);
                resultSet.next();
                Lecture lecture = getLectureAtIndex(resultSet.getInt(1));
                lectures.add(lecture);
            }
            return lectures;
        }
    }

    private ArrayList<Integer> getLecturesFromCourse(Course course) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT id FROM students.lectures WHERE course_id=" + course.getID());
            ArrayList<Integer> intList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                intList.add(id);
            }
            return intList;
        }
    }

    public ArrayList<Student> getStudentsFromLecture(int lectureId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT id_students FROM students.students_lectures WHERE id_lectures=" + lectureId);
            ArrayList<Student> lectureList = new ArrayList<>();
            while (resultSet.next()) {
                int studentID = resultSet.getInt(1);
                lectureList.add(getStudent(studentID, connectionString));
            }
            return lectureList;
        }
    }


    public Course getCourseFromLecture(int lectureId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT course_id FROM students.lectures WHERE id=" + lectureId);
            resultSet.next();

            return getCourse(resultSet.getInt(1), connectionString);
        }
    }

    public static int getFreeLectureFromCourse(int courseId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT id, max_num_of_students, num_of_students FROM students.lectures WHERE course_id=" + courseId);
            while (resultSet.next()) {
                if (resultSet.getInt(2) > resultSet.getInt(3)) return resultSet.getInt(1);
            }
        }
        return -1;
    }

    public static void incrementLectureCapacity(int lectureId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {
            String query = "UPDATE students.lectures SET num_of_students=num_of_students+1 WHERE id=" + lectureId;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }


}
