package com.jakubeeee.iotaccess.core.data.metadata.scheduledtaskmetadata;

import com.jakubeeee.iotaccess.core.data.MandatoryEntityNotFoundException;
import com.jakubeeee.iotaccess.core.data.metadata.MetadataService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ScheduledTaskMetadataService implements MetadataService<ScheduledTaskMetadata> {

    private final ScheduledTaskMetadataRepository scheduledTaskMetadataRepository;

    public void save(@NonNull ScheduledTaskMetadata scheduledTaskMetadata) {
        scheduledTaskMetadataRepository.save(scheduledTaskMetadata);
        LOG.trace("New \"{}\" saved: \"{}\"", scheduledTaskMetadata.getClass().getSimpleName(), scheduledTaskMetadata);
    }

    @Override
    public ScheduledTaskMetadata findByIdentifier(@NonNull String identifier) {
        return scheduledTaskMetadataRepository.findByIdentifier(identifier).orElseThrow(
                () -> new MandatoryEntityNotFoundException(ScheduledTaskMetadata.class, "identifier", identifier));
    }

    @Override
    public Optional<ScheduledTaskMetadata> findOptionalByIdentifier(@NonNull String identifier) {
        return scheduledTaskMetadataRepository.findByIdentifier(identifier);
    }

    public void setRunningTrue(@NonNull String identifier) {
        scheduledTaskMetadataRepository.updateRunning(true, identifier);
    }

    @Transactional
    @Override
    public void delete(@NonNull ScheduledTaskMetadata scheduledTaskMetadata) {
        scheduledTaskMetadataRepository.delete(scheduledTaskMetadata);
    }

    @Transactional
    @Override
    public void deleteAll() {
        scheduledTaskMetadataRepository.deleteAll();
    }

}
