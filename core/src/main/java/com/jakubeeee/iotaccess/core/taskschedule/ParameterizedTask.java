package com.jakubeeee.iotaccess.core.taskschedule;

@FunctionalInterface
public non-sealed interface ParameterizedTask extends ScheduledTask {

    void execute(TaskParametersContainer properties);

}
