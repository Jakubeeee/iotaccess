package com.jakubeeee.masterthesis.core.data.metadata.processmetadata;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring data jpa repository for crud operations on {@link ProcessMetadata} objects.
 */
@Repository
interface ProcessMetadataRepository extends CrudRepository<ProcessMetadata, Long> {

    Optional<ProcessMetadata> findByIdentifier(String identifier);

}