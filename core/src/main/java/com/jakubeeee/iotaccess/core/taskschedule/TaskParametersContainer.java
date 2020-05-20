package com.jakubeeee.iotaccess.core.taskschedule;

import lombok.NonNull;

import java.util.List;
import java.util.Set;

import static java.text.MessageFormat.format;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public record TaskParametersContainer(Set<TaskParameter<?>>parameters) {

    public String get(@NonNull String fullName) {
        return get(fullName, String.class);
    }

    public <T> T get(@NonNull String fullName, @NonNull Class<T> valueType) {
        String[] splitFullName = fullName.split("\\.");
        return get(splitFullName[0], splitFullName[1], valueType);
    }

    public String get(@NonNull String groupName, @NonNull String name) {
        return get(groupName, name, String.class);
    }

    public <T> T get(@NonNull String groupName, @NonNull String name, Class<T> valueType) {
        List<T> matchingParameters = parameters.stream()
                .filter(parameter -> parameter.groupName().equals(groupName))
                .filter(parameter -> parameter.name().equals(name))
                .filter(parameter -> valueType.isAssignableFrom(parameter.value().getClass()))
                .map(TaskParameter::value)
                .map(valueType::cast)
                .collect(toList());

        if (matchingParameters.size() == 0)
            throw new IllegalStateException(format(
                    "There is no value for task parameter with name \"{0}\" and group \"{1}\"", name, groupName));
        else if (matchingParameters.size() > 1)
            throw new IllegalStateException(format(
                    "There are multiple values for task parameter with name \"{0}\" and group \"{1}\"",
                    name, groupName));
        else return matchingParameters.get(0);
    }

    public TaskParametersContainer withUpdatedDynamically(@NonNull String fullName, @NonNull String value) {
        String[] splitFullName = fullName.split("\\.");
        return withUpdatedDynamically(splitFullName[0], splitFullName[1], value);
    }

    public TaskParametersContainer withUpdatedDynamically(@NonNull String groupName, @NonNull String name,
                                                          @NonNull String newValue) {
        List<DynamicTaskParameter> matchingParameters = parameters.stream()
                .filter(DynamicTaskParameter.class::isInstance)
                .map(DynamicTaskParameter.class::cast)
                .filter(dynamicParameter -> dynamicParameter.groupName().equals(groupName))
                .filter(dynamicParameter -> dynamicParameter.name().equals(name))
                .collect(toList());

        if (matchingParameters.size() == 0)
            throw new IllegalStateException(format(
                    "There is no task parameter with name \"{0}\" and group \"{1}\"", name, groupName));
        else if (matchingParameters.size() > 1)
            throw new IllegalStateException(format(
                    "There are multiple task parameters with name \"{0}\" and group \"{1}\"", name, groupName));

        var matchingParameter = matchingParameters.get(0);
        var updatedParameter = new DynamicTaskParameter(groupName, name, newValue);

        Set<TaskParameter<?>> updatedParameters = parameters.stream()
                .filter(parameter -> !parameter.equals(matchingParameter))
                .collect(toSet());
        updatedParameters.add(updatedParameter);

        return new TaskParametersContainer(updatedParameters);
    }

}
