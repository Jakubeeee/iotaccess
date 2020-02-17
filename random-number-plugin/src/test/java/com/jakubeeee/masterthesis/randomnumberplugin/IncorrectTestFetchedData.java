package com.jakubeeee.masterthesis.randomnumberplugin;

class IncorrectTestFetchedData {

    // @formatter:off

    static final String INCORRECT_MALFORMED_JSON =
            "" +
                "\"values\"" +
                    "\"a\"" +
                "]" +
            "}";

    static final String INCORRECT_EMPTY_JSON =
            "{" +
            "}";

    static final String INCORRECT_NO_VALUES_JSON =
            "{" +
                "\"values\": [" +
                "]" +
            "}";

    static final String INCORRECT_SINGLE_NO_NUMERIC_VALUE_JSON =
            "{" +
                "\"values\": [" +
                    "\"a\"" +
                "]" +
            "}";

    static final String INCORRECT_MULTIPLE_NO_NUMERIC_VALUES_JSON =
            "{" +
                "\"values\": [" +
                    "\"a\"," +
                    "\"b\"," +
                    "\"c\"" +
                "]" +
            "}";

    static final String INCORRECT_MIXED_NUMERIC_AND_NO_NUMERIC_VALUES_JSON =
            "{" +
                "\"values\": [" +
                    "\"1.23\"," +
                    "\"a\"," +
                    "\"5\"," +
                    "\"b\"" +
                "]" +
            "}";

    // @formatter:on

}
