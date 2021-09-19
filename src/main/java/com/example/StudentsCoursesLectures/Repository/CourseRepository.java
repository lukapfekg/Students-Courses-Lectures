package com.example.StudentsCoursesLectures.Repository;

import com.example.StudentsCoursesLectures.Model.Course;
import com.example.StudentsCoursesLectures.Model.Lecture;
import com.example.StudentsCoursesLectures.Model.Student;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

import static com.example.StudentsCoursesLectures.Repository.LectureRepository.getLecture;
import static com.example.StudentsCoursesLectures.Repository.StudentRepository.getStudent;

@Repository
public class CourseRepository {

    private static final String connectionString = "jdbc:postgresql://localhost:5432/studentSystem";

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

    public void addNewCourse(Course course) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root")) {

            String query = "INSERT INTO students.courses VALUES (DEFAULT, '" + course.getCourseName() + "', " + course.getMaxNumberOfStudents() + ", " + course.getNumberOfStudents() + ")";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();

            course.setId(getCourseId(course.getCourseName()));
            createLecturesForCourse(course);
        }
    }

    private int getCourseId(String courseName) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT id FROM students.courses WHERE course_name='" + courseName + "'");
            resultSet.next();
            return resultSet.getInt(1);
        }
    }

    private void createLecturesForCourse(Course course) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root")) {
            int maxNumOfStudents = course.getMaxNumberOfStudents() / 3;

            for (int i = 1; i < 4; i++) {
                String query = "INSERT INTO students.lectures VALUES (DEFAULT, '" + course.getCourseName() + " L" + i + "', " + maxNumOfStudents + ", " + 0 + ", " + course.getID() + ")";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.execute();
            }
        }
    }

    public boolean doesCourseExist(Course course) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {

            final ResultSet resultSet = statement.executeQuery("SELECT * FROM students.courses WHERE course_name='"
                    + course.getCourseName() + "'");
            return resultSet.next();
        }
    }

    public Course getCourseAtIndex(int courseID) throws SQLException {
        return getCourse(courseID);
    }

    public static Course getCourse(int courseID) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
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

    public ArrayList<Student> getAllStudentsFromCourse(int courseId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT id_students FROM students.students_courses WHERE id_courses=" + courseId);

            ArrayList<Student> studentList = new ArrayList<>();
            while (resultSet.next()) {
                int studentID = resultSet.getInt(1);
                studentList.add(getStudentAtIndex(studentID));
            }

            return studentList;
        }
    }

    private Student getStudentAtIndex(int studentID) throws SQLException {
        return getStudent(studentID, connectionString);
    }

    public ArrayList<Lecture> getLecturesFromCourse(int courseId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT id FROM students.lectures WHERE course_id=" + courseId);
            ArrayList<Lecture> lectures = new ArrayList<>();
            while (resultSet.next()) {
                int lectureId = resultSet.getInt(1);
                lectures.add(getLecture(lectureId));
            }
            return lectures;
        }
    }

    public static void incrementCourseCapacity(int courseId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root")) {

            String query = "UPDATE students.courses SET num_of_students=num_of_students+1 WHERE id=" + courseId;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }

    public static void decrementCourseCapacity(int courseId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root")) {

            String query = "UPDATE students.courses SET num_of_students=num_of_students-1 WHERE id=" + courseId;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }

    public static boolean isCourseFull(int courseID) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT max_num_of_students, num_of_students FROM students.courses WHERE id=" + courseID);
            resultSet.next();

            int maxNumOfStudents = resultSet.getInt(1);
            int numOfStudents = resultSet.getInt(2);
            return maxNumOfStudents == numOfStudents;
        }
    }

    public void deleteCourse(int courseId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root")) {

            String query = "DELETE FROM students.courses WHERE id=" + courseId;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();

            deleteStudentCourseConnection(courseId);
        }
    }

    public void deleteStudentCourseConnection(int courseId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root")) {

            String query = "DELETE FROM students.students_courses WHERE id_courses=" + courseId;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }

}
