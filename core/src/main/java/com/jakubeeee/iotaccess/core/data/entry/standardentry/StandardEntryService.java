package com.jakubeeee.iotaccess.core.data.entry.standardentry;

import com.jakubeeee.iotaccess.core.data.entry.EntryService;
import com.jakubeeee.iotaccess.core.data.metadata.processmetadata.ProcessMetadata;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class StandardEntryService implements EntryService<StandardEntry> {

    private final StandardEntryRepository standardEntryRepository;

    public void save(@NonNull StandardEntry entry) {
        standardEntryRepository.save(entry);
        LOG.trace("New \"{}\" saved: \"{}\"", entry.getClass().getSimpleName(), entry);
    }

    @Override
    public void disconnectFromProcessMetadata(ProcessMetadata processMetadata) {
        standardEntryRepository.disconnectFromProcessMetadata(processMetadata.getId());
    }

}
