package com.jakubeeee.iotaccess.core.taskschedule;

public interface TaskParameter<T> {

    String groupName();

    String name();

    T value();

}
