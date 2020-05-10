package com.jakubeeee.iotaccess.pluginapi.config;

import com.jakubeeee.iotaccess.pluginapi.converter.DataFormat;
import com.jakubeeee.iotaccess.pluginapi.converter.DataType;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * Configuration value object used for specifying the way that external data is fetched.
 */
@RequiredArgsConstructor(staticName = "of")
@Value
public final class FetchConfig {

    @NonNull
    private final String url;

    @NonNull
    private final DataFormat dataFormat;

    @NonNull
    private final DataType dataType;

}
