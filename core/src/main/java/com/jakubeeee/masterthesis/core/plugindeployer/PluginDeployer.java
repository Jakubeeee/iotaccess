package com.jakubeeee.masterthesis.core.plugindeployer;

/**
 * Component responsible for finding and deploying applicable plugins.
 */
public interface PluginDeployer {

    void deploy();

    String getIdentifier();

    RegistrationStrategy getAssociatedStrategy();

    long getInterval();

    long getInitialDelay();

}
