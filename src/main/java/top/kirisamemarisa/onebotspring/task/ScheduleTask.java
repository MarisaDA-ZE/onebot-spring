package top.kirisamemarisa.onebotspring.task;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * @Author: MarisaDAZE
 * @Description: 定时任务
 * @Date: 2024/1/24
 */
@Data
@Component
public class ScheduleTask implements SchedulingConfigurer {

    @Value("${trigger.cron}")
    private String cron;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        // 动态使用cron表达式设置循环间隔
        taskRegistrar.addTriggerTask(() -> System.out.println("开始执行定时任务： " + simpleDateFormat.format(new Date())), new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                // 使用CronTrigger触发器，可动态修改cron表达式来操作循环规则
                CronTrigger cronTrigger = new CronTrigger(cron);
                return cronTrigger.nextExecutionTime(triggerContext);
            }

            @Override
            public Instant nextExecution(TriggerContext triggerContext) {
                return triggerContext.lastActualExecution();
            }
        });
    }
}