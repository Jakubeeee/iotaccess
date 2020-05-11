package com.jakubeeee.iotaccess.core.taskschedule;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DynamicConfigurationRestController {

    private final TaskScheduleService scheduleService;

    @PostMapping("config/tasks/executetask")
    public void pauseAllScheduledTasks(@RequestParam("taskid") String taskId, @RequestParam("groupid") String groupId) {
        scheduleService.executeImmediately(taskId, groupId);
    }

    @PostMapping("config/tasks/pauseall")
    public void pauseAllScheduledTasks() {
        scheduleService.pauseAll();
    }

    @PostMapping("config/tasks/pausegroup")
    public void pauseScheduledTaskGroup(@RequestParam("groupid") String groupId) {
        scheduleService.pauseGroup(groupId);
    }

    @PostMapping("config/tasks/pausetask")
    public void pauseScheduledTask(@RequestParam("taskid") String taskId, @RequestParam("groupid") String groupId) {
        scheduleService.pauseTask(taskId, groupId);
    }

    @PostMapping("config/tasks/resumeall")
    public void resumeAllScheduledTasks() {
        scheduleService.resumeAll();
    }

    @PostMapping("config/tasks/resumegroup")
    public void resumeScheduledTaskGroup(@RequestParam("groupid") String groupId) {
        scheduleService.resumeGroup(groupId);
    }

    @PostMapping("config/tasks/resumetask")
    public void resumeScheduledTask(@RequestParam("taskid") String taskId, @RequestParam("groupid") String groupId) {
        scheduleService.resumeTask(taskId, groupId);
    }

    @PostMapping("config/tasks/deletegroup")
    public void deleteScheduledTaskGroup(@RequestParam("groupid") String groupId) {
        scheduleService.deleteGroup(groupId);
    }

    @PostMapping("config/tasks/deletetask")
    public void deleteScheduledTask(@RequestParam("taskid") String taskId, @RequestParam("groupid") String groupId) {
        scheduleService.deleteTask(taskId, groupId);
    }

    @PostMapping("config/tasks/rescheduletask")
    public void rescheduleTask(@RequestParam("taskid") String taskId,
                               @RequestParam("groupid") String groupId,
                               @RequestParam("interval") long newInterval) {
        scheduleService.reschedule(new ScheduledTaskConfig(taskId, groupId, newInterval));
    }

}
