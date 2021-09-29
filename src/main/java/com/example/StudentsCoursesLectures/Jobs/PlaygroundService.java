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

    public void runAverageGradeJob() {
        final TimerInfo info = new TimerInfo();
        info.setRunForever(true);
        info.setRepeatIntervalMs(10000);
        info.setInitialOffsetMs(1000);
        info.setCallbackData("My callback data");
        scheduler.schedule(CalculateAverageGrade.class, info);

    }

}
