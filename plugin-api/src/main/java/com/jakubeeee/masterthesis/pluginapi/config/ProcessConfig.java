package com.jakubeeee.masterthesis.pluginapi.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * Configuration value object used for specifying a single process of fetching external data.
 */
@RequiredArgsConstructor(staticName = "of")
@Value
public class ProcessConfig {

    @NonNull
    private final String identifier;

    @NonNull
    private final String description;

    @NonNull
    private final FetchConfig fetchConfig;

    @NonNull
    private final ScheduleConfig scheduleConfig;

}
