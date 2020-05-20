package com.jakubeeee.iotaccess.pluginapi.config;

import com.jakubeeee.iotaccess.pluginapi.converter.DataFormat;
import com.jakubeeee.iotaccess.pluginapi.converter.DataType;
import lombok.NonNull;
import lombok.Value;

import static com.jakubeeee.iotaccess.pluginapi.converter.DataType.STANDARD;

/**
 * Configuration value object used for specifying the way that external data is fetched.
 */
@Value
public final class FetchConfig {

    private final String url;

    private final DataFormat dataFormat;

    private final DataType dataType;

    public static FetchConfig of(@NonNull String url, @NonNull DataFormat dataFormat, @NonNull DataType dataType) {
        return new FetchConfig(url, dataFormat, dataType);
    }

    public static FetchConfig of(@NonNull String url, @NonNull DataFormat dataFormat) {
        return new FetchConfig(url, dataFormat, STANDARD);
    }

}
