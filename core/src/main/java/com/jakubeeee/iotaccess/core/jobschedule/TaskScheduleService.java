package com.jakubeeee.iotaccess.core.jobschedule;

import com.jakubeeee.iotaccess.core.data.metadata.scheduledtaskmetadata.ScheduledTaskMetadata;
import com.jakubeeee.iotaccess.core.data.metadata.scheduledtaskmetadata.ScheduledTaskMetadataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

import static java.text.MessageFormat.format;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Slf4j
@RequiredArgsConstructor
@Service
public class TaskScheduleService {

    private static final String SCHEDULED_TASK_DATA_MAP_KEY = "scheduled_task";
    private static final long IMMEDIATE_JOB_DELAY_IN_MILLIS = 2000L;

    private final Scheduler scheduler;

    private final ScheduledTaskMetadataService scheduledTaskMetadataService;

    public void schedule(ScheduledTask task, ScheduledTaskConfig config) {
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
            scheduledTaskMetadataService.setRunningTrue(config.taskIdentifier());
        } catch (SchedulerException e) {
            throw new IllegalStateException(
                    format("Exception during scheduling of a task with identifier \"{0}\". Details in underlying exception message: ",
                            config.taskIdentifier()), e);
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
