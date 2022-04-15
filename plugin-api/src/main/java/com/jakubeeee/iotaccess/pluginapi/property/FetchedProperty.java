package com.jakubeeee.iotaccess.pluginapi.property;

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
public sealed abstract class FetchedProperty<T> permits FetchedDate, FetchedNumber, FetchedText {

    @NonNull
    private final String key;

    private final T value;

}
