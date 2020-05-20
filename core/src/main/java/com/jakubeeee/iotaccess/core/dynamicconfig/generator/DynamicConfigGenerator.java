package com.jakubeeee.iotaccess.core.dynamicconfig.generator;

public interface DynamicConfigGenerator {

    void generate();

    String getIdentifier();

    long getInterval();

}
