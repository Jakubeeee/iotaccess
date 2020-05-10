package com.jakubeeee.iotaccess.meteoplugin.impl.converter.txt;

import com.jakubeeee.iotaccess.pluginapi.converter.DataConverter;
import com.jakubeeee.iotaccess.pluginapi.converter.DataFormat;
import com.jakubeeee.iotaccess.pluginapi.converter.ExternalDataParseException;
import com.jakubeeee.iotaccess.pluginapi.property.FetchedContainer;
import com.jakubeeee.iotaccess.pluginapi.property.FetchedProperty;
import com.jakubeeee.iotaccess.pluginapi.property.FetchedVector;
import lombok.NonNull;

import java.util.List;
import java.util.Map;

import static com.jakubeeee.iotaccess.meteoplugin.impl.converter.MeteoConverterConstants.TXT_CONVERTER_IDENTIFIER;
import static com.jakubeeee.iotaccess.meteoplugin.impl.converter.MeteoConverterHelper.*;
import static com.jakubeeee.iotaccess.meteoplugin.impl.converter.txt.MeteoTxtConverterConstants.*;
import static com.jakubeeee.iotaccess.pluginapi.meteo.MeteoPropertyKeyConstants.*;
import static java.text.MessageFormat.format;
import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toUnmodifiableList;
import static java.util.stream.Collectors.toUnmodifiableMap;

public class MeteoTxtConverter implements DataConverter {

    private static final MeteoTxtConverter INSTANCE = new MeteoTxtConverter();

    private MeteoTxtConverter() {
    }

    @Override
    public FetchedContainer convert(@NonNull String rawData, @NonNull DataFormat dataFormat) {
        if (isRawDataEmpty(rawData))
            return FetchedContainer.of(emptyList());

        String[] fragments = splitOnNewLines(rawData);
        List<FetchedVector> vectors = createVectors(fragments);
        return FetchedContainer.of(vectors);
    }

    private boolean isRawDataEmpty(String rawData) {
        return rawData.trim().equals("");
    }

    private String[] splitOnNewLines(String rawData) {
        return rawData.split("\n\\s*\n");
    }

    private List<FetchedVector> createVectors(String[] fragments) {
        return stream(fragments)
                .map(this::createVector)
                .collect(toUnmodifiableList());
    }

    private FetchedVector createVector(String fragment) {
        List<String> propertyLines = extractPropertyLines(fragment);
        validateAllPropertyLinesCorrect(propertyLines);
        Map<String, String> rawProperties = extractProperties(propertyLines);
        List<FetchedProperty<?>> properties = transformToFetchedProperties(rawProperties);
        return FetchedVector.of(properties);
    }

    private List<String> extractPropertyLines(String fragment) {
        return fragment.lines()
                .filter(line -> !line.trim().equals(""))
                .filter(line -> !line.trim().equals("Current meteo conditions:"))
                .collect(toUnmodifiableList());
    }

    private void validateAllPropertyLinesCorrect(List<String> propertyLines) {
        boolean allLinesCorrect = propertyLines.stream()
                .allMatch(line -> line.contains("="));
        if (!allLinesCorrect)
            throw new ExternalDataParseException(
                    format("One of given property lines is not correct: \"{0}\"", propertyLines));
    }

    private Map<String, String> extractProperties(List<String> propertyLines) {
        return propertyLines.stream()
                .collect(toUnmodifiableMap(
                        line -> line.substring(0, line.indexOf('=')).trim(),
                        line -> line.substring(line.indexOf('=') + 1).trim())
                );
    }

    private List<FetchedProperty<?>> transformToFetchedProperties(Map<String, String> rawProperties) {
        return List.of(
                createFetchedText(IDENTIFIER, rawProperties.get(ID_KEY)),
                createFetchedNumber(TEMPERATURE, rawProperties.get(TEMPERATURE_KEY)),
                createFetchedNumber(HUMIDITY, rawProperties.get(HUMIDITY_KEY)),
                createFetchedNumber(PRESSURE, rawProperties.get(PRESSURE_KEY)),
                createFetchedNumber(LUMINANCE, rawProperties.get(LUMINANCE_KEY)),
                createFetchedNumber(RAIN_DIGITAL, rawProperties.get(RAIN_DIGITAL_KEY)),
                createFetchedNumber(RAIN_ANALOG, rawProperties.get(RAIN_ANALOG_KEY)),
                createFetchedNumber(WIND_POWER, rawProperties.get(WIND_POWER_KEY)),
                createFetchedText(WIND_DIRECTION, rawProperties.get(WIND_DIRECTION_KEY)),
                createFetchedNumber(GPS_ALTITUDE, rawProperties.get(GPS_ALTITUDE_KEY)),
                createFetchedNumber(GPS_LONGITUDE, rawProperties.get(GPS_LONGITUDE_KEY)),
                createFetchedNumber(GPS_LATITUDE, rawProperties.get(GPS_LATITUDE_KEY)),
                createFetchedDate(MOMENT, rawProperties.get(MOMENT_KEY))
        );
    }

    @Override
    public String getIdentifier() {
        return TXT_CONVERTER_IDENTIFIER;
    }

    public static MeteoTxtConverter getInstance() {
        return INSTANCE;
    }

}
