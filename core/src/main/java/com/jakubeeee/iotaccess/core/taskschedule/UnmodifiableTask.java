package com.jakubeeee.iotaccess.core.taskschedule;

@FunctionalInterface
public non-sealed interface UnmodifiableTask extends ScheduledTask {

    void execute();

}
