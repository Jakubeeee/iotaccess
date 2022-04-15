package com.jakubeeee.iotaccess.core.dynamicconfig.generator;

import com.jakubeeee.iotaccess.core.dynamicconfig.DynamicConfigContainer;
import com.jakubeeee.iotaccess.core.dynamicconfig.DynamicConfigEntry;
import com.jakubeeee.iotaccess.core.taskschedule.DynamicTaskParameter;
import com.jakubeeee.iotaccess.core.taskschedule.ScheduledTaskId;
import com.jakubeeee.iotaccess.core.taskschedule.TaskDetails;
import com.jakubeeee.iotaccess.core.taskschedule.TaskScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
abstract class BaseDynamicConfigGenerator implements DynamicConfigGenerator {

    private final TaskScheduleService scheduleService;

    @Transactional
    @Override
    public void generate() {
        LOG.trace("Invoked execution of \"{}\" with identifier \"{}\"", this.getClass().getSimpleName(),
                getIdentifier());
        DynamicConfigContainer configContainer = buildConfigContainer();
        LOG.trace("Created configuration container: \"{}\"", configContainer);
        generateConfiguration(configContainer);
    }

    private DynamicConfigContainer buildConfigContainer() {
        List<TaskDetails> tasksDetails = scheduleService.getAllTasksDetails();
        return tasksDetailsToContainer(tasksDetails);
    }

    private DynamicConfigContainer tasksDetailsToContainer(List<TaskDetails> tasksDetails) {
        List<DynamicConfigEntry> entries = tasksDetails.stream()
                .flatMap(this::taskDetailsToEntryStream)
                .sorted()
                .toList();
        return new DynamicConfigContainer(entries);
    }

    private Stream<DynamicConfigEntry> taskDetailsToEntryStream(TaskDetails taskDetails) {
        ScheduledTaskId identifier = taskDetails.identifier();
        String keyPrefix = identifier.groupId() + "." + identifier.taskId();

        String runningKey = keyPrefix + ".schedule.running";
        var runningEntry = new DynamicConfigEntry(runningKey, String.valueOf(taskDetails.running()));
        String intervalKey = keyPrefix + ".schedule.interval";
        var intervalEntry = new DynamicConfigEntry(intervalKey, String.valueOf(taskDetails.interval()));

        Stream<DynamicConfigEntry> parametersEntriesStream = taskDetails.parameterContainer().parameters()
                .stream()
                .filter(DynamicTaskParameter.class::isInstance)
                .map(DynamicTaskParameter.class::cast)
                .map(parameter -> toDynamicEntry(keyPrefix, parameter));

        return Stream.concat(
                parametersEntriesStream,
                Stream.of(runningEntry, intervalEntry));
    }

    private DynamicConfigEntry toDynamicEntry(String keyPrefix, DynamicTaskParameter parameter) {
        return new DynamicConfigEntry(keyPrefix + "." + parameter.groupName() + "." + parameter.name(),
                parameter.value());
    }

    abstract void generateConfiguration(DynamicConfigContainer configContainer);

}
