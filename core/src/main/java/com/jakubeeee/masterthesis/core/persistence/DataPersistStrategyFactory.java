package com.jakubeeee.masterthesis.core.persistence;

import com.jakubeeee.masterthesis.pluginapi.converter.DataType;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static java.text.MessageFormat.format;
import static java.util.stream.Collectors.toUnmodifiableList;

@Slf4j
@Component
public class DataPersistStrategyFactory {

    private final Set<DataPersistStrategy> strategies;

    public DataPersistStrategyFactory(Set<DataPersistStrategy> strategies) {
        this.strategies = strategies;
        validateInjectedStrategies(strategies);
        LOG.debug("Data persist strategies resolved by \"{}\": \"{}\"", this.getClass().getSimpleName(), strategies);
    }

    public DataPersistStrategy getStrategy(@NonNull DataType dataType) {
        return strategies.stream()
                .filter(strategy -> strategy.getSupportedDataType().equals(dataType))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        format("No data persist strategy found for data type \"{0}\"", dataType)));
    }

    private void validateInjectedStrategies(Set<DataPersistStrategy> strategies) {
        List<DataType> supportedDataTypes = extractDataTypes(strategies);
        var resolvedDataTypes = EnumSet.noneOf(DataType.class);
        for (var dataType : supportedDataTypes) {
            if (resolvedDataTypes.contains(dataType))
                throw new IllegalStateException(format("Only one data persist strategy is allowed per data type. " +
                        "There is already one registered for data type: \"{0}\"", dataType));
            else
                resolvedDataTypes.add(dataType);
        }
    }

    private List<DataType> extractDataTypes(Set<DataPersistStrategy> strategies) {
        return strategies.stream()
                .map(DataPersistStrategy::getSupportedDataType)
                .collect(toUnmodifiableList());
    }

}
