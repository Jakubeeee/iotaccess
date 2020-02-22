package com.jakubeeee.masterthesis.pluginapi.converter;

import com.jakubeeee.masterthesis.pluginapi.PluginConnector;
import com.jakubeeee.masterthesis.pluginapi.property.FetchedContainer;
import lombok.NonNull;

/**
 * Plugin component that has a responsibility of converting data fetched from web service into common object form, which
 * is represented by {@link FetchedContainer}. The pre-converted data is in form of a raw {@link String} object. The
 * {@link #convert} method additionally receives information about {@link DataFormat} in which the raw data is stored,
 * so the converter can use proper conversion strategy.
 * <p>
 * NOTE: this class ia a target for asynchronous processing, therefore it should be thread safe. Since data converters
 * are meant to just transform raw string to object form, there is really no need for any state. Highly recommended
 * approach is to make the implementation a stateless singleton. Other approaches are: new stateful objects created each
 * time in the {@link PluginConnector} or stateful singleton with synchronized access to its state. Making it a stateful
 * non-synchronized singleton may result in non-deterministic behaviour when multiple threads are processing data in the
 * same time.
 */
public interface DataConverter {

    FetchedContainer convert(@NonNull String rawData, @NonNull DataFormat dataFormat);

}
