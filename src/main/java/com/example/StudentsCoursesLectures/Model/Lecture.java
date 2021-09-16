package com.example.StudentsCoursesLectures.Model;

public class Lecture  {
    private int id;
    private String lectureName;
    private int maxNumOfStudents;
    private int numOfStudents;

    public Lecture(int id, String lectureName, int maxNumOfStudents, int numOfStudents) {
        this.id = id;
        this.lectureName = lectureName;
        this.maxNumOfStudents = maxNumOfStudents;
        this.numOfStudents = numOfStudents;
    }

    public Lecture(String lectureName, int maxNumOfStudents, int numOfStudents) {
        this.lectureName = lectureName;
        this.maxNumOfStudents = maxNumOfStudents;
        this.numOfStudents = numOfStudents;
    }

    public int getId() {
        return id;
    }

    public String getLectureName() {
        return lectureName;
    }

    public int getMaxNumOfStudents() {
        return maxNumOfStudents;
    }

    public int getNumOfStudents() {
        return numOfStudents;
    }

    @Override
    public String toString() {
        return "Lecture{" +
                "id=" + id +
                ", lectureName='" + lectureName + '\'' +
                ", maxNumOfStudents=" + maxNumOfStudents +
                ", numOfStudents=" + numOfStudents +
                '}';
    }
}

