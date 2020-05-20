package com.jakubeeee.iotaccess.core.dynamicconfig.reader;

import com.jakubeeee.iotaccess.core.dynamicconfig.DynamicConfigContainer;
import com.jakubeeee.iotaccess.core.taskschedule.ScheduledTaskId;
import com.jakubeeee.iotaccess.core.taskschedule.TaskDetails;
import com.jakubeeee.iotaccess.core.taskschedule.TaskScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
abstract class BaseDynamicConfigReader implements DynamicConfigReader {

    private final TaskScheduleService scheduleService;

    @Transactional
    @Override
    public void read() {
        LOG.trace("Invoked execution of \"\"{}\"\" with identifier \"\"{}\"\"", this.getClass().getSimpleName(),
                getIdentifier());
        DynamicConfigContainer configContainer = scanForConfiguration();
        LOG.trace("Found configuration container: \"\"{}\"\"", configContainer);
        updateTasks(configContainer);
    }

    abstract DynamicConfigContainer scanForConfiguration();

    private void updateTasks(DynamicConfigContainer configContainer) {
        for (var entry : configContainer.entries()) {
            String key = entry.key();
            String value = entry.value();
            String[] splitKey = key.split("\\.");
            String groupId = splitKey[0];
            String taskId = splitKey[1];
            String parameterGroup = splitKey[2];
            String parameterName = splitKey[3];
            String fullPropertyName = parameterGroup + "." + parameterName;
            TaskDetails currentTaskDetails =
                    scheduleService.getSingleTaskDetails(new ScheduledTaskId(taskId, groupId));

            if (fullPropertyName.equals("schedule.running")) {
                updateRunning(currentTaskDetails, value);
            } else if (fullPropertyName.equals("schedule.interval")) {
                updateInterval(currentTaskDetails, value);
            } else {
                updateDynamicProperty(currentTaskDetails, fullPropertyName, value);
            }

        }
    }

    private void updateRunning(TaskDetails taskDetails, String value) {
        boolean newRunningValue = Boolean.parseBoolean(value);
        if (taskDetails.running() != newRunningValue) {
            var taskId = taskDetails.identifier().taskId();
            var groupId = taskDetails.identifier().groupId();
            if (newRunningValue) {
                scheduleService.resumeTask(taskId, groupId);
                LOG.debug("Scheduled task \"{}\" of group \"{}\" resumed successfully by \"{}\"", taskId, groupId,
                        getIdentifier());
            } else {
                scheduleService.pauseTask(taskId, groupId);
                LOG.debug("Scheduled task \"{}\" of group \"{}\" paused successfully by \"{}\"", taskId, groupId,
                        getIdentifier());
            }
        }
    }

    private void updateInterval(TaskDetails taskDetails, String value) {
        long currentIntervalValue = taskDetails.interval();
        long newIntervalValue = Long.parseLong(value);
        if (currentIntervalValue != newIntervalValue) {
            var taskId = taskDetails.identifier().taskId();
            var groupId = taskDetails.identifier().groupId();
            scheduleService.reschedule(new ScheduledTaskId(taskId, groupId), newIntervalValue);
            LOG.debug(
                    "Interval of scheduled task \"{}\" of group \"{}\" successfully changed from \"{}\" to \"{}\" by \"{}\"",
                    taskId, groupId, currentIntervalValue, newIntervalValue, getIdentifier());
        }
    }

    private void updateDynamicProperty(TaskDetails taskDetails, String fullParameterName, String newValue) {
        String currentValue = taskDetails.parameterContainer().get(fullParameterName);
        if (!currentValue.equals(newValue)) {
            var taskId = taskDetails.identifier().taskId();
            var groupId = taskDetails.identifier().groupId();
            scheduleService.swapDynamicParameter(new ScheduledTaskId(taskId, groupId), fullParameterName, newValue);
            LOG.debug(
                    "Dynamic parameter value \"{}\" of scheduled task \"{}\" of group \"{}\" successfully changed from \"{}\" to \"{}\" by \"{}\"",
                    fullParameterName, taskId, groupId, currentValue, newValue, getIdentifier());
        }
    }

}
