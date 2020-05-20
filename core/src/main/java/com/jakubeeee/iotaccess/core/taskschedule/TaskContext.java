package com.jakubeeee.iotaccess.core.taskschedule;

public interface TaskContext<T extends ScheduledTask> {

    ScheduledTaskId identifier();

    T task();

    long interval();

}
