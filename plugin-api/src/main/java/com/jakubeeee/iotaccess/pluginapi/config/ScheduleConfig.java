package com.jakubeeee.iotaccess.pluginapi.config;

import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * Configuration value object used for specifying the way that the fetching process is scheduled.
 */
@RequiredArgsConstructor(staticName = "of")
@Value
public final class ScheduleConfig {

    private final long interval;

}
