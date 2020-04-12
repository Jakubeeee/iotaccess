package com.jakubeeee.iotaccess.core.plugindeployer;

import com.jakubeeee.iotaccess.core.BaseIsolatedIntegrationTest;
import com.jakubeeee.iotaccess.core.CoreApplicationEntryPoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static com.jakubeeee.iotaccess.core.plugindeployer.PluginDeployerPackageITConstants.*;

@SpringBootTest(
        classes = CoreApplicationEntryPoint.class,
        args = "--regstrat=spi"
)
class SPIPluginDeployerIT extends BaseIsolatedIntegrationTest {

    @Autowired
    private PluginDeployerPackageITHelper helper;

    @Test
    void spiDeployerRegistrationIntegrationTest() {
        helper.validatePluginDeployed(
                SPI_RANDOM_NUMBER_PLUGIN_IDENTIFIER,
                Set.of(SPI_SINGLE_RANDOM_NUMBER_FETCH_PROCESS,
                        SPI_THREE_RANDOM_NUMBERS_FETCH_PROCESS,
                        SPI_TEN_RANDOM_NUMBERS_FETCH_PROCESS));
    }

}
