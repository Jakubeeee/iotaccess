package com.jakubeeee.iotaccess.core.plugindeployer;

import com.jakubeeee.iotaccess.core.data.metadata.InitialMetadataSweeper;
import com.jakubeeee.iotaccess.core.data.metadata.deployermetadata.DeployerMetadata;
import com.jakubeeee.iotaccess.core.data.metadata.deployermetadata.DeployerMetadataService;
import com.jakubeeee.iotaccess.core.jobschedule.JobScheduleService;
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

    private final DeployerMetadataService deployerMetadataService;

    private final JobScheduleService jobScheduleService;

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
        var deployerMetadata =
                new DeployerMetadata(pluginDeployer.getIdentifier(), pluginDeployer.getAssociatedStrategy());
        deployerMetadataService.save(deployerMetadata);
    }

    private void registerDeployers(Set<PluginDeployer> applicableDeployers) {
        for (var deployer : applicableDeployers) {
            jobScheduleService.schedule(deployer::deploy, deployer.getInterval());
            deployerMetadataService.setRegisteredTrue(deployer.getIdentifier());
            LOG.trace("Registered plugin deployer with identifier: \"{}\"", deployer.getIdentifier());
        }
    }

}
