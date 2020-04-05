package com.jakubeeee.masterthesis.randomnumberplugindb.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jakubeeee.masterthesis.pluginapi.converter.DataConverter;
import com.jakubeeee.masterthesis.pluginapi.converter.DataFormat;
import com.jakubeeee.masterthesis.pluginapi.property.FetchedContainer;
import com.jakubeeee.masterthesis.pluginapi.property.FetchedNumber;
import com.jakubeeee.masterthesis.pluginapi.property.FetchedProperty;
import com.jakubeeee.masterthesis.pluginapi.property.FetchedVector;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

import static java.text.MessageFormat.format;
import static java.util.stream.Collectors.toUnmodifiableList;

public final class RandomNumberConverter implements DataConverter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final RandomNumberDataValidator VALIDATOR = RandomNumberDataValidator.getInstance();

    private static final RandomNumberConverter INSTANCE = new RandomNumberConverter();

    private RandomNumberConverter() {
    }

    @Override
    public FetchedContainer convert(@NonNull String rawData, @NonNull DataFormat dataFormat) {
        VALIDATOR.validate(rawData, dataFormat);
        RandomNumberContainer container = parseToExternalContainer(rawData);
        List<BigDecimal> values = container.getValues();
        List<FetchedProperty<?>> fetchedNumbers = rawValuesToFetchedNumbers(values);
        return FetchedContainer.of(List.of(FetchedVector.of(fetchedNumbers)));
    }

    private RandomNumberContainer parseToExternalContainer(String rawData) {
        try {
            return MAPPER.readValue(rawData, RandomNumberContainer.class);
        } catch (JsonProcessingException e) {
            throw VALIDATOR.prepareForRethrow(e);
        }
    }

    private List<FetchedProperty<?>> rawValuesToFetchedNumbers(List<BigDecimal> values) {
        return IntStream
                .range(0, values.size())
                .mapToObj(i -> rawValueToFetchedNumber(values.get(i), i))
                .collect(toUnmodifiableList());
    }

    private FetchedNumber rawValueToFetchedNumber(BigDecimal value, int index) {
        return new FetchedNumber(format("random_value_{0}", index + 1), value);
    }

    public static RandomNumberConverter getInstance() {
        return INSTANCE;
    }

}
