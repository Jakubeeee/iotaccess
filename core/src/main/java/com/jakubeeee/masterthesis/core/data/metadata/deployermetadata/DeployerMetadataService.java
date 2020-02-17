package com.jakubeeee.masterthesis.core.data.metadata.deployermetadata;

import com.jakubeeee.masterthesis.core.data.metadata.MetadataService;
import com.jakubeeee.masterthesis.core.misc.MandatoryEntityNotFound;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Default service related to operations on {@link DeployerMetadata} objects.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class DeployerMetadataService implements MetadataService<DeployerMetadata> {

    private final DeployerMetadataRepository deployerMetadataRepository;

    public void save(@NonNull DeployerMetadata deployerMetadata) {
        deployerMetadataRepository.save(deployerMetadata);
    }

    @Override
    public DeployerMetadata findByIdentifier(@NonNull String identifier) {
        return deployerMetadataRepository.findByIdentifier(identifier).orElseThrow(MandatoryEntityNotFound::new);
    }

    @Override
    public Optional<DeployerMetadata> findOptionalByIdentifier(@NonNull String identifier) {
        return deployerMetadataRepository.findByIdentifier(identifier);
    }

    public boolean isIdentifierUnique(@NonNull String identifier) {
        return deployerMetadataRepository.findByIdentifier(identifier).isEmpty();
    }

    public void setRegisteredTrue(@NonNull String identifier) {
        deployerMetadataRepository.updateRegistered(true, identifier);
    }

}
