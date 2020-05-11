package com.jakubeeee.iotaccess.core.taskschedule;

import lombok.NonNull;

public record ScheduledTaskConfig(@NonNull String taskIdentifier, @NonNull String groupIdentifier, long interval) {
}
