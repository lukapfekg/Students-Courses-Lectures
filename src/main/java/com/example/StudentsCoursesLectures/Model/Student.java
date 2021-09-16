package com.example.StudentsCoursesLectures.Model;

public class Student {
    private int id;
    private String FirstName;
    private String LastName;
    private String YearEntered;

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
