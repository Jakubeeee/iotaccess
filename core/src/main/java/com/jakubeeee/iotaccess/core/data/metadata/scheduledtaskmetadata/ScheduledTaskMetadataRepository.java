package com.jakubeeee.iotaccess.core.data.metadata.scheduledtaskmetadata;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface ScheduledTaskMetadataRepository extends CrudRepository<ScheduledTaskMetadata, Long> {

    Optional<ScheduledTaskMetadata> findByIdentifier(String identifier);

    Set<ScheduledTaskMetadata> findAllByGroupIdentifier(String groupIdentifier);

    @Modifying
    @Query("UPDATE ScheduledTaskMetadata m SET m.interval = :interval WHERE m.identifier = :taskIdentifier AND m.groupIdentifier = :groupIdentifier")
    void updateInterval(long interval, String taskIdentifier, String groupIdentifier);

    @Modifying
    @Query("UPDATE ScheduledTaskMetadata m SET m.running = :running WHERE m.identifier = :taskIdentifier AND m.groupIdentifier = :groupIdentifier")
    void updateRunning(boolean running, String taskIdentifier, String groupIdentifier);

    void deleteByGroupIdentifier(String groupIdentifier);

    void deleteByIdentifierAndGroupIdentifier(String taskIdentifier, String groupIdentifier);

}
