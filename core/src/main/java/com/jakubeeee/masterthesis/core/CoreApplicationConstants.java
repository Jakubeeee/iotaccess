package com.jakubeeee.masterthesis.core;

import com.jakubeeee.masterthesis.core.misc.NotInitializableClassException;

/**
 * Global constants used by <code>Core</code> standalone module.
 */
public final class CoreApplicationConstants {

    public static final String ROOT_PACKAGE = "com.jakubeeee.masterthesis";

    public static final String CL_ARGUMENT_REGISTRATION_STRATEGIES = "regstrat";

    private CoreApplicationConstants() {
        throw new NotInitializableClassException(this.getClass());
    }

}
