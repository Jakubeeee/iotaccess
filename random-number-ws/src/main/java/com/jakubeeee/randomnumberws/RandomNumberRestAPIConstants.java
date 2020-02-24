package com.jakubeeee.randomnumberws;

final class RandomNumberRestAPIConstants {

    static final String GET_RANDOM_NUMBER_ENDPOINT = "/random-number";
    static final String GET_MULTIPLE_RANDOM_NUMBERS_ENDPOINT = "/multiple-random-numbers/{quantity}";

    private RandomNumberRestAPIConstants() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + " class cannot be initialized");
    }

}
