package com.jakubeeee.masterthesis.core.data.entry.meteoentry;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeteoEntryRepository extends CrudRepository<MeteoEntry, Long> {

}
