package com.jakubeeee.masterthesis.pluginapi.config;

import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * Configuration value object used for specifying a single process of fetching external data.
 */
@RequiredArgsConstructor(staticName = "of")
@Value
public class ProcessConfig {

    private final String identifier;

    private final String description;

    private final FetchConfig fetchConfig;

    private final ScheduleConfig scheduleConfig;

}
