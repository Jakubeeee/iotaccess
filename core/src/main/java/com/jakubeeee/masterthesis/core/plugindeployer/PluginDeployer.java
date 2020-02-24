package com.jakubeeee.masterthesis.core.plugindeployer;

public interface PluginDeployer {

    void deploy();

    String getIdentifier();

    RegistrationStrategy getAssociatedStrategy();

    long getInterval();

    long getInitialDelay();

}
