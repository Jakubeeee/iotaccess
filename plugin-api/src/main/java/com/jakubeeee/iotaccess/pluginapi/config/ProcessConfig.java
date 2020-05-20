package com.jakubeeee.iotaccess.pluginapi.config;

import lombok.NonNull;
import lombok.Value;

/**
 * Configuration value object used for specifying a single process of fetching external data.
 */
@Value
public final class ProcessConfig {

    private final String identifier;

    private final String description;

    private final FetchConfig fetchConfig;

    private final ConverterConfig converterConfig;

    private final ScheduleConfig scheduleConfig;

    public static ProcessConfig of(@NonNull String identifier,
                                   @NonNull String description,
                                   @NonNull FetchConfig fetchConfig,
                                   @NonNull ConverterConfig converterConfig,
                                   @NonNull ScheduleConfig scheduleConfig) {
        return new ProcessConfig(identifier, description, fetchConfig, converterConfig, scheduleConfig);
    }

    public static ProcessConfig of(@NonNull String identifier,
                                   @NonNull FetchConfig fetchConfig,
                                   @NonNull ConverterConfig converterConfig,
                                   @NonNull ScheduleConfig scheduleConfig) {
        return new ProcessConfig(identifier, "", fetchConfig, converterConfig, scheduleConfig);
    }

}
