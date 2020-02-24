package com.jakubeeee.masterthesis.pluginapi.property;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@RequiredArgsConstructor(staticName = "of")
@Value
public final class FetchedVector {

    private final List<? extends FetchedProperty> fetchedProperties;

}
