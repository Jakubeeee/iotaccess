package com.jakubeeee.masterthesis.meteoplugin.impl.converter.xml;

import com.jakubeeee.masterthesis.pluginapi.converter.ExternalDataParseException;
import com.jakubeeee.masterthesis.pluginapi.property.FetchedContainer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.jakubeeee.masterthesis.meteoplugin.impl.converter.CorrectFetchedContainerData.*;
import static com.jakubeeee.masterthesis.meteoplugin.impl.converter.xml.CorrectTestXmlFetchedData.*;
import static com.jakubeeee.masterthesis.meteoplugin.impl.converter.xml.IncorrectTestXmlFetchedData.INCORRECT_MULTIPLE_VALUE_MALFORMED_XML;
import static com.jakubeeee.masterthesis.meteoplugin.impl.converter.xml.IncorrectTestXmlFetchedData.INCORRECT_SINGLE_VALUE_MALFORMED_XML;
import static com.jakubeeee.masterthesis.pluginapi.converter.DataFormat.XML;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
class MeteoXmlConverterTest {

    private final MeteoXmlConverter converter = MeteoXmlConverter.getInstance();

    @ParameterizedTest
    @MethodSource("convertTestParams_correctData")
    void convertTest_correctData_shouldPass(String rawData, FetchedContainer expectedResult) {
        LOG.debug("Converting correct data \"{}\" into fetched container: \"{}\"", rawData, expectedResult);
        FetchedContainer resultContainer = converter.convert(rawData, XML);
        assertThat(resultContainer, equalTo(expectedResult));
    }

    @ParameterizedTest
    @MethodSource("convertTestParams_incorrectData")
    void convertTest_correctData_shouldThrowException(String rawData) {
        LOG.debug("Converting incorrect data \"{}\"", rawData);
        assertThrows(ExternalDataParseException.class, () -> converter.convert(rawData, XML));
    }

    private static Object[][] convertTestParams_correctData() {
        return new Object[][]{
                {CORRECT_NO_VALUES_XML, EMPTY_CONTAINER},
                {CORRECT_SINGLE_FRAGMENT_SINGLE_VALUE_XML, CORRECT_SINGLE_FRAGMENT_SINGLE_VALUE_CONTAINER},
                {CORRECT_SINGLE_FRAGMENT_ALL_VALUES_XML, CORRECT_SINGLE_FRAGMENT_ALL_VALUES_CONTAINER},
                {CORRECT_MULTIPLE_FRAGMENT_SINGLE_VALUE_XML, CORRECT_MULTIPLE_FRAGMENT_SINGLE_VALUE_CONTAINER},
                {CORRECT_MULTIPLE_FRAGMENT_ALL_VALUES_XML, CORRECT_MULTIPLE_FRAGMENT_ALL_VALUES_CONTAINER}
        };
    }

    private static Object[] convertTestParams_incorrectData() {
        return new Object[]{
                INCORRECT_SINGLE_VALUE_MALFORMED_XML,
                INCORRECT_MULTIPLE_VALUE_MALFORMED_XML
        };
    }

}
