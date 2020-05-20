package com.jakubeeee.iotaccess.core.taskschedule;

import lombok.NonNull;

public record ScheduledTaskId(@NonNull String taskId, @NonNull String groupId) {
}
