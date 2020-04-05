package com.jakubeeee.masterthesis.core.plugindeployer;

import com.jakubeeee.masterthesis.core.BaseIsolatedIntegrationTest;
import com.jakubeeee.masterthesis.core.CoreApplicationEntryPoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static com.jakubeeee.masterthesis.core.plugindeployer.PluginDeployerPackageITConstants.*;

@SpringBootTest(
        classes = CoreApplicationEntryPoint.class,
        args = "--regstrat=db"
)
class DatabasePluginDeployerIT extends BaseIsolatedIntegrationTest {

    @Autowired
    private PluginDeployerPackageITHelper helper;

    @Test
    void databaseDeployerRegistrationIntegrationTest() {
        helper.insertTestPluginIntoDatabase();
        helper.validatePluginDeployed(
                DB_RANDOM_NUMBER_PLUGIN_IDENTIFIER,
                Set.of(DB_SINGLE_RANDOM_NUMBER_FETCH_PROCESS,
                        DB_THREE_RANDOM_NUMBERS_FETCH_PROCESS,
                        DB_TEN_RANDOM_NUMBERS_FETCH_PROCESS));
    }

}
