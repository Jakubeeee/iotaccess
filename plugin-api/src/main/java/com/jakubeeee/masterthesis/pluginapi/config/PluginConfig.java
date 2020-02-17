package com.jakubeeee.masterthesis.pluginapi.config;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Set;

/**
 * Configuration value object used for specifying the plugin behaviour. Aggregates several other configuration objects.
 */
@RequiredArgsConstructor(staticName = "of")
@Value
public final class PluginConfig {

    private final String identifier;

    private final Set<ProcessConfig> processConfigs;

}
