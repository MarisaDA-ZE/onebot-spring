package top.kirisamemarisa.onebotspring.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import top.kirisamemarisa.onebotspring.entity.system.ScheduledTask;
import top.kirisamemarisa.onebotspring.mapper.ScheduledTaskMapper;
import top.kirisamemarisa.onebotspring.service.IScheduledTaskService;

/**
 * @Author: MarisaDAZE
 * @Description: DynamicScheduleServiceImpl.描述
 * @Date: 2024/1/24
 */

@Service
public class ScheduledTaskServiceImpl extends ServiceImpl<ScheduledTaskMapper, ScheduledTask>
        implements IScheduledTaskService {
    private final TaskScheduler taskScheduler;

    @Autowired
    public ScheduledTaskServiceImpl(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }


    /**
     * 创建定时任务
     *
     * @param cronText cron表达式
     * @param task     执行的任务（lambda方法）
     */
    private void createTask(String cronText, Runnable task) {
        // 使用CronTrigger指定cron表达式
        CronTrigger trigger = new CronTrigger(cronText);
        // 使用TaskScheduler调度定时任务
        taskScheduler.schedule(task, trigger);

    }

}