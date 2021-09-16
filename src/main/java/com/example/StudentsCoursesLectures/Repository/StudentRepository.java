package com.example.StudentsCoursesLectures.Repository;

import com.example.StudentsCoursesLectures.Model.Course;
import com.example.StudentsCoursesLectures.Model.Student;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {

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

    public void addNewStudent(String firstName, String lastName, String yearEntered) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {

            String query = "INSERT INTO students.students VALUES (DEFAULT, '" + firstName + "', '" + lastName + "', '" + yearEntered + "')";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }

    public void addStudentToClass(Student student, Course course) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {

            String query = "INSERT INTO students.students_courses VALUES (" + student.getId() + ", " + course.getID() + ", " + "3)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();

            /*incrementCourseCapacity(course.getID());
            course.incrementNumOfStudents();

            addStudentToLecture(student, course);*/
        }
    }

    public boolean isStudentInCourse(Student student, Course course) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM students.students_courses WHERE id_students="
                    + student.getId() + " AND id_courses=" + course.getID());
            return resultSet.next();
        }
    }

    public ArrayList<Course> getAllCoursesFromStudent(Student student) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, "postgres", "root");
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT id_courses FROM students.students_courses WHERE id_students=" + student.getId());
            ArrayList<Course> courseList = new ArrayList<>();
            while (resultSet.next()) {
                int courseID = resultSet.getInt(1);
                courseList.add(getCourseAtIndex(courseID));
            }

            return courseList;
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
                grades.add(resultSet.getInt(1));

            return grades;
        }
    }


}

