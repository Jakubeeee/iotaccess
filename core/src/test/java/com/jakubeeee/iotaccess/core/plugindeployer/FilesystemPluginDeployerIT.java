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
        args = "--regstrat=fs"
)
class FilesystemPluginDeployerIT extends BaseIsolatedIntegrationTest {

    @Autowired
    private PluginDeployerPackageITHelper helper;

    @Test
    void filesystemDeployerRegistrationIntegrationTest() {
        helper.validatePluginDeployed(
                FS_RANDOM_NUMBER_PLUGIN_IDENTIFIER,
                Set.of(FS_SINGLE_RANDOM_NUMBER_FETCH_PROCESS,
                        FS_THREE_RANDOM_NUMBERS_FETCH_PROCESS,
                        FS_TEN_RANDOM_NUMBERS_FETCH_PROCESS));
    }

}
