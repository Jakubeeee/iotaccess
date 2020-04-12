package com.jakubeeee.iotaccess.core.misc;

import lombok.extern.slf4j.Slf4j;

import static java.text.MessageFormat.format;

@Slf4j
public class LoggingRuntimeException extends RuntimeException {

    public LoggingRuntimeException(String message, Object... parameters) {
        super(format(message, parameters));
        LOG.error(format(message, parameters));
    }

}
