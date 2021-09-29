package com.example.StudentsCoursesLectures.Jobs;

import com.example.StudentsCoursesLectures.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class PlaygroundService {
    private final SchedulerService scheduler;

    @Autowired
    public PlaygroundService(final SchedulerService scheduler) {
        this.scheduler = scheduler;
    }

    public void runAverageGradeJob() throws SQLException {
        final TimerInfo info = new TimerInfo();
        info.setTotalFireCount(5);
        info.setRepeatIntervalMs(2000);
        info.setInitialOffsetMs(1000);
        info.setCallbackData("My callback data");
        scheduler.schedule(CalculateAverageGrade.class, info);

    }

}
