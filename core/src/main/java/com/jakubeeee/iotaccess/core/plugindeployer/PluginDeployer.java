package com.jakubeeee.iotaccess.core.plugindeployer;

public interface PluginDeployer {

    void deploy();

    String getIdentifier();

    RegistrationStrategy getAssociatedStrategy();

    long getInterval();

}
