package com.jakubeeee.iotaccess.core.plugindeployer;

import com.jakubeeee.iotaccess.core.misc.NotInitializableClassException;

final class ProcessTaskParameterConstants {

    static final String ALL_CONVERTERS_PARAMETER = "converter.all_converters";

    static final String PROCESS_IDENTIFIER_PARAMETER = "process.identifier";

    static final String FETCH_URL_PARAMETER = "fetch.url";

    static final String FETCH_DATA_FORMAT_PARAMETER = "fetch.data_format";

    static final String FETCH_DATA_TYPE_PARAMETER = "fetch.data_type";

    static final String CONVERTER_IDENTIFIER_PARAMETER = "converter.identifier";

    private ProcessTaskParameterConstants() {
        throw new NotInitializableClassException(this.getClass());
    }

}
