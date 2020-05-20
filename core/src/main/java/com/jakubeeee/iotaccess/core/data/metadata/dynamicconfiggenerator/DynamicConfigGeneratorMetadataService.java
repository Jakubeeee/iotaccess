package com.jakubeeee.iotaccess.core.data.metadata.dynamicconfiggenerator;

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
public class DynamicConfigGeneratorMetadataService implements MetadataService<DynamicConfigGeneratorMetadata> {

    private final DynamicConfigGeneratorMetadataRepository dynamicConfigGeneratorMetadataRepository;

    public void save(@NonNull DynamicConfigGeneratorMetadata configGeneratorMetadata) {
        dynamicConfigGeneratorMetadataRepository.save(configGeneratorMetadata);
        LOG.trace("New \"{}\" saved: \"{}\"", configGeneratorMetadata.getClass().getSimpleName(),
                configGeneratorMetadata);
    }

    @Override
    public DynamicConfigGeneratorMetadata findByIdentifier(@NonNull String identifier) {
        return dynamicConfigGeneratorMetadataRepository.findByIdentifier(identifier).orElseThrow(
                () -> new MandatoryEntityNotFoundException(DynamicConfigGeneratorMetadata.class, "identifier",
                        identifier));
    }

    @Override
    public Optional<DynamicConfigGeneratorMetadata> findOptionalByIdentifier(@NonNull String identifier) {
        return dynamicConfigGeneratorMetadataRepository.findByIdentifier(identifier);
    }

    public void setRegisteredTrue(@NonNull String identifier) {
        dynamicConfigGeneratorMetadataRepository.updateRegistered(true, identifier);
    }

    @Transactional
    @Override
    public void delete(@NonNull DynamicConfigGeneratorMetadata metadata) {
        dynamicConfigGeneratorMetadataRepository.delete(metadata);

    }

    @Transactional
    @Override
    public void deleteAll() {
        dynamicConfigGeneratorMetadataRepository.deleteAll();
    }

}
