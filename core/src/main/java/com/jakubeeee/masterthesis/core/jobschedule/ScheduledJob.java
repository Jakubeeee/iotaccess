package com.jakubeeee.masterthesis.core.jobschedule;

/**
 * Represents code that can be scheduled as recurrent job by {@link JobScheduleService}.
 */
@FunctionalInterface
public interface ScheduledJob {

    void run();

}
