package com.jakubeeee.masterthesis.pluginapi.config;

import lombok.NonNull;
import lombok.Value;

import java.util.Set;

import static java.util.Collections.unmodifiableSet;
import static java.util.Set.copyOf;

/**
 * Configuration value object used for specifying the plugin behaviour. Aggregates several other configuration objects.
 */
@Value
public final class PluginConfig {

    private final String identifier;

    private final Set<ProcessConfig> processConfigs;

    private PluginConfig(@NonNull String identifier,
                         @NonNull Set<ProcessConfig> processConfigs) {
        this.identifier = identifier;
        this.processConfigs = copyOf(processConfigs);
    }

    public static PluginConfig of(@NonNull String identifier, @NonNull Set<ProcessConfig> processConfigs) {
        return new PluginConfig(identifier, processConfigs);
    }

    public Set<ProcessConfig> getProcessConfigs() {
        return unmodifiableSet(processConfigs);
    }

}
