package com.jakubeeee.iotaccess.core.taskschedule;

public sealed interface TaskContext<T extends ScheduledTask> permits ParameterizedTaskContext, UnmodifiableTaskContext {

    ScheduledTaskId identifier();

    T task();

    long interval();

}
