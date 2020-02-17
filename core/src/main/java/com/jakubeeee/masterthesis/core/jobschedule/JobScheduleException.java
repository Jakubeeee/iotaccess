package com.jakubeeee.masterthesis.core.jobschedule;

import com.jakubeeee.masterthesis.core.misc.LoggingRuntimeException;

class JobScheduleException extends LoggingRuntimeException {

    JobScheduleException(String message, Object... parameters) {
        super(message, parameters);
    }

}
