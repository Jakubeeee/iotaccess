package com.jakubeeee.masterthesis.core.jobschedule;

@FunctionalInterface
public interface ScheduledJob extends Runnable {

    void run();

}
