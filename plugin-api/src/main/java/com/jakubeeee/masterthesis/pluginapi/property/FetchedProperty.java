package com.jakubeeee.masterthesis.pluginapi.property;

import lombok.*;

@EqualsAndHashCode
@ToString
@Getter
@RequiredArgsConstructor
public abstract class FetchedProperty<T> {

    @NonNull
    private final String key;

    private final T value;

}
