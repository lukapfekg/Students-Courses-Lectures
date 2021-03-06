package com.example.Job.Service;

import com.example.Job.Jobs.CalculateAverageGrade;
import com.example.Job.Jobs.GetCryptoValue;
import com.example.Job.Util.TimerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {
    private static SchedulerService scheduler;

    @Autowired
    public JobService(final SchedulerService scheduler) {
        JobService.scheduler = scheduler;
    }

    public static void runAverageGradeJob() {
        final TimerInfo info = new TimerInfo();
        info.setRunForever(true);
        info.setRepeatIntervalMs(10000);
        info.setInitialOffsetMs(1000);
        info.setCallbackData("My callback data");
        scheduler.schedule(CalculateAverageGrade.class, info);
    }

    public static void runGetCryptoValue() {
        final TimerInfo info = new TimerInfo();
        info.setRunForever(true);
        info.setRepeatIntervalMs(10000);
        info.setInitialOffsetMs(1000);
        info.setCallbackData("My callback data");
        scheduler.schedule(GetCryptoValue.class, info);
    }

}
