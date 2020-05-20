package com.jakubeeee.iotaccess.core.data.metadata.dynamicconfigreader;

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
public class DynamicConfigReaderMetadataService implements MetadataService<DynamicConfigReaderMetadata> {

    private final DynamicConfigReaderMetadataRepository dynamicConfigReaderMetadataRepository;

    public void save(@NonNull DynamicConfigReaderMetadata configReaderMetadata) {
        dynamicConfigReaderMetadataRepository.save(configReaderMetadata);
        LOG.trace("New \"{}\" saved: \"{}\"", configReaderMetadata.getClass().getSimpleName(), configReaderMetadata);
    }

    @Override
    public DynamicConfigReaderMetadata findByIdentifier(@NonNull String identifier) {
        return dynamicConfigReaderMetadataRepository.findByIdentifier(identifier).orElseThrow(
                () -> new MandatoryEntityNotFoundException(DynamicConfigReaderMetadata.class, "identifier",
                        identifier));
    }

    @Override
    public Optional<DynamicConfigReaderMetadata> findOptionalByIdentifier(@NonNull String identifier) {
        return dynamicConfigReaderMetadataRepository.findByIdentifier(identifier);
    }

    public void setRegisteredTrue(@NonNull String identifier) {
        dynamicConfigReaderMetadataRepository.updateRegistered(true, identifier);
    }

    @Transactional
    @Override
    public void delete(@NonNull DynamicConfigReaderMetadata metadata) {
        dynamicConfigReaderMetadataRepository.delete(metadata);
    }

    @Transactional
    @Override
    public void deleteAll() {
        dynamicConfigReaderMetadataRepository.deleteAll();
    }

}
