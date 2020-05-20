package com.jakubeeee.iotaccess.core.taskschedule;

import lombok.NonNull;

public record UnmodifiableTaskContext(@NonNull ScheduledTaskId identifier,
                                      @NonNull UnmodifiableTask task,
                                      long interval)
        implements TaskContext<UnmodifiableTask> {
}
