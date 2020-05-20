package com.jakubeeee.iotaccess.core.taskschedule;

@FunctionalInterface
public interface ParameterizedTask extends ScheduledTask {

    void execute(TaskParametersContainer properties);

}
