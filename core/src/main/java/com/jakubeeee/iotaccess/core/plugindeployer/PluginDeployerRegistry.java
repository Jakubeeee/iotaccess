package com.jakubeeee.iotaccess.core.plugindeployer;

import com.jakubeeee.iotaccess.core.data.metadata.InitialMetadataSweeper;
import com.jakubeeee.iotaccess.core.data.metadata.deployermetadata.DeployerMetadata;
import com.jakubeeee.iotaccess.core.data.metadata.deployermetadata.DeployerMetadataService;
import com.jakubeeee.iotaccess.core.taskschedule.ScheduledTaskConfig;
import com.jakubeeee.iotaccess.core.taskschedule.TaskScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.jakubeeee.iotaccess.core.plugindeployer.RegistrationStrategiesResolveHelper.resolveRegistrationStrategiesFromArgs;
import static java.util.stream.Collectors.toUnmodifiableSet;

@Slf4j
@RequiredArgsConstructor
@Order(InitialMetadataSweeper.INITIAL_METADATA_SWEEPER_ORDER + 1)
@Component
public class PluginDeployerRegistry implements ApplicationRunner {

    private static final String PLUGIN_DEPLOYERS_TASK_GROUP_NAME = "plugin_deployers_group";

    private final DeployerMetadataService deployerMetadataService;

    private final TaskScheduleService taskScheduleService;

    private final Set<PluginDeployer> pluginDeployers;

    @Transactional
    @Override
    public void run(ApplicationArguments args) {
        Set<RegistrationStrategy> registrationStrategies = resolveRegistrationStrategiesFromArgs(args);
        Set<PluginDeployer> applicableDeployers = resolveApplicableDeployers(registrationStrategies);
        saveMetadataSet(applicableDeployers);
        registerDeployers(applicableDeployers);
    }

    private Set<PluginDeployer> resolveApplicableDeployers(Set<RegistrationStrategy> registrationStrategies) {
        return pluginDeployers.stream()
                .filter(deployer -> isDeployerApplicable(deployer, registrationStrategies))
                .collect(toUnmodifiableSet());
    }

    private boolean isDeployerApplicable(PluginDeployer checkedDeployer,
                                         Set<RegistrationStrategy> registrationStrategies) {
        return registrationStrategies.contains(checkedDeployer.getAssociatedStrategy());
    }

    private void saveMetadataSet(Set<PluginDeployer> pluginDeployers) {
        for (var pluginDeployer : pluginDeployers)
            saveMetadata(pluginDeployer);
    }

    private void saveMetadata(PluginDeployer pluginDeployer) {
        var deployerMetadata = new DeployerMetadata(
                pluginDeployer.getIdentifier(),
                pluginDeployer.getAssociatedStrategy(),
                pluginDeployer.getInterval());
        deployerMetadataService.save(deployerMetadata);
    }

    private void registerDeployers(Set<PluginDeployer> applicableDeployers) {
        for (var deployer : applicableDeployers) {
            var config = new ScheduledTaskConfig(
                    deployer.getIdentifier().replaceAll("\\s", ""),
                    PLUGIN_DEPLOYERS_TASK_GROUP_NAME,
                    deployer.getInterval());
            taskScheduleService.schedule(deployer::deploy, config);
            deployerMetadataService.setRegisteredTrue(deployer.getIdentifier());
            LOG.trace("Registered plugin deployer with identifier: \"{}\"", deployer.getIdentifier());
        }
    }

}
