package com.jakubeeee.masterthesis.pluginapi.property;

import com.jakubeeee.masterthesis.pluginapi.converter.DataConverter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

import static java.util.Collections.unmodifiableList;
import static java.util.List.copyOf;

/**
 * Internal container specifying a common format for incoming data. {@link DataConverter Converters} are used for
 * transforming fetched external data into instances of this class.
 */
@Value
public final class FetchedContainer {

    private final List<FetchedVector> fetchedVectors;

    private FetchedContainer(List<FetchedVector> fetchedVectors) {
        this.fetchedVectors = copyOf(fetchedVectors);
    }

    public static FetchedContainer of(List<FetchedVector> fetchedVectors) {
        return new FetchedContainer(fetchedVectors);
    }

    public List<FetchedVector> getFetchedVectors() {
        return unmodifiableList(fetchedVectors);
    }

}

