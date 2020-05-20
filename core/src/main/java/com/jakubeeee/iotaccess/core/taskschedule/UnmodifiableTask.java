package com.jakubeeee.iotaccess.core.taskschedule;

@FunctionalInterface
public interface UnmodifiableTask extends ScheduledTask {

    void execute();

}
