package com.jakubeeee.iotaccess.pluginapi.converter;

import com.jakubeeee.iotaccess.pluginapi.PluginConnector;
import com.jakubeeee.iotaccess.pluginapi.property.FetchedContainer;
import lombok.NonNull;

/**
 * Plugin component that has a responsibility of converting data fetched from web service into common object form.
 * <p>
 * NOTE: this class ia a target for asynchronous processing, therefore it should be thread safe. Since data converters
 * are meant to just transform raw string to object form, there is really no need for any state. Highly recommended
 * approach is to make the implementation a stateless singleton. Other approaches are: new stateful objects created each
 * time in the {@link PluginConnector} or stateful singleton with synchronized access to its state. Making it a stateful
 * non-synchronized singleton may result in non-deterministic behaviour when multiple threads are processing data in the
 * same time.
 */
public interface DataConverter {

    /**
     * Converts raw data in form of a {@link String} to object in form of {@link FetchedContainer}.
     *
     * @param rawData    the raw data to be converted to object form
     * @param dataFormat the format of the raw data
     * @return the object result of parsing the raw data
     */
    FetchedContainer convert(@NonNull String rawData, @NonNull DataFormat dataFormat);

}
