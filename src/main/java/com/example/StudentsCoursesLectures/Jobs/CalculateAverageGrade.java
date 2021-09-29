package com.example.StudentsCoursesLectures.Jobs;

import com.example.StudentsCoursesLectures.Model.Student;
import com.example.StudentsCoursesLectures.Services.StudentService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CalculateAverageGrade implements Job {
    private List<Student> students = null;
    private final List<List<Double>> grades = new ArrayList<>();



    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        try {
            if(students == null) this.initStudents();
            this.getAverageGrades();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.printOut();
    }

    private void initStudents() throws SQLException {
        students = StudentService.getStudents();
        for(Student student : students)
            grades.add(new ArrayList<>());
    }

    private void getAverageGrades() throws SQLException {
        for(int i = 0; i < students.size(); i++){
            double grade = StudentService.getAverageGrade(students.get(i).getId());
            grades.get(i).add(grade);
        }

        if(grades.get(0).size() == 6) deleteFirst();
    }

    private void deleteFirst(){
        if (grades.size() > 0) {
            grades.subList(0, grades.size()).remove(0);
        }
    }

    public void printOut(){
        System.out.println("----------------------------------------------");
        for(int i = 0; i < grades.size(); i++){
            System.out.print("Student ID: " + students.get(i).getId() + " - grades: ");
            for(int j = 0; j < grades.get(i).size(); j++){
                System.out.println((j == 0 ? "" : ", ") +  grades.get(i).get(j));
            }
        }
        System.out.println("----------------------------------------------");
    }




}
