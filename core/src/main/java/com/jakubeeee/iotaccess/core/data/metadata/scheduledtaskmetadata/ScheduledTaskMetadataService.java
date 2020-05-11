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

    @Transactional
    public void updateInterval(@NonNull String taskIdentifier, @NonNull String groupIdentifier, long newInterval) {
        scheduledTaskMetadataRepository.updateInterval(newInterval, taskIdentifier, groupIdentifier);
    }

    @Transactional
    public void setRunningTrueForAll() {
        for (var taskMetadata : scheduledTaskMetadataRepository.findAll()) {
            scheduledTaskMetadataRepository
                    .updateRunning(true, taskMetadata.getIdentifier(), taskMetadata.getGroupIdentifier());
        }
    }

    @Transactional
    public void setRunningFalseForAll() {
        for (var taskMetadata : scheduledTaskMetadataRepository.findAll()) {
            scheduledTaskMetadataRepository
                    .updateRunning(false, taskMetadata.getIdentifier(), taskMetadata.getGroupIdentifier());
        }
    }

    @Transactional
    public void setRunningTrueForGroup(@NonNull String groupIdentifier) {
        for (var taskMetadata : scheduledTaskMetadataRepository.findAllByGroupIdentifier(groupIdentifier)) {
            scheduledTaskMetadataRepository
                    .updateRunning(true, taskMetadata.getIdentifier(), taskMetadata.getGroupIdentifier());
        }
    }

    @Transactional
    public void setRunningFalseForGroup(@NonNull String groupIdentifier) {
        for (var taskMetadata : scheduledTaskMetadataRepository.findAllByGroupIdentifier(groupIdentifier)) {
            scheduledTaskMetadataRepository
                    .updateRunning(false, taskMetadata.getIdentifier(), taskMetadata.getGroupIdentifier());
        }
    }

    @Transactional
    public void setRunningTrueForTask(@NonNull String taskIdentifier, @NonNull String groupIdentifier) {
        scheduledTaskMetadataRepository.updateRunning(true, taskIdentifier, groupIdentifier);
    }

    @Transactional
    public void setRunningFalseForTask(@NonNull String taskIdentifier, @NonNull String groupIdentifier) {
        scheduledTaskMetadataRepository.updateRunning(false, taskIdentifier, groupIdentifier);
    }

    @Transactional
    public void deleteAllOfGroup(@NonNull String groupIdentifier) {
        scheduledTaskMetadataRepository.deleteByGroupIdentifier(groupIdentifier);
    }

    @Transactional
    public void delete(@NonNull String taskIdentifier, @NonNull String groupIdentifier) {
        scheduledTaskMetadataRepository.deleteByIdentifierAndGroupIdentifier(taskIdentifier, groupIdentifier);
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
