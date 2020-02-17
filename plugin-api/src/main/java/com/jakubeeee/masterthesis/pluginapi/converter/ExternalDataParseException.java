package com.jakubeeee.masterthesis.pluginapi.converter;

public class ExternalDataParseException extends RuntimeException {

    public ExternalDataParseException(String message) {
        super(message);
    }

    public ExternalDataParseException(String message, Throwable cause) {
        super(message, cause);
    }

}
