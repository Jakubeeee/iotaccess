package com.jakubeeee.masterthesis.pluginapi.property;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@RequiredArgsConstructor(staticName = "of")
@Value
public final class FetchedRecord {

    private final List<? extends FetchedProperty> fetchedProperties;

}
