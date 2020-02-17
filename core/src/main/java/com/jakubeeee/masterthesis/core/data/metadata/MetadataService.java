package com.jakubeeee.masterthesis.core.data.metadata;

import com.jakubeeee.masterthesis.core.data.DataService;
import lombok.NonNull;

import java.util.Optional;

public interface MetadataService<T extends MetadataEntity> extends DataService<T> {

    T findByIdentifier(@NonNull String identifier);

    Optional<T> findOptionalByIdentifier(@NonNull String identifier);

}
