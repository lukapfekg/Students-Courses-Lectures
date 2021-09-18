package com.example.StudentsCoursesLectures.Model;


public class Course {
    private int id;
    private final String courseName;
    private final int maxNumberOfStudents;
    private final int numberOfStudents;

    public Course(int id, String courseName, int maxNumberOfStudents, int numberOfStudents) {
        this.id = id;
        this.courseName = courseName;
        this.maxNumberOfStudents = maxNumberOfStudents;
        this.numberOfStudents = numberOfStudents;
    }

    public Course(String courseName, int maxNumberOfStudents, int numberOfStudents) {
        this.courseName = courseName;
        this.maxNumberOfStudents = maxNumberOfStudents;
        this.numberOfStudents = numberOfStudents;
    }

    public int getID() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getMaxNumberOfStudents() {
        return maxNumberOfStudents;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", maxNumberOfStudents=" + maxNumberOfStudents +
                ", numberOfStudents=" + numberOfStudents +
                '}';
    }
}
