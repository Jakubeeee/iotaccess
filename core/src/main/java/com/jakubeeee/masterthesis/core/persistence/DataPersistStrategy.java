package com.jakubeeee.masterthesis.core.persistence;

import com.jakubeeee.masterthesis.pluginapi.converter.DataType;
import com.jakubeeee.masterthesis.pluginapi.property.FetchedContainer;
import lombok.NonNull;

public interface DataPersistStrategy {

    void persist(@NonNull FetchedContainer container, @NonNull String processIdentifier);

    DataType getSupportedDataType();

}
