package com.jakubeeee.iotaccess.core.taskschedule;

import lombok.NonNull;

public record TaskDetails(@NonNull ScheduledTaskId identifier, boolean running, long interval,
                          @NonNull TaskParametersContainer parameterContainer) {
}