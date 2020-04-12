package com.jakubeeee.iotaccess.core;

import com.jakubeeee.iotaccess.core.misc.NotInitializableClassException;

public final class CoreApplicationConstants {

    public static final String ROOT_PACKAGE = "com.jakubeeee.iotaccess";

    public static final String CL_ARGUMENT_REGISTRATION_STRATEGIES = "regstrat";

    private CoreApplicationConstants() {
        throw new NotInitializableClassException(this.getClass());
    }

}
