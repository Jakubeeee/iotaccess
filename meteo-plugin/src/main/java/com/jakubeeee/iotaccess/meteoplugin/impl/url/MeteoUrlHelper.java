package com.jakubeeee.iotaccess.meteoplugin.impl.url;

import lombok.NonNull;

import java.text.MessageFormat;

import static com.jakubeeee.iotaccess.meteoplugin.impl.url.MeteoClientConstants.GET_METEO_PATH;

public final class MeteoUrlHelper {

    public static String getUrl() {
        return getUrl(CommandUrlParameter.NONE, ResultUrlParameter.NONE, IdentifierUrlParameter.NONE);
    }

    public static String getUrl(@NonNull CommandUrlParameter commandUrlParameter) {
        return getUrl(commandUrlParameter, ResultUrlParameter.NONE, IdentifierUrlParameter.NONE);
    }

    public static String getUrl(@NonNull ResultUrlParameter resultUrlParameter) {
        return getUrl(CommandUrlParameter.NONE, resultUrlParameter, IdentifierUrlParameter.NONE);
    }

    public static String getUrl(@NonNull IdentifierUrlParameter identifierUrlParameter) {
        return getUrl(CommandUrlParameter.NONE, ResultUrlParameter.NONE, identifierUrlParameter);
    }

    public static String getUrl(@NonNull CommandUrlParameter commandUrlParameter,
                                @NonNull ResultUrlParameter resultUrlParameter) {
        return getUrl(commandUrlParameter, resultUrlParameter, IdentifierUrlParameter.NONE);
    }

    public static String getUrl(@NonNull CommandUrlParameter commandUrlParameter,
                                @NonNull IdentifierUrlParameter identifierUrlParameter) {
        return getUrl(commandUrlParameter, ResultUrlParameter.NONE, identifierUrlParameter);
    }

    public static String getUrl(@NonNull ResultUrlParameter resultUrlParameter,
                                @NonNull IdentifierUrlParameter identifierUrlParameter) {
        return getUrl(CommandUrlParameter.NONE, resultUrlParameter, identifierUrlParameter);
    }

    public static String getUrl(@NonNull CommandUrlParameter commandUrlParameter,
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
