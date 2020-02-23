package com.jakubeeee.masterthesis.meteoplugin.impl.converter;

import com.jakubeeee.masterthesis.pluginapi.converter.ExternalDataParseException;
import com.jakubeeee.masterthesis.pluginapi.property.*;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class MeteoConverterHelper {

    private static final String MOMENT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final DateTimeFormatter MOMENT_FORMATTER = DateTimeFormatter.ofPattern(MOMENT_FORMAT);

    private static final ZoneId METEO_WEB_SERVICE_ZONE_ID = ZoneId.of("+1");

    private static final String PARSE_EXCEPTION_MESSAGE = "Error during parsing of external data in Meteo Converter.";

    private MeteoConverterHelper() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + " class cannot be initialized");
    }

    public static FetchedText createFetchedText(@NonNull String propertyKey, String text) {
        return text == null
                ? new FetchedNullText(propertyKey)
                : new FetchedText(propertyKey, text);
    }

    public static FetchedNumber createFetchedNumber(@NonNull String propertyKey, String rawNumber) {
        return createFetchedNumber(propertyKey, rawNumber == null ? null : new BigDecimal(rawNumber));
    }

    public static FetchedNumber createFetchedNumber(@NonNull String propertyKey, BigDecimal number) {
        return number == null
                ? new FetchedNullNumber(propertyKey)
                : new FetchedNumber(propertyKey, number);
    }

    public static FetchedDate createFetchedDate(@NonNull String propertyKey, String rawDateTime) {
        Instant instantDateTime = null;
        if (rawDateTime != null) {
            var localDateTime = LocalDateTime.parse(rawDateTime, MOMENT_FORMATTER);
            var zonedDateTime = localDateTime.atZone(METEO_WEB_SERVICE_ZONE_ID);
            instantDateTime = zonedDateTime.toInstant();
        }
        return createFetchedDate(propertyKey, instantDateTime);
    }

    public static FetchedDate createFetchedDate(@NonNull String propertyKey, Instant dateTime) {
        return dateTime == null
                ? new FetchedNullDate(propertyKey)
                : new FetchedDate(propertyKey, dateTime);
    }

    public static ExternalDataParseException getParseException(@NonNull Throwable cause) {
        return new ExternalDataParseException(PARSE_EXCEPTION_MESSAGE + " Details in underlying exception message.",
                cause);
    }

}
