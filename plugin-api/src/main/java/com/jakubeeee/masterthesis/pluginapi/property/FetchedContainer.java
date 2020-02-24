package com.jakubeeee.masterthesis.pluginapi.property;

import com.jakubeeee.masterthesis.pluginapi.converter.DataConverter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

/**
 * Internal container specifying a common format for incoming data. {@link DataConverter Converters} are used for
 * transforming fetched external data into instances of this class.
 */
@RequiredArgsConstructor(staticName = "of")
@Value
public final class FetchedContainer {

    private final List<FetchedVector> fetchedVectors;

}
