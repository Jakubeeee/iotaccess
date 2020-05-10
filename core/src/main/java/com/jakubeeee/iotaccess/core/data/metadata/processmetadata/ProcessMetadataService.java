package com.jakubeeee.iotaccess.core.data.metadata.processmetadata;

import com.jakubeeee.iotaccess.core.data.MandatoryEntityNotFoundException;
import com.jakubeeee.iotaccess.core.data.entry.EntryService;
import com.jakubeeee.iotaccess.core.data.metadata.MetadataService;
import com.jakubeeee.iotaccess.core.data.metadata.pluginmetadata.PluginMetadata;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProcessMetadataService implements MetadataService<ProcessMetadata> {

    private final ProcessMetadataRepository processMetadataRepository;

    private final Set<EntryService<?>> entryServices;

    public void save(@NonNull ProcessMetadata processMetadata) {
        processMetadataRepository.save(processMetadata);
        LOG.trace("New \"{}\" saved: \"{}\"", processMetadata.getClass().getSimpleName(), processMetadata);
    }

    @Override
    public ProcessMetadata findByIdentifier(@NonNull String identifier) {
        return processMetadataRepository.findByIdentifier(identifier).orElseThrow(
                () -> new MandatoryEntityNotFoundException(ProcessMetadata.class, "identifier", identifier));
    }

    public Optional<ProcessMetadata> findOptionalByIdentifier(@NonNull String identifier) {
        return processMetadataRepository.findByIdentifier(identifier);
    }

    @Transactional
    @Override
    public void delete(@NonNull ProcessMetadata processMetadata) {
        for (var entryService : entryServices)
            entryService.disconnectFromProcessMetadata(processMetadata);
        processMetadataRepository.delete(processMetadata);
    }

    @Transactional
    public void deleteAllByParent(@NonNull PluginMetadata pluginMetadata) {
        for (var processMetadata : processMetadataRepository.findAllByPluginMetadataId(pluginMetadata.getId()))
            delete(processMetadata);
    }

    @Transactional
    @Override
    public void deleteAll() {
        for (var processMetadata : processMetadataRepository.findAll())
            delete(processMetadata);
    }

}
