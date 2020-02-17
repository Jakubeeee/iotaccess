package com.jakubeeee.masterthesis.core.data.metadata.pluginmetadata;

import com.jakubeeee.masterthesis.core.data.metadata.MetadataService;
import com.jakubeeee.masterthesis.core.misc.MandatoryEntityNotFound;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Default service related to operations on {@link PluginMetadata} objects.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PluginMetadataService implements MetadataService<PluginMetadata> {

    private final PluginMetadataRepository pluginMetadataRepository;

    public void save(@NonNull PluginMetadata pluginMetadata) {
        pluginMetadataRepository.save(pluginMetadata);
    }

    public PluginMetadata findByIdentifier(@NonNull String identifier) {
        return pluginMetadataRepository.findByIdentifier(identifier).orElseThrow(MandatoryEntityNotFound::new);
    }

    public Optional<PluginMetadata> findOptionalByIdentifier(@NonNull String identifier) {
        return pluginMetadataRepository.findByIdentifier(identifier);
    }

    public boolean isIdentifierUnique(@NonNull String identifier) {
        return pluginMetadataRepository.findByIdentifier(identifier).isEmpty();
    }

    public void setDeployedTrue(@NonNull String identifier) {
        pluginMetadataRepository.updateDeployed(true, identifier);
    }

}
