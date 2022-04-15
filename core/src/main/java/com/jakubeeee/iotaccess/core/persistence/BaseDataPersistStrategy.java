package com.jakubeeee.iotaccess.core.persistence;

import com.jakubeeee.iotaccess.core.data.entry.EntryEntity;
import com.jakubeeee.iotaccess.core.data.metadata.processmetadata.ProcessMetadata;
import com.jakubeeee.iotaccess.core.data.metadata.processmetadata.ProcessMetadataService;
import com.jakubeeee.iotaccess.pluginapi.property.FetchedContainer;
import com.jakubeeee.iotaccess.pluginapi.property.FetchedVector;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class BaseDataPersistStrategy<T extends EntryEntity> implements DataPersistStrategy {

    private final ProcessMetadataService processMetadataService;

    @Override
    public void persist(@NonNull FetchedContainer container, @NonNull String processIdentifier) {
        LOG.trace("Invoked execution of \"{}\" for data fetched by process with identifier \"{}\"",
                this.getClass().getSimpleName(), processIdentifier);
        List<FetchedVector> vectors = container.getFetchedVectors();
        ProcessMetadata processMetadata = processMetadataService.findByIdentifier(processIdentifier);
        processVectors(vectors, processMetadata);
    }

    private void processVectors(List<FetchedVector> vectors, ProcessMetadata metadata) {
        vectors.forEach(vector -> processVector(vector, metadata));
    }

    private void processVector(FetchedVector vector, ProcessMetadata metadata) {
        T newEntry = generateEntry(vector, metadata);
        persistEntry(newEntry);
    }

    protected abstract T generateEntry(FetchedVector vector, ProcessMetadata metadata);

    protected abstract void persistEntry(T newEntry);

}
