package com.jakubeeee.iotaccess.core.dynamicconfig.reader;

public interface DynamicConfigReader {

    void read();

    String getIdentifier();

    long getInterval();

}
