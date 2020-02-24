package com.jakubeeee.masterthesis.pluginapi.property;

import lombok.*;

/**
 * Basic wrapper for data used as an association of {@link String} key and {@link T} value.
 *
 * @param <T> type of value contained by this wrapper object
 */
@EqualsAndHashCode
@ToString
@Getter
@RequiredArgsConstructor
public abstract class FetchedProperty<T> {

    @NonNull
    private final String key;

    private final T value;

}
