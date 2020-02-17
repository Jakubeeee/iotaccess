package com.jakubeeee.masterthesis.core.misc;

public class NotInitializableClassException extends LoggingRuntimeException {

    public NotInitializableClassException(Class<?> notInitializableClass) {
        super("\"{0}\" class cannot be initialized", notInitializableClass.getSimpleName());
    }

}
