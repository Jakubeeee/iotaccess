package com.jakubeeee.iotaccess.core.data.entry.meteoentry;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeteoEntryRepository extends CrudRepository<MeteoEntry, Long> {

    @Modifying
    @Query("UPDATE MeteoEntry e SET e.processMetadata = null WHERE e.processMetadata.id = :processMetadataId")
    void disconnectFromProcessMetadata(long processMetadataId);

}
