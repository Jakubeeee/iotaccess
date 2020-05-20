package com.jakubeeee.iotaccess.core.taskschedule;

import lombok.NonNull;

public record ParameterizedTaskContext(@NonNull ScheduledTaskId identifier,
                                       @NonNull ParameterizedTask task,
                                       @NonNull TaskParametersContainer taskProperties,
                                       long interval)
        implements TaskContext<ParameterizedTask> {
}
