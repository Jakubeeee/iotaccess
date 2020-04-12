package com.jakubeeee.iotaccess.core.jobschedule;

@FunctionalInterface
public interface ScheduledJob extends Runnable {

    void run();

}
