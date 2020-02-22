package com.jakubeeee.masterthesis.randomnumberplugin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.jakubeeee.masterthesis.pluginapi.converter.DataConverter;
import com.jakubeeee.masterthesis.pluginapi.converter.DataFormat;
import com.jakubeeee.masterthesis.pluginapi.converter.ExternalDataParseException;
import com.jakubeeee.masterthesis.pluginapi.property.FetchedContainer;
import com.jakubeeee.masterthesis.pluginapi.property.FetchedNumber;
import com.jakubeeee.masterthesis.pluginapi.property.FetchedRecord;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

import static com.jakubeeee.masterthesis.pluginapi.converter.DataFormat.JSON;
import static java.text.MessageFormat.format;
import static java.util.stream.Collectors.toUnmodifiableList;
import static java.util.stream.StreamSupport.stream;

public final class RandomNumberConverter implements DataConverter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String PARSE_EXCEPTION_MESSAGE =
            "Error during parsing of external data in Random Number Converter.";

    private static final RandomNumberConverter INSTANCE = new RandomNumberConverter();

    private RandomNumberConverter() {
    }

    @Override
    public FetchedContainer convert(@NonNull String rawData, @NonNull DataFormat dataFormat) {
        validateProperDataFormat(dataFormat);
        try {
            List<FetchedNumber> values = resolveFetchedNumbers(rawData);
            return FetchedContainer.of(List.of(FetchedRecord.of(values)));
        } catch (JsonProcessingException e) {
            throw getParseException(e);
        }
    }

    private void validateProperDataFormat(DataFormat dataFormat) {
        if (dataFormat != JSON)
            throw new UnsupportedOperationException(
                    format("\"{0}\" supports only json data. Data format \"{1}\" not applicable",
                            this.getClass().getSimpleName(), dataFormat));
    }

    private List<FetchedNumber> resolveFetchedNumbers(String fetchedData) throws JsonProcessingException {
        JsonNode rootNode = MAPPER.readTree(fetchedData);
        JsonNode valuesNode = rootNode.path("values");
        validateValuesNode(valuesNode);
        List<String> values = stream(valuesNode.spliterator(), false)
                .map(JsonNode::asText)
                .collect(toUnmodifiableList());
        return IntStream
                .range(0, values.size())
                .mapToObj(i -> new FetchedNumber(format("random_value_{0}", i + 1), parseBigDecimal(values.get(i))))
                .collect(toUnmodifiableList());
    }

    private void validateValuesNode(JsonNode valuesNode) {
        if (valuesNode instanceof MissingNode)
            throw getParseException("Details: could not find mandatory \"values\" node.");
        if (!(valuesNode instanceof ArrayNode))
            throw getParseException("Details: mandatory \"values\" node must be an array node.");
        if (valuesNode.isEmpty())
            throw getParseException("Details: \"values\" node is empty.");
    }

    private BigDecimal parseBigDecimal(String rawFetchedNumber) {
        try {
            return new BigDecimal(rawFetchedNumber);
        } catch (NumberFormatException e) {
            throw getParseException(
                    "Details: value: \"" + rawFetchedNumber + "\" is not parsable to big decimal.", e);
        }
    }

    private ExternalDataParseException getParseException(String messageExtension) {
        return new ExternalDataParseException(PARSE_EXCEPTION_MESSAGE + " " + messageExtension);
    }

    private ExternalDataParseException getParseException(Throwable cause) {
        return new ExternalDataParseException(PARSE_EXCEPTION_MESSAGE + " Details in underlying exception message.",
                cause);
    }

    private ExternalDataParseException getParseException(String messageExtension, Throwable cause) {
        return new ExternalDataParseException(
                PARSE_EXCEPTION_MESSAGE + " " + messageExtension + " More details in underlying exception message.",
                cause);
    }

    public static RandomNumberConverter getInstance() {
        return INSTANCE;
    }

}
