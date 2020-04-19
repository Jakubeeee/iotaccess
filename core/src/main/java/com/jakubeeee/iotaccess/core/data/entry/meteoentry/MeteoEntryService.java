package com.jakubeeee.iotaccess.core.data.entry.meteoentry;

import com.jakubeeee.iotaccess.core.data.entry.EntryService;
import com.jakubeeee.iotaccess.core.data.metadata.processmetadata.ProcessMetadata;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MeteoEntryService implements EntryService<MeteoEntry> {

    private final MeteoEntryRepository meteoEntryRepository;

    public void save(@NonNull MeteoEntry entry) {
        meteoEntryRepository.save(entry);
        LOG.trace("New \"{}\" saved: \"{}\"", entry.getClass().getSimpleName(), entry);
    }

    @Override
    public void disconnectFromProcessMetadata(ProcessMetadata processMetadata) {
        meteoEntryRepository.disconnectFromProcessMetadata(processMetadata.getId());
    }

}
