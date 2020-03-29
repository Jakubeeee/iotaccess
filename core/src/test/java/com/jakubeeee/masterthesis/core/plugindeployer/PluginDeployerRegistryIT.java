package com.jakubeeee.masterthesis.core.plugindeployer;

import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.jakubeeee.masterthesis.core.BaseIntegrationTest;
import com.jakubeeee.masterthesis.core.data.metadata.MetadataEntity;
import com.jakubeeee.masterthesis.core.data.metadata.MetadataService;
import com.jakubeeee.masterthesis.core.data.metadata.deployermetadata.DeployerMetadata;
import com.jakubeeee.masterthesis.core.data.metadata.deployermetadata.DeployerMetadataService;
import com.jakubeeee.masterthesis.core.data.metadata.pluginmetadata.PluginMetadata;
import com.jakubeeee.masterthesis.core.data.metadata.pluginmetadata.PluginMetadataService;
import com.jakubeeee.masterthesis.core.data.metadata.processmetadata.ProcessMetadata;
import com.jakubeeee.masterthesis.core.data.metadata.processmetadata.ProcessMetadataService;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;

import static com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@DatabaseTearDown(value = "/dbunit/delete_all_entries.xml", type = DELETE_ALL)
class PluginDeployerRegistryIT extends BaseIntegrationTest {

    private static final String SPI_PLUGIN_DEPLOYER_IDENTIFIER = "SPI Plugin Deployer";
    private static final String SPI_RANDOM_NUMBER_PLUGIN_IDENTIFIER = "Random number plugin (SPI)";
    private static final String SPI_SINGLE_RANDOM_NUMBER_FETCH_PROCESS = "Single random number fetch process (SPI)";
    private static final String SPI_THREE_RANDOM_NUMBERS_FETCH_PROCESS = "Three random numbers fetch process (SPI)";
    private static final String SPI_TEN_RANDOM_NUMBERS_FETCH_PROCESS = "Ten random numbers fetch process (SPI)";

    private static final String DB_PLUGIN_DEPLOYER_IDENTIFIER = "Database Plugin Deployer";
    private static final String DB_RANDOM_NUMBER_PLUGIN_IDENTIFIER = "Random number plugin (DB)";
    private static final String DB_SINGLE_RANDOM_NUMBER_FETCH_PROCESS = "Single random number fetch process (DB)";
    private static final String DB_THREE_RANDOM_NUMBERS_FETCH_PROCESS = "Three random numbers fetch process (DB)";
    private static final String DB_TEN_RANDOM_NUMBERS_FETCH_PROCESS = "Ten random numbers fetch process (DB)";

    private static final String FS_PLUGIN_DEPLOYER_IDENTIFIER = "Filesystem Plugin Deployer";
    private static final String FS_RANDOM_NUMBER_PLUGIN_IDENTIFIER = "Random number plugin (FS)";
    private static final String FS_SINGLE_RANDOM_NUMBER_FETCH_PROCESS = "Single random number fetch process (FS)";
    private static final String FS_THREE_RANDOM_NUMBERS_FETCH_PROCESS = "Three random numbers fetch process (FS)";
    private static final String FS_TEN_RANDOM_NUMBERS_FETCH_PROCESS = "Ten random numbers fetch process (FS)";

    @Autowired
    private DeployerMetadataService deployerMetadataService;

    @Autowired
    private PluginMetadataService pluginMetadataService;

    @Autowired
    private ProcessMetadataService processMetadataService;

    @BeforeAll
    static void setUp() {
        Awaitility.setDefaultPollInterval(1, SECONDS);
        Awaitility.setDefaultTimeout(15, SECONDS);
    }

    @Test
    void spiDeployerRegistrationIntegrationTest() {
        validateDeployerRegistered(SPI_PLUGIN_DEPLOYER_IDENTIFIER, RegistrationStrategy.SPI);
        validatePluginDeployed(
                SPI_RANDOM_NUMBER_PLUGIN_IDENTIFIER,
                Set.of(SPI_SINGLE_RANDOM_NUMBER_FETCH_PROCESS,
                        SPI_THREE_RANDOM_NUMBERS_FETCH_PROCESS,
                        SPI_TEN_RANDOM_NUMBERS_FETCH_PROCESS));
    }

    @Disabled("Database deployer not implemented yet")
    @Test
    void databaseDeployerRegistrationIntegrationTest() {
        validateDeployerRegistered(DB_PLUGIN_DEPLOYER_IDENTIFIER, RegistrationStrategy.DATABASE);
        validatePluginDeployed(
                DB_RANDOM_NUMBER_PLUGIN_IDENTIFIER,
                Set.of(DB_SINGLE_RANDOM_NUMBER_FETCH_PROCESS,
                        DB_THREE_RANDOM_NUMBERS_FETCH_PROCESS,
                        DB_TEN_RANDOM_NUMBERS_FETCH_PROCESS));
    }

    @Test
    void filesystemDeployerRegistrationIntegrationTest() {
        validateDeployerRegistered(FS_PLUGIN_DEPLOYER_IDENTIFIER, RegistrationStrategy.FILESYSTEM);
        validatePluginDeployed(
                FS_RANDOM_NUMBER_PLUGIN_IDENTIFIER,
                Set.of(FS_SINGLE_RANDOM_NUMBER_FETCH_PROCESS,
                        FS_THREE_RANDOM_NUMBERS_FETCH_PROCESS,
                        FS_TEN_RANDOM_NUMBERS_FETCH_PROCESS));
    }

    private void validateDeployerRegistered(String deployerIdentifier, RegistrationStrategy registrationStrategy) {
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

    private void validatePluginDeployed(String pluginIdentifier, Set<String> processesIdentifiers) {
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

    private <T extends MetadataEntity> T fetchMetadata(String metadataIdentifier, MetadataService<T> metadataService) {
        LOG.debug("Searching for \"{}\" metadata in the database...", metadataIdentifier);
        Optional<T> metadataO = metadataService.findOptionalByIdentifier(metadataIdentifier);
        if (metadataO.isEmpty()) {
            LOG.debug("\"{}\" not found yet. Aborting this attempt.", metadataIdentifier);
            return null;
        }
        LOG.debug("\"{}\" found successfully.", metadataIdentifier);
        return metadataO.get();
    }

}
