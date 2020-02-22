package com.jakubeeee.masterthesis.randomnumberplugin;

import com.jakubeeee.masterthesis.pluginapi.converter.DataFormat;
import com.jakubeeee.masterthesis.pluginapi.converter.ExternalDataParseException;

import static com.jakubeeee.masterthesis.pluginapi.converter.DataFormat.JSON;
import static java.text.MessageFormat.format;

final class RandomNumberDataValidator {

    private static final String PARSE_EXCEPTION_MESSAGE =
            "Error during parsing of external data in Random Number Converter.";

    private static final RandomNumberDataValidator INSTANCE = new RandomNumberDataValidator();

    private RandomNumberDataValidator() {
    }

    void validate(String rawData, DataFormat dataFormat) {
        validateProperDataFormat(dataFormat);
        validateDataNotEmpty(rawData);
    }

    ExternalDataParseException prepareForRethrow(Throwable cause) {
        return getParseException(cause);
    }

    private void validateProperDataFormat(DataFormat dataFormat) {
        if (dataFormat != JSON)
            throw getParseException(
                    format("\"{0}\" supports only json data. Data format \"{1}\" not applicable",
                            this.getClass().getSimpleName(), dataFormat));
    }

    private void validateDataNotEmpty(String rawData) {
        if (rawData.replaceAll("\\s", "").equals("{}"))
            throw getParseException(format("\"{0}\" cannot convert empty json.", this.getClass().getSimpleName()));
    }

    private ExternalDataParseException getParseException(String messageExtension) {
        return new ExternalDataParseException(PARSE_EXCEPTION_MESSAGE + " " + messageExtension);
    }

    private ExternalDataParseException getParseException(Throwable cause) {
        return new ExternalDataParseException(PARSE_EXCEPTION_MESSAGE + " Details in underlying exception message.",
                cause);
    }

    static RandomNumberDataValidator getInstance() {
        return INSTANCE;
    }

}
