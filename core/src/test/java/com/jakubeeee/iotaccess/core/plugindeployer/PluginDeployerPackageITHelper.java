package com.jakubeeee.iotaccess.core.plugindeployer;

import com.jakubeeee.iotaccess.core.data.metadata.MetadataEntity;
import com.jakubeeee.iotaccess.core.data.metadata.MetadataService;
import com.jakubeeee.iotaccess.core.data.metadata.deployermetadata.DeployerMetadata;
import com.jakubeeee.iotaccess.core.data.metadata.deployermetadata.DeployerMetadataService;
import com.jakubeeee.iotaccess.core.data.metadata.pluginmetadata.PluginMetadata;
import com.jakubeeee.iotaccess.core.data.metadata.pluginmetadata.PluginMetadataService;
import com.jakubeeee.iotaccess.core.data.metadata.processmetadata.ProcessMetadata;
import com.jakubeeee.iotaccess.core.data.metadata.processmetadata.ProcessMetadataService;
import com.jakubeeee.iotaccess.core.data.plugindeployment.PluginDeploymentCandidate;
import com.jakubeeee.iotaccess.core.data.plugindeployment.PluginDeploymentCandidateService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.util.Optional;
import java.util.Set;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@Component
class PluginDeployerPackageITHelper {

    @Autowired
    private DeployerMetadataService deployerMetadataService;

    @Autowired
    private PluginMetadataService pluginMetadataService;

    @Autowired
    private ProcessMetadataService processMetadataService;

    @Autowired
    private PluginDeploymentCandidateService pluginDeploymentCandidateService;

    @Value("${deployer.db.artifact.location}")
    private String deployerDBArtifactLocation;

    void validateDeployerRegistered(String deployerIdentifier, RegistrationStrategy registrationStrategy) {
        await().until(() -> {
            DeployerMetadata deployerMetadata = fetchMetadata(deployerIdentifier, deployerMetadataService);
            if (deployerMetadata == null) {
                return false;
            }
            if (!deployerMetadata.isRegistered()) {
                LOG.debug("\"{}\" not registered yet. Aborting this attempt.", deployerIdentifier);
                return false;
            }
            assertThat(deployerMetadata.getIdentifier(), is(equalTo(deployerIdentifier)));
            assertTrue(deployerMetadata.isRegistered());
            assertThat(deployerMetadata.getRegistrationStrategy(), is(equalTo(registrationStrategy)));
            return true;
        });
    }

    void validatePluginDeployed(String pluginIdentifier, Set<String> processesIdentifiers) {
        await().until(() -> {

            PluginMetadata pluginMetadata = fetchMetadata(pluginIdentifier, pluginMetadataService);
            if (pluginMetadata == null)
                return false;
            if (!pluginMetadata.isDeployed()) {
                LOG.debug("\"{}\" not registered yet. Aborting this attempt.", pluginIdentifier);
                return false;
            }
            assertThat(pluginMetadata.getIdentifier(), is(equalTo(pluginIdentifier)));
            assertTrue(pluginMetadata.isDeployed());

            for (var processIdentifier : processesIdentifiers) {
                ProcessMetadata processMetadata = fetchMetadata(processIdentifier, processMetadataService);
                if (processMetadata == null)
                    return false;
                assertThat(processMetadata.getIdentifier(), is(equalTo(processIdentifier)));
                assertThat(processMetadata.getPluginMetadata(), is(equalTo(pluginMetadata)));
            }

            return true;
        });
    }

    <T extends MetadataEntity> T fetchMetadata(String metadataIdentifier,
                                               MetadataService<T> metadataService) {
        LOG.debug("Searching for \"{}\" metadata in the database...", metadataIdentifier);
        Optional<T> metadataO = metadataService.findOptionalByIdentifier(metadataIdentifier);
        if (metadataO.isEmpty()) {
            LOG.debug("\"{}\" not found yet. Aborting this attempt.", metadataIdentifier);
            return null;
        }
        LOG.debug("\"{}\" found successfully.", metadataIdentifier);
        return metadataO.get();
    }

    @SneakyThrows
    void insertTestPluginIntoDatabase() {
        try (var inputStream = new FileInputStream(deployerDBArtifactLocation)) {
            var testCandidate = new PluginDeploymentCandidate();
            testCandidate.setDeployed(false);
            testCandidate.setJarName("test_db_random_number_plugin.jar");
            testCandidate.setBinaryData(inputStream.readAllBytes());
            pluginDeploymentCandidateService.save(testCandidate);
        }
    }

}
