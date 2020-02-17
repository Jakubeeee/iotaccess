package com.jakubeeee.masterthesis.randomnumberplugin;

final class RandomNumberClientConstants {

    private static final String PROTOCOL = "http";

    private static final String HOST = "random-number-ws";

    private static final String PORT = "8080";

    private static final String URL_PREFIX = PROTOCOL + "://" + HOST + ":" + PORT;

    private static final String GET_RANDOM_NUMBER_ENDPOINT = "/random-number";

    private static final String GET_3_RANDOM_NUMBERS_ENDPOINT = "/multiple-random-numbers/3";

    private static final String GET_10_RANDOM_NUMBERS_ENDPOINT = "/multiple-random-numbers/10";

    static final String GET_RANDOM_NUMBER_PATH = URL_PREFIX + GET_RANDOM_NUMBER_ENDPOINT;

    static final String GET_3_RANDOM_NUMBERS_PATH = URL_PREFIX + GET_3_RANDOM_NUMBERS_ENDPOINT;

    static final String GET_10_RANDOM_NUMBERS_PATH = URL_PREFIX + GET_10_RANDOM_NUMBERS_ENDPOINT;

    private RandomNumberClientConstants() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + " class cannot be initialized");
    }

}
