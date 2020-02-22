package com.jakubeeee.masterthesis.meteoplugin.converter;

import com.jakubeeee.masterthesis.pluginapi.property.*;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.Instant;

final class FetchedPropertyHelper {

    private FetchedPropertyHelper() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + " class cannot be initialized");
    }

    static FetchedText createFetchedText(@NonNull String propertyKey, String text) {
        return text == null
                ? new FetchedNullText(propertyKey)
                : new FetchedText(propertyKey, text);
    }

    static FetchedNumber createFetchedNumber(@NonNull String propertyKey, BigDecimal number) {
        return number == null
                ? new FetchedNullNumber(propertyKey)
                : new FetchedNumber(propertyKey, number);
    }

    static FetchedDate createFetchedDate(@NonNull String propertyKey, Instant date) {
        return date == null
                ? new FetchedNullDate(propertyKey)
                : new FetchedDate(propertyKey, date);
    }

}
