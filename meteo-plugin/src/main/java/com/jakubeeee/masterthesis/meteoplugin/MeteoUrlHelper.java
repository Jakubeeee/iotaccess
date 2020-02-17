package com.jakubeeee.masterthesis.meteoplugin;

import lombok.NonNull;

import java.text.MessageFormat;

import static com.jakubeeee.masterthesis.meteoplugin.MeteoClientConstants.GET_METEO_PATH;

final class MeteoUrlHelper {

    static String getUrl() {
        return getUrl(CommandUrlParameter.NONE, ResultUrlParameter.NONE, IdentifierUrlParameter.NONE);
    }

    static String getUrl(@NonNull CommandUrlParameter commandUrlParameter) {
        return getUrl(commandUrlParameter, ResultUrlParameter.NONE, IdentifierUrlParameter.NONE);
    }

    static String getUrl(@NonNull ResultUrlParameter resultUrlParameter) {
        return getUrl(CommandUrlParameter.NONE, resultUrlParameter, IdentifierUrlParameter.NONE);
    }

    static String getUrl(@NonNull IdentifierUrlParameter identifierUrlParameter) {
        return getUrl(CommandUrlParameter.NONE, ResultUrlParameter.NONE, identifierUrlParameter);
    }

    static String getUrl(@NonNull CommandUrlParameter commandUrlParameter,
                         @NonNull ResultUrlParameter resultUrlParameter) {
        return getUrl(commandUrlParameter, resultUrlParameter, IdentifierUrlParameter.NONE);
    }

    static String getUrl(@NonNull CommandUrlParameter commandUrlParameter,
                         @NonNull IdentifierUrlParameter identifierUrlParameter) {
        return getUrl(commandUrlParameter, ResultUrlParameter.NONE, identifierUrlParameter);
    }

    static String getUrl(@NonNull ResultUrlParameter resultUrlParameter,
                         @NonNull IdentifierUrlParameter identifierUrlParameter) {
        return getUrl(CommandUrlParameter.NONE, resultUrlParameter, identifierUrlParameter);
    }

    static String getUrl(@NonNull CommandUrlParameter commandUrlParameter,
                         @NonNull ResultUrlParameter resultUrlParameter,
                         @NonNull IdentifierUrlParameter identifierUrlParameter) {
        return MessageFormat.format(GET_METEO_PATH,
                commandUrlParameter.getValue(),
                resultUrlParameter.getValue(),
                identifierUrlParameter.getValue());
    }

    private MeteoUrlHelper() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + " class cannot be initialized");
    }

}
