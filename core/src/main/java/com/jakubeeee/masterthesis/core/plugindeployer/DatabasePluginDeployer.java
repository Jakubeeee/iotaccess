package com.jakubeeee.masterthesis.core.plugindeployer;

import com.jakubeeee.masterthesis.core.data.metadata.pluginmetadata.PluginMetadataService;
import com.jakubeeee.masterthesis.core.data.metadata.processmetadata.ProcessMetadataService;
import com.jakubeeee.masterthesis.core.data.plugindeployment.PluginDeploymentCandidate;
import com.jakubeeee.masterthesis.core.data.plugindeployment.PluginDeploymentCandidateService;
import com.jakubeeee.masterthesis.core.jobschedule.JobScheduleService;
import com.jakubeeee.masterthesis.core.persistence.DataPersistStrategyFactory;
import com.jakubeeee.masterthesis.core.webservice.FetchPluginRestClient;
import com.jakubeeee.masterthesis.pluginapi.PluginConnector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.jar.JarFile;

import static java.text.MessageFormat.format;
import static java.util.stream.Collectors.toUnmodifiableSet;

@Slf4j
@Component
class DatabasePluginDeployer extends BasePluginDeployer {

    private static final String IDENTIFIER = "Database Plugin Deployer";

    private final PluginDeploymentCandidateService pluginDeploymentCandidateService;

    private final DynamicPluginClassLoader classLoader;

    @Value("${deployer.db.interval:-1}")
    private long dbDeployerInterval;

    @Value("${deployer.db.delay:-1}")
    private long dbDeployerDelay;

    public DatabasePluginDeployer(
            PluginMetadataService pluginMetadataService,
            ProcessMetadataService processMetadataService,
            FetchPluginRestClient restClient,
            DataPersistStrategyFactory dataPersistStrategyFactory,
            JobScheduleService jobScheduleService,
            PluginDeploymentCandidateService pluginDeploymentCandidateService) {
        super(pluginMetadataService, processMetadataService, restClient, dataPersistStrategyFactory,
                jobScheduleService);
        this.pluginDeploymentCandidateService = pluginDeploymentCandidateService;
        classLoader = new DynamicPluginClassLoader(this.getClass().getClassLoader());
    }

    @Override
    Set<PluginConnector> scanForPlugins() {
        Set<PluginDeploymentCandidate> pluginDeploymentCandidates = findCandidatesInDatabase();
        return processCandidates(pluginDeploymentCandidates);
    }

    private Set<PluginDeploymentCandidate> findCandidatesInDatabase() {
        return pluginDeploymentCandidateService.findNotDeployed();
    }

    private Set<PluginConnector> processCandidates(Set<PluginDeploymentCandidate> candidates) {
        return candidates.stream()
                .map(this::processCandidate)
                .collect(toUnmodifiableSet());
    }

    private PluginConnector processCandidate(PluginDeploymentCandidate candidate) {
        JarFile jarFile = tryExtractJarFileFromCandidate(candidate);
        PluginConnector connector = classLoader.loadPluginFromJarFile(jarFile);
        pluginDeploymentCandidateService.setDeployedTrue(candidate.getJarName());
        return connector;
    }

    private JarFile tryExtractJarFileFromCandidate(PluginDeploymentCandidate candidate) {
        try {
            return extractJarFileFromPluginDeployment(candidate);
        } catch (IOException e) {
            throw new IllegalStateException(
                    format("An exception has occurred during loading of jar file from database using \"{0}\". " +
                            "Details in underlying exception message", this.getClass().getSimpleName()), e);
        }
    }

    private JarFile extractJarFileFromPluginDeployment(PluginDeploymentCandidate candidate)
            throws IOException {
        var jarFile = new File(candidate.getJarName());
        byte[] binaryData = candidate.getBinaryData();
        try (var fileOutputStream = new FileOutputStream(jarFile)) {
            fileOutputStream.write(binaryData);
            return new JarFile(jarFile);
        }
    }

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public RegistrationStrategy getAssociatedStrategy() {
        return RegistrationStrategy.DATABASE;
    }

    @Override
    public long getInterval() {
        return dbDeployerInterval;
    }

    @Override
    public long getInitialDelay() {
        return dbDeployerDelay;
    }

}
