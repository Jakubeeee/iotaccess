package com.jakubeeee.masterthesis.core.jobschedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class JobScheduleService {

    private final ThreadPoolTaskScheduler scheduler;

    public void schedule(ScheduledJob job, long interval) {
        if (interval == 0)
            scheduler.execute(job);
        else
            scheduler.scheduleAtFixedRate(job, interval);
    }

}
