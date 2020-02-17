package com.jakubeeee.masterthesis.core.persistence;

import com.jakubeeee.masterthesis.core.data.entry.EntryEntity;
import com.jakubeeee.masterthesis.core.data.metadata.processmetadata.ProcessMetadata;
import com.jakubeeee.masterthesis.core.data.metadata.processmetadata.ProcessMetadataService;
import com.jakubeeee.masterthesis.pluginapi.property.FetchedContainer;
import com.jakubeeee.masterthesis.pluginapi.property.FetchedRecord;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public abstract class BaseDataPersistStrategy<T extends EntryEntity> implements DataPersistStrategy {

    private final ProcessMetadataService processMetadataService;

    @Override
    public void persist(FetchedContainer container, String processIdentifier) {
        List<FetchedRecord> records = container.getFetchedRecords();
        ProcessMetadata processMetadata = processMetadataService.findByIdentifier(processIdentifier);
        processRecords(records, processMetadata);
    }

    private void processRecords(List<FetchedRecord> records, ProcessMetadata metadata) {
        for (var record : records)
            processRecord(record, metadata);
    }

    private void processRecord(FetchedRecord record, ProcessMetadata metadata) {
        T newEntry = generateEntry(record, metadata);
        persistEntry(newEntry);
    }

    protected abstract T generateEntry(FetchedRecord record, ProcessMetadata metadata);

    protected abstract void persistEntry(T newEntry);

}
