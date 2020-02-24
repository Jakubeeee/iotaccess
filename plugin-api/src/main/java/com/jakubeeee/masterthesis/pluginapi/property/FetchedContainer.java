package com.jakubeeee.masterthesis.pluginapi.property;

import com.jakubeeee.masterthesis.pluginapi.converter.DataConverter;
import lombok.Value;

import java.util.List;

import static java.util.Collections.unmodifiableList;
import static java.util.List.copyOf;

/**
 * Container specifying a common format for data incoming from web services. Created by {@link DataConverter converters}
 * from raw data. Aggregates multiple {@link FetchedVector vectors}.
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

