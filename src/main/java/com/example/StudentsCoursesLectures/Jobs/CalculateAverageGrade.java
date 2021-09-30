package com.example.StudentsCoursesLectures.Jobs;

import com.example.StudentsCoursesLectures.Model.Student;
import com.example.StudentsCoursesLectures.Repository.AverageGradesRepository;
import com.example.StudentsCoursesLectures.Services.StudentService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CalculateAverageGrade implements Job {
    private List<Student> students = null;
    private final List<List<Double>> grades = new ArrayList<>();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {

        try {
            this.initStudents();
            this.getAverageGrades();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.printOut();
    }

    private void initStudents() throws SQLException {
        students = StudentService.getStudents();
        for (Student student : students)
            grades.add(AverageGradesRepository.getAllAverageGradesOfStudent(student.getId()));
    }

    private void getAverageGrades() throws SQLException {
        for (int i = 0; i < students.size(); i++) {
            double grade = StudentService.getAverageGrade(students.get(i).getId());
            grades.get(i).add(grade);
            AverageGradesRepository.addGradeToStudent(students.get(i).getId(), grade);
            if (grades.get(i).size() == 6) deleteGrade(i);
        }
    }

    private void deleteGrade(int i) throws SQLException {
        int id = AverageGradesRepository.getId(students.get(i).getId());
        AverageGradesRepository.deleteGrade(id);
        grades.get(i).remove(0);
    }

    public void printOut() {
        System.out.println("----------------------------------------------");
        for (int i = 0; i < grades.size(); i++) {
            System.out.print("Student ID: " + students.get(i).getId() + " - grades: ");
            for (int j = 0; j < grades.get(i).size(); j++) {
                System.out.print((j == 0 ? "" : ", ") + grades.get(i).get(j));
            }
            System.out.println();
        }
        System.out.println("----------------------------------------------");
    }


}
