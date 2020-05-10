package com.jakubeeee.iotaccess.core.data.metadata.scheduledtaskmetadata;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ScheduledTaskMetadataRepository extends CrudRepository<ScheduledTaskMetadata, Long> {

    Optional<ScheduledTaskMetadata> findByIdentifier(String identifier);

    @Modifying
    @Query("UPDATE ScheduledTaskMetadata m SET m.running = :running WHERE m.identifier = :identifier")
    void updateRunning(boolean running, String identifier);

}
