package com.jakubeeee.iotaccess.core.taskschedule;

import com.jakubeeee.iotaccess.core.data.metadata.scheduledtaskmetadata.ScheduledTaskMetadata;
import com.jakubeeee.iotaccess.core.data.metadata.scheduledtaskmetadata.ScheduledTaskMetadataService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static java.text.MessageFormat.format;
import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toList;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.Trigger.TriggerState.NORMAL;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.TriggerKey.triggerKey;
import static org.quartz.impl.matchers.GroupMatcher.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class TaskScheduleService {

    private static final String SCHEDULED_TASK_DATA_MAP_KEY = "scheduled_task";
    private static final String TASK_PARAMETERS_DATA_MAP_KEY = "task_parameters";
    private static final long IMMEDIATE_JOB_DELAY_IN_MILLIS = 2000L;
    private static final long MIN_INTERVAL_IN_MILLIS = 500L;

    private final Scheduler scheduler;

    private final ScheduledTaskMetadataService scheduledTaskMetadataService;

    public TaskDetails getSingleTaskDetails(@NonNull ScheduledTaskId identifier) {
        return getSingleTaskDetails(triggerKey(identifier.taskId(), identifier.groupId()));
    }

    private TaskDetails getSingleTaskDetails(TriggerKey key) {
        var identifier = new ScheduledTaskId(key.getName(), key.getGroup());
        return new TaskDetails(identifier, isRunning(key), getInterval(key), getParameters(key));
    }

    @Transactional
    @SneakyThrows(SchedulerException.class)
    public List<TaskDetails> getAllTasksDetails() {
        return scheduler.getJobKeys(anyGroup()).stream()
                .map(this::toTriggerKey)
                .map(this::getSingleTaskDetails)
                .collect(toList());
    }

    private TriggerKey toTriggerKey(JobKey jobKey) {
        return triggerKey(jobKey.getName(), jobKey.getGroup());
    }

    private JobKey toJobKey(TriggerKey triggerKey) {
        return jobKey(triggerKey.getName(), triggerKey.getGroup());
    }

    @SneakyThrows(SchedulerException.class)
    private boolean isRunning(TriggerKey key) {
        return scheduler.getTriggerState(key) == NORMAL;
    }

    private long getInterval(TriggerKey key) {
        return getSimpleTrigger(key).getRepeatInterval();
    }

    @SneakyThrows(SchedulerException.class)
    private TaskParametersContainer getParameters(TriggerKey key) {
        JobDetail jobDetail = scheduler.getJobDetail(toJobKey(key));
        return (TaskParametersContainer) jobDetail.getJobDataMap().get(TASK_PARAMETERS_DATA_MAP_KEY);
    }

    @SneakyThrows(SchedulerException.class)
    private SimpleTrigger getSimpleTrigger(TriggerKey key) {
        return (SimpleTrigger) scheduler.getTrigger(key);
    }

    public void schedule(@NonNull TaskContext<?> context) {
        schedule(context, true);
    }

    public void schedule(@NonNull TaskContext<?> context, boolean running) {
        JobDetail jobDetail = constructJobDetail(context);
        Trigger trigger = constructTrigger(context);
        saveMetadata(context);
        ScheduledTaskId identifier = context.identifier();
        scheduleQuartzJob(identifier, jobDetail, trigger, running);
        String taskId = identifier.taskId();
        String groupId = identifier.groupId();
        LOG.trace("Scheduled a new task: \"{}\" of group \"{}\"", taskId, groupId);
    }

    private JobDetail constructJobDetail(TaskContext<?> context) {
        ScheduledTaskId identifier = context.identifier();

        var jobDataMap = new JobDataMap();
        jobDataMap.put(SCHEDULED_TASK_DATA_MAP_KEY, context.task());
        if (context instanceof ParameterizedTaskContext parameterizedContext)
            jobDataMap.put(TASK_PARAMETERS_DATA_MAP_KEY, parameterizedContext.taskProperties());
        else if (context instanceof UnmodifiableTaskContext)
            jobDataMap.put(TASK_PARAMETERS_DATA_MAP_KEY, new TaskParametersContainer(emptySet()));

        return newJob(QuartzJobDelegate.class)
                .withIdentity(identifier.taskId(), identifier.groupId())
                .usingJobData(jobDataMap)
                .build();
    }

    private Trigger constructTrigger(TaskContext<?> context) {
        return constructTrigger(context.identifier(), context.interval());
    }

    private Trigger constructTrigger(ScheduledTaskId identifier, long interval) {
        if (interval > 0 && interval < MIN_INTERVAL_IN_MILLIS)
            throw new IllegalStateException(
                    format("Interval for scheduled task \"{0}\" of group \"{1}\" cannot be lower that \"{2}\". Currently set interval: \"{3}\"",
                            identifier.taskId(), identifier.groupId(), MIN_INTERVAL_IN_MILLIS, interval));

        TriggerBuilder<Trigger> builder = newTrigger()
                .withIdentity(identifier.taskId(), identifier.groupId());

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

    private void saveMetadata(TaskContext<?> context) {
        ScheduledTaskId identifier = context.identifier();
        scheduledTaskMetadataService
                .save(new ScheduledTaskMetadata(identifier.taskId(), identifier.groupId(), context.interval()));
    }

    @SneakyThrows(SchedulerException.class)
    private void scheduleQuartzJob(ScheduledTaskId identifier, JobDetail jobDetail, Trigger trigger, boolean running) {
        scheduler.scheduleJob(jobDetail, trigger);
        if (running)
            scheduledTaskMetadataService.setRunningTrueForTask(identifier.taskId(), identifier.groupId());
        else
            scheduler.pauseJob(jobKey(identifier.taskId(), identifier.groupId()));
    }

    @SneakyThrows(SchedulerException.class)
    public void executeImmediately(@NonNull String taskId, @NonNull String groupId) {
        scheduler.triggerJob(jobKey(taskId, groupId));
        LOG.trace("Task executed immediately: \"{}\" of group \"{}\"", taskId, groupId);
    }

    @SneakyThrows(SchedulerException.class)
    public void pauseAll() {
        scheduler.pauseAll();
        scheduledTaskMetadataService.setRunningFalseForAll();
        LOG.trace("Paused all tasks");
    }

    @SneakyThrows(SchedulerException.class)
    public void pauseGroup(@NonNull String groupId) {
        scheduler.pauseTriggers(triggerGroupEquals(groupId));
        scheduledTaskMetadataService.setRunningFalseForGroup(groupId);
        LOG.trace("Paused all tasks of group \"{}\"", groupId);
    }

    @SneakyThrows(SchedulerException.class)
    public void pauseTask(@NonNull String taskId, @NonNull String groupId) {
        scheduler.pauseTrigger(triggerKey(taskId, groupId));
        scheduledTaskMetadataService.setRunningFalseForTask(taskId, groupId);
        LOG.trace("Paused task \"{}\" of group \"{}\"", taskId, groupId);
    }

    @SneakyThrows(SchedulerException.class)
    public void resumeAll() {
        scheduler.resumeAll();
        scheduledTaskMetadataService.setRunningTrueForAll();
        LOG.trace("Resumed all tasks");
    }

    @SneakyThrows(SchedulerException.class)
    public void resumeGroup(@NonNull String groupId) {
        scheduler.resumeTriggers(triggerGroupEquals(groupId));
        scheduledTaskMetadataService.setRunningTrueForGroup(groupId);
        LOG.trace("Resumed all tasks of group \"{}\"", groupId);
    }

    @SneakyThrows(SchedulerException.class)
    public void resumeTask(@NonNull String taskId, @NonNull String groupId) {
        scheduler.resumeTrigger(triggerKey(taskId, groupId));
        scheduledTaskMetadataService.setRunningTrueForTask(taskId, groupId);
        LOG.trace("Resumed task \"{}\" of group \"{}\"", taskId, groupId);
    }

    @SneakyThrows(SchedulerException.class)
    public void reschedule(@NonNull ScheduledTaskId identifier, long interval) {
        Trigger trigger = constructTrigger(identifier, interval);
        String taskId = identifier.taskId();
        String groupId = identifier.groupId();
        TriggerKey triggerKey = triggerKey(taskId, groupId);
        TaskDetails taskDetails = getSingleTaskDetails(triggerKey);
        boolean running = taskDetails.running();
        scheduler.rescheduleJob(triggerKey, trigger);
        if (!running)
            scheduler.pauseJob(toJobKey(triggerKey));
        scheduledTaskMetadataService.updateInterval(taskId, groupId, interval);
        LOG.trace("Rescheduled task \"{}\" of group \"{}\" with new interval \"{}\"", taskId, groupId, interval);
    }

    @Synchronized
    @SneakyThrows(SchedulerException.class)
    public void swapDynamicParameter(ScheduledTaskId identifier, String fullParameterName, String newValue) {
        String taskId = identifier.taskId();
        String groupId = identifier.groupId();
        JobDetail jobDetail = scheduler.getJobDetail(jobKey(taskId, groupId));
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        var container = (TaskParametersContainer) jobDataMap.get(TASK_PARAMETERS_DATA_MAP_KEY);
        var updatedContainer = container.withUpdatedDynamically(fullParameterName, newValue);
        jobDataMap.put(TASK_PARAMETERS_DATA_MAP_KEY, updatedContainer);
        Trigger trigger = scheduler.getTrigger(triggerKey(taskId, groupId));
        scheduler.scheduleJob(jobDetail, singleton(trigger), true);
        LOG.trace(
                "Dynamic parameter value \"{}\" of scheduled task \"{}\" of group \"{}\" successfully swapped to \"{}\"",
                fullParameterName, taskId, groupId, newValue);
    }

    @SneakyThrows(SchedulerException.class)
    public void deleteGroup(@NonNull String groupId) {
        Set<JobKey> keysOfJobsToDelete = scheduler.getJobKeys(groupEquals(groupId));
        scheduler.deleteJobs(List.copyOf(keysOfJobsToDelete));
        scheduledTaskMetadataService.deleteAllOfGroup(groupId);
        LOG.trace("Deleted all tasks of group \"{}\"", groupId);
    }

    @SneakyThrows(SchedulerException.class)
    public void deleteTask(@NonNull String taskId, @NonNull String groupId) {
        scheduler.deleteJob(jobKey(taskId, groupId));
        scheduledTaskMetadataService.delete(taskId, groupId);
        LOG.trace("Deleted task \"{}\" of group \"{}\"", taskId, groupId);
    }

    @PersistJobDataAfterExecution
    private static final class QuartzJobDelegate implements Job {

        @Override
        public void execute(JobExecutionContext context) {
            JobDataMap dataMap = context.getJobDetail().getJobDataMap();
            var task = (ScheduledTask) dataMap.get(SCHEDULED_TASK_DATA_MAP_KEY);
            var properties = (TaskParametersContainer) dataMap.get(TASK_PARAMETERS_DATA_MAP_KEY);
            if (task instanceof ParameterizedTask parameterizedTask)
                parameterizedTask.execute(properties);
            else if (task instanceof UnmodifiableTask unmodifiableTask)
                unmodifiableTask.execute();
        }

    }

}
