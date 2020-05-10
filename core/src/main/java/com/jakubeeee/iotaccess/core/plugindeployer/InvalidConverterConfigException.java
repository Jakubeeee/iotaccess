package com.jakubeeee.iotaccess.core.plugindeployer;

import com.jakubeeee.iotaccess.core.misc.LoggingRuntimeException;

public class InvalidConverterConfigException extends LoggingRuntimeException {

    InvalidConverterConfigException(String message, Object... parameters) {
        super(message, parameters);
    }

}
