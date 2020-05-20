package com.jakubeeee.iotaccess.core.data.metadata.dynamicconfiggenerator;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DynamicConfigGeneratorMetadataRepository extends CrudRepository<DynamicConfigGeneratorMetadata, Long> {

    Optional<DynamicConfigGeneratorMetadata> findByIdentifier(String identifier);

    @Modifying
    @Query("UPDATE DynamicConfigGeneratorMetadata m SET m.registered = :registered WHERE m.identifier = :identifier")
    void updateRegistered(boolean registered, String identifier);

}
