package com.jakubeeee.iotaccess.core.taskschedule;

import com.jakubeeee.iotaccess.core.misc.LoggingRuntimeException;
import lombok.NonNull;

public class ScheduledTaskOperationException extends LoggingRuntimeException {

    public ScheduledTaskOperationException(@NonNull String message,
                                           @NonNull Throwable throwable,
                                           @NonNull Object... parameters) {
        super(message, throwable, parameters);
    }

}
