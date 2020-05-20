package com.jakubeeee.iotaccess.core.data.metadata.dynamicconfigreader;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DynamicConfigReaderMetadataRepository extends CrudRepository<DynamicConfigReaderMetadata, Long> {

    Optional<DynamicConfigReaderMetadata> findByIdentifier(String identifier);

    @Modifying
    @Query("UPDATE DynamicConfigReaderMetadata m SET m.registered = :registered WHERE m.identifier = :identifier")
    void updateRegistered(boolean registered, String identifier);

}
