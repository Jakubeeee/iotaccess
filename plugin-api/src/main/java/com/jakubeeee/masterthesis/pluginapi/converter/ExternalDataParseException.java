package com.jakubeeee.masterthesis.pluginapi.converter;

/**
 * Unrecoverable runtime exception used by {@link DataConverter} to indicate that there were problems with parsing of
 * raw external data.
 */
public class ExternalDataParseException extends RuntimeException {

    public ExternalDataParseException(String message) {
        super(message);
    }

    public ExternalDataParseException(String message, Throwable cause) {
        super(message, cause);
    }

}
