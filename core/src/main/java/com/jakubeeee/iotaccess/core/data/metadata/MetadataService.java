package com.jakubeeee.iotaccess.core.data.metadata;

import com.jakubeeee.iotaccess.core.data.DataService;
import lombok.NonNull;

import java.util.Optional;

public interface MetadataService<T extends MetadataEntity> extends DataService<T> {

    T findByIdentifier(@NonNull String identifier);

    Optional<T> findOptionalByIdentifier(@NonNull String identifier);

    void delete(@NonNull T metadata);

    void deleteAll();

}
