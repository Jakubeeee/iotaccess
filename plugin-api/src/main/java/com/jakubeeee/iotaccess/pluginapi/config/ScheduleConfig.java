package com.jakubeeee.iotaccess.pluginapi.config;

import lombok.Value;

/**
 * Configuration value object used for specifying the way that the fetching process is scheduled.
 */
@Value
public final class ScheduleConfig {

    private final long interval;

    private final boolean running;

    public static ScheduleConfig of(long interval, boolean running) {
        return new ScheduleConfig(interval, running);
    }

    public static ScheduleConfig of(long interval) {
        return new ScheduleConfig(interval, true);
    }

}
