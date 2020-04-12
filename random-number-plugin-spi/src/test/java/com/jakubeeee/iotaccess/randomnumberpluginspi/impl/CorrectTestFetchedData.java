package com.jakubeeee.iotaccess.randomnumberpluginspi.impl;

class CorrectTestFetchedData {

    static final String CORRECT_NO_VALUES_JSON =
            """

            {
                "values": [
                ]
            }
            """;

    static final String CORRECT_SINGLE_INTEGER_VALUE_JSON =
            """

            {
                "values": [
                    "1"
                ]
            }
            """;

    static final String CORRECT_SINGLE_DOUBLE2_VALUE_JSON =
            """

            {
                "values": [
                    "1.23"
                ]
            }
            """;

    static final String CORRECT_SINGLE_DOUBLE8_VALUE_JSON =
            """

            {
                "values": [
                    "1.23456789"
                ]
            }
            """;

    static final String CORRECT_SINGLE_DOUBLE16_VALUE_JSON =
            """

            {
                "values": [
                    "1.2345678987654321"
                ]
            }
            """;

    static final String CORRECT_SINGLE_NEGATIVE_INTEGER_VALUE_JSON =
            """

            {
                "values": [
                    "-1"
                ]
            }
            """;

    static final String CORRECT_SINGLE_NEGATIVE_DOUBLE2_VALUE_JSON =
            """

            {
                "values": [
                    "-1.23"
                ]
            }
            """;

    static final String CORRECT_SINGLE_NEGATIVE_DOUBLE8_VALUE_JSON =
            """

            {
                "values": [
                    "-1.23456789"
                ]
            }
            """;

    static final String CORRECT_SINGLE_NEGATIVE_DOUBLE16_VALUE_JSON =
            """

            {
                "values": [
                    "-1.2345678987654321"
                ]
            }
            """;

    static final String CORRECT_MULTIPLE_VALUE_JSON =
            """

            {
                "values": [
                    "1.23",
                    "2.34",
                    "-3.45",
                    "4",
                    "5.678987654321"
                ]
            }
            """;

}
