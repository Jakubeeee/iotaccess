package com.jakubeeee.iotaccess.core.persistence;

import com.jakubeeee.iotaccess.pluginapi.converter.DataType;
import com.jakubeeee.iotaccess.pluginapi.property.FetchedContainer;
import lombok.NonNull;

public interface DataPersistStrategy {

    void persist(@NonNull FetchedContainer container, @NonNull String processIdentifier);

    DataType getSupportedDataType();

}
