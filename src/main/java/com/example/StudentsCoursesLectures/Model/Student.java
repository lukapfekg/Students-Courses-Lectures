package com.example.StudentsCoursesLectures.Model;

public class Student {
    private int id;
    private final String FirstName;
    private final String LastName;
    private final String YearEntered;

    public Student(int id, String firstName, String lastName, String yearEntered) {
        this.id = id;
        FirstName = firstName;
        LastName = lastName;
        YearEntered = yearEntered;
    }

    public Student(String firstName, String lastName, String yearEntered) {
        FirstName = firstName;
        LastName = lastName;
        YearEntered = yearEntered;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getYearEntered() {
        return YearEntered;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", YearEntered=" + YearEntered +
                '}';
    }
}
