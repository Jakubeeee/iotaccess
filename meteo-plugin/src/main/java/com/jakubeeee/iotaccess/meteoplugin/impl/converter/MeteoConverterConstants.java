package com.jakubeeee.iotaccess.meteoplugin.impl.converter;

public final class MeteoConverterConstants {

    public static final String JSON_CONVERTER_IDENTIFIER = "meteo_json_converter";

    public static final String XML_CONVERTER_IDENTIFIER = "meteo_xml_converter";

    public static final String TXT_CONVERTER_IDENTIFIER = "meteo_txt_converter";

    private MeteoConverterConstants() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + " class cannot be initialized");
    }

}
