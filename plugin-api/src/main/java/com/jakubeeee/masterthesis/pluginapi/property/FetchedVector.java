package com.jakubeeee.masterthesis.pluginapi.property;

import lombok.Value;

import java.util.List;

import static java.util.Collections.unmodifiableList;
import static java.util.List.copyOf;

@Value
public final class FetchedVector {

    private final List<FetchedProperty> fetchedProperties;

    private FetchedVector(List<FetchedProperty> fetchedProperties) {
        this.fetchedProperties = copyOf(fetchedProperties);
    }

    public static FetchedVector of(List<FetchedProperty> fetchedProperties) {
        return new FetchedVector(fetchedProperties);
    }

    public List<FetchedProperty> getFetchedProperties() {
        return unmodifiableList(fetchedProperties);
    }

}
