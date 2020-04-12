package com.jakubeeee.iotaccess.core.jobschedule;

import com.jakubeeee.iotaccess.core.misc.LoggingRuntimeException;

class JobScheduleException extends LoggingRuntimeException {

    JobScheduleException(String message, Object... parameters) {
        super(message, parameters);
    }

}
