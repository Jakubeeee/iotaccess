package com.jakubeeee.iotaccess.core.data.entry.meteoentry;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeteoEntryRepository extends CrudRepository<MeteoEntry, Long> {

}
