package com.jakubeeee.masterthesis.pluginapi.config;

import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * Configuration value object used for specifying the way that the fetching process shall be scheduled.
 */
@RequiredArgsConstructor(staticName = "of")
@Value
public class ScheduleConfig {

    private final long interval;

    private final long initialDelay;

}
