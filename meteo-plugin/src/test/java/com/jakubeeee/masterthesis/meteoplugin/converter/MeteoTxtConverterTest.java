package com.jakubeeee.masterthesis.meteoplugin.converter;

import com.jakubeeee.masterthesis.pluginapi.converter.ExternalDataParseException;
import com.jakubeeee.masterthesis.pluginapi.property.FetchedContainer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.jakubeeee.masterthesis.meteoplugin.converter.CorrectFetchedContainerData.*;
import static com.jakubeeee.masterthesis.meteoplugin.converter.CorrectTestTxtFetchedData.*;
import static com.jakubeeee.masterthesis.meteoplugin.converter.IncorrectTestTxtFetchedData.*;
import static com.jakubeeee.masterthesis.pluginapi.converter.DataFormat.TXT;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
class MeteoTxtConverterTest {

    private final MeteoTxtConverter converter = MeteoTxtConverter.getInstance();

    @ParameterizedTest
    @MethodSource("convertTestParams_correctData")
    void convertTest_correctData_shouldPass(String rawData, FetchedContainer expectedResult) {
        LOG.info("Converting correct data \"{}\" into fetched container: \"{}\"", rawData, expectedResult);
        FetchedContainer resultContainer = converter.convert(rawData, TXT);
        assertThat(resultContainer, equalTo(expectedResult));
    }

    @ParameterizedTest
    @MethodSource("convertTestParams_incorrectData")
    void convertTest_correctData_shouldThrowException(String rawData) {
        LOG.info("Converting incorrect data \"{}\"", rawData);
        assertThrows(ExternalDataParseException.class, () -> converter.convert(rawData, TXT));
    }

    private static Object[][] convertTestParams_correctData() {
        return new Object[][]{
                {CORRECT_NO_VALUES_TXT, EMPTY_CONTAINER},
                {CORRECT_SINGLE_FRAGMENT_SINGLE_VALUE_TXT, CORRECT_SINGLE_FRAGMENT_SINGLE_VALUE_CONTAINER},
                {CORRECT_SINGLE_FRAGMENT_ALL_VALUES_TXT, CORRECT_SINGLE_FRAGMENT_ALL_VALUES_CONTAINER},
                {CORRECT_MULTIPLE_FRAGMENT_SINGLE_VALUE_TXT, CORRECT_MULTIPLE_FRAGMENT_SINGLE_VALUE_CONTAINER},
                {CORRECT_MULTIPLE_FRAGMENT_ALL_VALUES_TXT, CORRECT_MULTIPLE_FRAGMENT_ALL_VALUES_CONTAINER}
        };
    }

    private static Object[] convertTestParams_incorrectData() {
        return new Object[]{
                INCORRECT_SINGLE_VALUE_MALFORMED_TXT,
                INCORRECT_MULTIPLE_VALUE_MALFORMED_TXT
        };
    }

}
