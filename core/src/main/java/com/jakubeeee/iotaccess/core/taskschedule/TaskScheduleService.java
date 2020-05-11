package com.jakubeeee.iotaccess.core.taskschedule;

import com.jakubeeee.iotaccess.core.data.metadata.scheduledtaskmetadata.ScheduledTaskMetadata;
import com.jakubeeee.iotaccess.core.data.metadata.scheduledtaskmetadata.ScheduledTaskMetadataService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.TriggerKey.triggerKey;
import static org.quartz.impl.matchers.GroupMatcher.groupEquals;
import static org.quartz.impl.matchers.GroupMatcher.triggerGroupEquals;

@Slf4j
@RequiredArgsConstructor
@Service
public class TaskScheduleService {

    private static final String SCHEDULED_TASK_DATA_MAP_KEY = "scheduled_task";
    private static final long IMMEDIATE_JOB_DELAY_IN_MILLIS = 2000L;

    private final Scheduler scheduler;

    private final ScheduledTaskMetadataService scheduledTaskMetadataService;

    public void schedule(@NonNull ScheduledTask task, @NonNull ScheduledTaskConfig config) {
        JobDetail jobDetail = constructJobDetail(task, config);
        Trigger trigger = constructTrigger(config);
        saveMetadata(config);
        scheduleQuartzJob(jobDetail, trigger, config);
    }

    private JobDetail constructJobDetail(ScheduledTask task, ScheduledTaskConfig config) {
        var jobDataMap = new JobDataMap();
        jobDataMap.put(SCHEDULED_TASK_DATA_MAP_KEY, task);

        return newJob(QuartzJobDelegate.class)
                .withIdentity(config.taskIdentifier(), config.groupIdentifier())
                .usingJobData(jobDataMap)
                .build();
    }

    private Trigger constructTrigger(ScheduledTaskConfig config) {

        TriggerBuilder<Trigger> builder = newTrigger()
                .withIdentity(config.taskIdentifier(), config.groupIdentifier());

        long interval = config.interval();
        if (interval > 0)
            builder.startAt(calculateFirstScheduledStartDate(interval))
                    .withSchedule(simpleSchedule()
                            .withIntervalInMilliseconds(interval)
                            .repeatForever());
        else
            builder.startAt(calculateImmediateStartDate());

        return builder.build();
    }

    private Date calculateFirstScheduledStartDate(long interval) {
        return Date.from(Instant.now().plusMillis(interval));
    }

    private Date calculateImmediateStartDate() {
        return Date.from(Instant.now().plusMillis(IMMEDIATE_JOB_DELAY_IN_MILLIS));
    }

    private void saveMetadata(ScheduledTaskConfig config) {
        scheduledTaskMetadataService.save(
                new ScheduledTaskMetadata(config.taskIdentifier(), config.groupIdentifier(), config.interval()));
    }

    private void scheduleQuartzJob(JobDetail jobDetail, Trigger trigger, ScheduledTaskConfig config) {
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new ScheduledTaskOperationException(
                    "Task with identifier \"{0}\" of group \"{1}\" could not be scheduled. Details in underlying exception message: ",
                    e, config.taskIdentifier(), config.groupIdentifier());
        }
        scheduledTaskMetadataService.setRunningTrueForTask(config.taskIdentifier(), config.groupIdentifier());
    }

    public void executeImmediately(@NonNull String taskId, @NonNull String groupId) {
        tryExecuteImmediately(taskId, groupId);
    }

    private void tryExecuteImmediately(String taskId, String groupId) {
        try {
            scheduler.triggerJob(jobKey(taskId, groupId));
        } catch (SchedulerException e) {
            throw new ScheduledTaskOperationException(
                    "Task with identifier \"{0}\" of group \"{1}\" could not be executed immediately. Details in underlying exception message: ",
                    e, taskId, groupId);
        }
    }

    public void pauseAll() {
        tryPauseAll();
        scheduledTaskMetadataService.setRunningFalseForAll();
    }

    private void tryPauseAll() {
        try {
            scheduler.pauseAll();
        } catch (SchedulerException e) {
            throw new ScheduledTaskOperationException(
                    "All tasks could not be paused. Details in underlying exception message: ", e);
        }
    }

    public void pauseGroup(@NonNull String groupId) {
        tryPauseGroup(groupId);
        scheduledTaskMetadataService.setRunningFalseForGroup(groupId);
    }

    private void tryPauseGroup(String groupId) {
        try {
            scheduler.pauseTriggers(triggerGroupEquals(groupId));
        } catch (SchedulerException e) {
            throw new ScheduledTaskOperationException(
                    "Task group \"{1}\" could not be paused. Details in underlying exception message: ", e, groupId);
        }
    }

    public void pauseTask(@NonNull String taskId, @NonNull String groupId) {
        tryPauseTask(taskId, groupId);
        scheduledTaskMetadataService.setRunningFalseForTask(taskId, groupId);
    }

    private void tryPauseTask(String taskId, String groupId) {
        try {
            scheduler.pauseTrigger(triggerKey(taskId, groupId));
        } catch (SchedulerException e) {
            throw new ScheduledTaskOperationException(
                    "Task with identifier \"{0}\" of group \"{1}\" could not be paused. Details in underlying exception message: ",
                    e, taskId, groupId);
        }
    }

    public void resumeAll() {
        tryResumeAll();
        scheduledTaskMetadataService.setRunningTrueForAll();
    }

    private void tryResumeAll() {
        try {
            scheduler.resumeAll();
        } catch (SchedulerException e) {
            throw new ScheduledTaskOperationException(
                    "All tasks could not be resumed. Details in underlying exception message: ", e);
        }
    }

    public void resumeGroup(@NonNull String groupId) {
        tryResumeGroup(groupId);
        scheduledTaskMetadataService.setRunningTrueForGroup(groupId);
    }

    private void tryResumeGroup(String groupId) {
        try {
            scheduler.resumeTriggers(triggerGroupEquals(groupId));
        } catch (SchedulerException e) {
            throw new ScheduledTaskOperationException(
                    "Task group \"{1}\" could not be resumed. Details in underlying exception message: ", e, groupId);
        }
    }

    public void resumeTask(@NonNull String taskId, @NonNull String groupId) {
        tryResumeTask(taskId, groupId);
        scheduledTaskMetadataService.setRunningTrueForTask(taskId, groupId);
    }

    private void tryResumeTask(String taskId, String groupId) {
        try {
            scheduler.resumeTrigger(triggerKey(taskId, groupId));
        } catch (SchedulerException e) {
            throw new ScheduledTaskOperationException(
                    "Task with identifier \"{0}\" of group \"{1}\" could not be resumed. Details in underlying exception message: ",
                    e, taskId, groupId);
        }
    }

    public void reschedule(@NonNull ScheduledTaskConfig newConfig) {
        Trigger trigger = constructTrigger(newConfig);
        String taskIdentifier = newConfig.taskIdentifier();
        String taskGroupIdentifier = newConfig.groupIdentifier();
        tryReschedule(triggerKey(taskIdentifier, taskGroupIdentifier), trigger);
        scheduledTaskMetadataService.updateInterval(taskIdentifier, taskGroupIdentifier, newConfig.interval());
    }

    private void tryReschedule(TriggerKey triggerKey, Trigger newTrigger) {
        try {
            scheduler.rescheduleJob(triggerKey, newTrigger);
        } catch (SchedulerException e) {
            throw new ScheduledTaskOperationException(
                    "Task with identifier \"{0}\" of group \"{1}\" could not be rescheduled with \"{2}\" interval. Details in underlying exception message: ",
                    e, triggerKey.getName(), triggerKey.getGroup());
        }
    }

    public void deleteGroup(@NonNull String groupId) {
        tryDeleteGroup(groupId);
        scheduledTaskMetadataService.deleteAllOfGroup(groupId);
    }

    private void tryDeleteGroup(String groupId) {
        Set<JobKey> keysOfJobsToDelete;
        try {
            keysOfJobsToDelete = scheduler.getJobKeys(groupEquals(groupId));
        } catch (SchedulerException e) {
            throw new ScheduledTaskOperationException(
                    "Tasks of group \"{1}\" could not be fetched. Details in underlying exception message: ",
                    e, groupId);
        }
        try {
            scheduler.deleteJobs(List.copyOf(keysOfJobsToDelete));
        } catch (SchedulerException e) {
            throw new ScheduledTaskOperationException(
                    "Task group \"{1}\" could not be deleted. Details in underlying exception message: ",
                    e, groupId);
        }
    }

    public void deleteTask(@NonNull String taskId, @NonNull String groupId) {
        tryDeleteTask(taskId, groupId);
        scheduledTaskMetadataService.delete(taskId, groupId);
    }

    private void tryDeleteTask(String taskId, String groupId) {
        try {
            scheduler.deleteJob(jobKey(taskId, groupId));
        } catch (SchedulerException e) {
            throw new ScheduledTaskOperationException(
                    "Task with identifier \"{0}\" of group \"{1}\" could not be deleted. Details in underlying exception message: ",
                    e, taskId, groupId);
        }
    }

    private static final class QuartzJobDelegate implements Job {

        @Override
        public void execute(JobExecutionContext context) {
            JobDataMap dataMap = context.getJobDetail().getJobDataMap();
            var task = (ScheduledTask) dataMap.get(SCHEDULED_TASK_DATA_MAP_KEY);
            task.execute();
        }

    }

}
