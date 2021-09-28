package com.example.StudentsCoursesLectures.Model;


public class DBParameters {
    private static DBParameters instance = null;

    public String getConnectionString() {
        return "jdbc:postgresql://localhost:5432/studentSystem";
    }

    public String getUsername() {
        return "postgres";
    }

    public String getPassword() {
        return "root";
    }

    public static DBParameters getInstance() {
        if (instance == null) {
            instance = new DBParameters();
        }
        return instance;
    }

}
