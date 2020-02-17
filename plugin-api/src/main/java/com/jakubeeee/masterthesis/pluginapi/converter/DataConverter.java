package com.jakubeeee.masterthesis.pluginapi.converter;

import com.jakubeeee.masterthesis.pluginapi.property.FetchedContainer;
import lombok.NonNull;

/**
 * TODO DESCRIBE THIS: this class ia a target for asynchronous processing, therefore it should be thread safe. Since
 * this class is meant to just transform raw string to object form, there is really no need for any state. Highly
 * recommended approach is to make the implementation a stateless singleton. Other approaches are: new stateful objects
 * created each time in the plugin connector or stateful singleton with synchronized access to its state. Making it a
 * stateful non-synchronized singleton may result in non-deterministic behaviour when multiple threads are processing
 * data in the same time.
 */
public interface DataConverter {

    FetchedContainer convert(@NonNull String rawData, @NonNull DataFormat dataFormat);

}
