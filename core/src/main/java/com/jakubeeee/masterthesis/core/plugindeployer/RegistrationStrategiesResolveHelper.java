package com.jakubeeee.masterthesis.core.plugindeployer;

import com.jakubeeee.masterthesis.core.misc.NotInitializableClassException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;

import java.util.*;

import static com.jakubeeee.masterthesis.core.CoreApplicationConstants.CL_ARGUMENT_REGISTRATION_STRATEGIES;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toUnmodifiableSet;

/**
 * Helper class used by {@link PluginDeployerRegistry} for deciding which registration strategies should be invoked
 * after application startup.
 */
@Slf4j
final class RegistrationStrategiesResolveHelper {

    private RegistrationStrategiesResolveHelper() {
        throw new NotInitializableClassException(this.getClass());
    }

    static Set<RegistrationStrategy> resolveRegistrationStrategiesFromArgs(ApplicationArguments args) {
        Set<RegistrationStrategy> resolvedRegistrationStrategies;
        LOG.debug("Passed application arguments: " + Arrays.toString(args.getSourceArgs()));
        Set<String> registrationOptionValues = getRegistrationStrategyOptionValues(args);
        if (registrationOptionValues.isEmpty()) {
            LOG.debug("No plugin registration option values passed. Falling back to using all available strategies");
            resolvedRegistrationStrategies = EnumSet.allOf(RegistrationStrategy.class);
        } else {
            LOG.debug("Passed plugin registration option values: " + registrationOptionValues);
            resolvedRegistrationStrategies = resolveRegistrationStrategiesFromOptionValues(registrationOptionValues);
        }
        LOG.info("Resolved registration strategies: {}", resolvedRegistrationStrategies);
        return resolvedRegistrationStrategies;
    }

    private static Set<String> getRegistrationStrategyOptionValues(ApplicationArguments args) {
        List<String> registrationOptionValues = args.getOptionValues(CL_ARGUMENT_REGISTRATION_STRATEGIES);
        return registrationOptionValues != null ? Set.copyOf(registrationOptionValues) : emptySet();
    }

    private static Set<RegistrationStrategy> resolveRegistrationStrategiesFromOptionValues(Set<String> optionValues) {
        return optionValues
                .stream()
                .map(RegistrationStrategy::fromCommandLineOptionValue)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toUnmodifiableSet());
    }

}
