package com.jakubeeee.masterthesis.core.plugindeployer;

import com.jakubeeee.masterthesis.core.data.metadata.deployermetadata.DeployerMetadata;
import com.jakubeeee.masterthesis.core.data.metadata.deployermetadata.DeployerMetadataService;
import com.jakubeeee.masterthesis.core.jobschedule.JobScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.jakubeeee.masterthesis.core.plugindeployer.RegistrationStrategiesResolveHelper.resolveRegistrationStrategiesFromArgs;
import static java.util.stream.Collectors.toUnmodifiableSet;

/**
 * Component invoked at application startup registers and schedules all applicable{@link PluginDeployer deployers}.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class PluginDeployerRegistry implements ApplicationRunner {

    private static final long DEFAULT_DEPLOYER_INTERVAL = 120_000;

    private static final long DEFAULT_DEPLOYER_INITIAL_DELAY = 5_000;

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
            LOG.info("Registered plugin deployer: \"{}\"", deployer.getIdentifier());
            jobScheduleService.scheduleContinuingAsyncJob(deployer::deploy, resolveDeployerInterval(deployer),
                    resolveDeployerInitialDelay(deployer));
            deployerMetadataService.setRegisteredTrue(deployer.getIdentifier());
        }
    }

    private long resolveDeployerInterval(PluginDeployer deployer) {
        return deployer.getInterval() == -1 ? DEFAULT_DEPLOYER_INTERVAL : deployer.getInterval();
    }

    private long resolveDeployerInitialDelay(PluginDeployer deployer) {
        return deployer.getInitialDelay() == -1 ? DEFAULT_DEPLOYER_INITIAL_DELAY : deployer.getInitialDelay();
    }

}
