package com.jakubeeee.iotaccess.core.data.metadata.processmetadata;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
interface ProcessMetadataRepository extends CrudRepository<ProcessMetadata, Long> {

    Optional<ProcessMetadata> findByIdentifier(String identifier);

    Set<ProcessMetadata> findAllByPluginMetadataId(long pluginMetadataId);

}