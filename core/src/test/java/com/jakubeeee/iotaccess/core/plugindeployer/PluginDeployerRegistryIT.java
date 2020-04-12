package com.jakubeeee.iotaccess.core.plugindeployer;

import com.jakubeeee.iotaccess.core.BaseIntegrationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jakubeeee.iotaccess.core.plugindeployer.PluginDeployerPackageITConstants.*;

@Slf4j
class PluginDeployerRegistryIT extends BaseIntegrationTest {

    @Autowired
    private PluginDeployerPackageITHelper helper;

    @Test
    void spiDeployerRegistrationIntegrationTest() {
        helper.validateDeployerRegistered(SPI_PLUGIN_DEPLOYER_IDENTIFIER, RegistrationStrategy.SPI);
    }

    @Test
    void databaseDeployerRegistrationIntegrationTest() {
        helper.validateDeployerRegistered(DB_PLUGIN_DEPLOYER_IDENTIFIER, RegistrationStrategy.DATABASE);
    }

    @Test
    void filesystemDeployerRegistrationIntegrationTest() {
        helper.validateDeployerRegistered(FS_PLUGIN_DEPLOYER_IDENTIFIER, RegistrationStrategy.FILESYSTEM);
    }

}
