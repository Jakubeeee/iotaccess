package com.jakubeeee.iotaccess.core.plugindeployer;

import com.jakubeeee.iotaccess.core.data.metadata.pluginmetadata.PluginMetadataService;
import com.jakubeeee.iotaccess.core.data.metadata.processmetadata.ProcessMetadataService;
import com.jakubeeee.iotaccess.core.data.plugindeployment.PluginDeploymentCandidate;
import com.jakubeeee.iotaccess.core.data.plugindeployment.PluginDeploymentCandidateService;
import com.jakubeeee.iotaccess.core.taskschedule.TaskScheduleService;
import com.jakubeeee.iotaccess.core.persistence.DataPersistStrategyFactory;
import com.jakubeeee.iotaccess.core.webservice.FetchPluginRestClient;
import com.jakubeeee.iotaccess.pluginapi.PluginConnector;
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

    private static final String IDENTIFIER = "database_plugin_deployer";

    private final PluginDeploymentCandidateService pluginDeploymentCandidateService;

    private final DynamicPluginClassLoader classLoader;

    @Value("${deployer.db.interval:-1}")
    private long dbDeployerInterval;

    public DatabasePluginDeployer(
            PluginMetadataService pluginMetadataService,
            ProcessMetadataService processMetadataService,
            FetchPluginRestClient restClient,
            DataPersistStrategyFactory dataPersistStrategyFactory,
            TaskScheduleService taskScheduleService,
            PluginDeploymentCandidateService pluginDeploymentCandidateService) {
        super(pluginMetadataService, processMetadataService, restClient, dataPersistStrategyFactory,
                taskScheduleService);
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

}
