package com.jakubeeee.masterthesis.meteoplugin.impl.url;

public final class MeteoClientConstants {

    private static final String PROTOCOL = "http";

    private static final String HOST = "150.254.36.196";

    private static final String PORT = "7100";

    private static final String URL_PREFIX = PROTOCOL + "://" + HOST + ":" + PORT;

    private static final String COMMAND_PARAMETER = "?command={0}";

    private static final String RESULT_PARAMETER = "&result={1}";

    private static final String IDENTIFIER_PARAMETER = "&ide={2}";

    private static final String GET_METEO_ENDPOINT = "/meteo";

    static final String GET_METEO_PATH =
            URL_PREFIX + GET_METEO_ENDPOINT + COMMAND_PARAMETER + RESULT_PARAMETER + IDENTIFIER_PARAMETER;

    private MeteoClientConstants() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + " class cannot be initialized");
    }

}
