package com.jakubeeee.masterthesis.randomnumberplugindb.impl;

import com.jakubeeee.masterthesis.pluginapi.converter.ExternalDataParseException;
import com.jakubeeee.masterthesis.pluginapi.property.FetchedContainer;
import com.jakubeeee.masterthesis.pluginapi.property.FetchedNumber;
import com.jakubeeee.masterthesis.pluginapi.property.FetchedProperty;
import com.jakubeeee.masterthesis.pluginapi.property.FetchedVector;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

import static com.jakubeeee.masterthesis.pluginapi.converter.DataFormat.JSON;
import static com.jakubeeee.masterthesis.randomnumberplugindb.impl.CorrectTestFetchedData.*;
import static com.jakubeeee.masterthesis.randomnumberplugindb.impl.IncorrectTestFetchedData.*;
import static java.text.MessageFormat.format;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toUnmodifiableList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
class RandomNumberConverterTest {

    private final RandomNumberConverter converter = RandomNumberConverter.getInstance();

    @ParameterizedTest
    @MethodSource("convertTestParams_correctData")
    void convertTest_correctData_shouldPass(String rawData, FetchedContainer expectedResult) {
        LOG.debug("Converting correct data \"{}\" into fetched container: \"{}\"", rawData, expectedResult);
        FetchedContainer resultContainer = converter.convert(rawData, JSON);
        assertThat(resultContainer, equalTo(expectedResult));
    }

    @ParameterizedTest
    @MethodSource("convertTestParams_incorrectData")
    void convertTest_correctData_shouldThrowException(String rawData) {
        LOG.debug("Converting incorrect data \"{}\"", rawData);
        assertThrows(ExternalDataParseException.class, () -> converter.convert(rawData, JSON));
    }

    private static Object[][] convertTestParams_correctData() {
        return new Object[][]{
                {CORRECT_NO_VALUES_JSON, container()},
                {CORRECT_SINGLE_INTEGER_VALUE_JSON, container("1")},
                {CORRECT_SINGLE_DOUBLE2_VALUE_JSON, container("1.23")},
                {CORRECT_SINGLE_DOUBLE8_VALUE_JSON, container("1.23456789")},
                {CORRECT_SINGLE_DOUBLE16_VALUE_JSON, container("1.2345678987654321")},
                {CORRECT_SINGLE_NEGATIVE_INTEGER_VALUE_JSON, container("-1")},
                {CORRECT_SINGLE_NEGATIVE_DOUBLE2_VALUE_JSON, container("-1.23")},
                {CORRECT_SINGLE_NEGATIVE_DOUBLE8_VALUE_JSON, container("-1.23456789")},
                {CORRECT_SINGLE_NEGATIVE_DOUBLE16_VALUE_JSON, container("-1.2345678987654321")},
                {CORRECT_MULTIPLE_VALUE_JSON, container("1.23", "2.34", "-3.45", "4", "5.678987654321")},
        };
    }

    private static Object[] convertTestParams_incorrectData() {
        return new Object[]{
                INCORRECT_MALFORMED_JSON,
                INCORRECT_EMPTY_JSON,
                INCORRECT_SINGLE_NO_NUMERIC_VALUE_JSON,
                INCORRECT_MULTIPLE_NO_NUMERIC_VALUES_JSON,
                INCORRECT_MIXED_NUMERIC_AND_NO_NUMERIC_VALUES_JSON,
        };
    }

    private static FetchedContainer container(String... values) {
        List<FetchedProperty> fetchedNumbers = IntStream
                .range(0, values.length)
                .mapToObj(i -> new FetchedNumber(format("random_value_{0}", i + 1), new BigDecimal(values[i])))
                .collect(toUnmodifiableList());
        return FetchedContainer.of(singletonList(FetchedVector.of(fetchedNumbers)));
    }

}
