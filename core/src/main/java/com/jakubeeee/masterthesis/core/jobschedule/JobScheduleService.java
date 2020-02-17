package com.jakubeeee.masterthesis.core.jobschedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Slf4j
@Service
public class JobScheduleService {

    public void scheduleContinuingAsyncJob(ScheduledJob job, long interval, long initialDelay) {
        job = new ContinuingScheduledJobDecorator(job);
        TimerTask task = constructTimerTask(job);
        validateScheduleJobParameters(interval, initialDelay);
        ScheduledExecutorService executor = newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(task, initialDelay, interval, MILLISECONDS);
    }

    private TimerTask constructTimerTask(ScheduledJob job) {
        return new TimerTask() {
            @Override
            public void run() {
                job.run();
            }
        };
    }

    private void validateScheduleJobParameters(long interval, long initialDelay) {
        validateInterval(interval);
        validateInitialDelay(initialDelay);
    }

    private void validateInterval(long interval) {
        if (interval < 0)
            throw new JobScheduleException("Job schedule interval cannot be a negative value: \"{0}\"", interval);
    }

    private void validateInitialDelay(long initialDelay) {
        if (initialDelay < 0)
            throw new JobScheduleException("Job schedule initial delay cannot be a negative value: \"{0}\"",
                    initialDelay);
    }

}
