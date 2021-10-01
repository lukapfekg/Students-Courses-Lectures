package com.example.Job.Service;

import com.example.Job.Jobs.CalculateAverageGrade;
import com.example.Job.Jobs.GetCryptoValue;
import com.example.Job.Util.TimerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void runGetCryptoValue() {
        final TimerInfo info = new TimerInfo();
        info.setRunForever(true);
        info.setRepeatIntervalMs(10000);
        info.setInitialOffsetMs(1000);
        info.setCallbackData("My callback data");
        scheduler.schedule(GetCryptoValue.class, info);
    }

}
