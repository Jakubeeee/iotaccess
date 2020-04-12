package com.jakubeeee.iotaccess.core.data;

import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.ArrayUtils.insert;

public class MandatoryEntityNotFoundException extends RuntimeException {

    public MandatoryEntityNotFoundException(Class<? extends DataEntity> entityType, Object... parameters) {
        super(format("No data of type \"{0}\" found for filtered property \"{1}\" with value \"{2}\"",
                insert(0, parameters, entityType)));
    }

}
