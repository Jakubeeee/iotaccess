package com.jakubeeee.iotaccess.core.jobschedule;

import lombok.NonNull;

public record ScheduledTaskConfig(@NonNull String taskIdentifier, @NonNull String groupIdentifier, long interval) {
}
