package top.kirisamemarisa.onebotspring.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kirisamemarisa.onebotspring.entity.system.BotConfig;
import top.kirisamemarisa.onebotspring.entity.system.ScheduledTask;
import top.kirisamemarisa.onebotspring.entity.system.TaskJob;
import top.kirisamemarisa.onebotspring.mapper.ScheduledTaskMapper;
import top.kirisamemarisa.onebotspring.service.IBotConfigService;
import top.kirisamemarisa.onebotspring.service.IScheduledTaskService;
import top.kirisamemarisa.onebotspring.service.ITaskJobService;
import top.kirisamemarisa.onebotspring.utils.HttpUtils;
import top.kirisamemarisa.onebotspring.utils.MassageTemplate;
import top.kirisamemarisa.onebotspring.utils.SnowflakeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Resource
    private ITaskJobService taskJobService;

    @Resource
    private IBotConfigService configService;

    @Transactional
    @Override
    public boolean createTextTask(String cron, String userId, String groupId, String template, String remark) {
        ScheduledTask scheduledTask = new ScheduledTask();
        String taskId = SnowflakeUtil.nextIdOne();
        scheduledTask.setId(taskId);
        scheduledTask.setUserId(userId);
        scheduledTask.setCronText(cron);
        scheduledTask.setRemark(remark);
        scheduledTask.setCreateBy("system");
        scheduledTask.setCreateTime(new Date());
        int insert = baseMapper.insert(scheduledTask);
        Boolean createJob = taskJobService.createTaskJob(taskId, userId, groupId, "massage", "group", template);
        return (insert > 0) && createJob;
    }

    @Override
    public void startTextTaskByUserId(String userId) {
        QueryWrapper<ScheduledTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("USER_ID", userId);
        List<ScheduledTask> tasks = baseMapper.selectList(queryWrapper);
        System.out.println(tasks);
        tasks.forEach(e -> startTextTaskById(e.getId()));
    }

    @Override
    public void startTextTaskById(String taskId) {
        ScheduledTask task = baseMapper.selectById(taskId);
        QueryWrapper<TaskJob> jqWrapper = new QueryWrapper<>();
        jqWrapper.eq("TASK_ID", task.getId());
        TaskJob job = taskJobService.getOne(jqWrapper);
        QueryWrapper<BotConfig> bqWrapper = new QueryWrapper<>();
        bqWrapper.eq("USER_ID", job.getGroupId());
        BotConfig config = configService.getOne(bqWrapper);
        String url = config.getClientUrl() + "/send_msg";
        System.out.println("定时任务启动成功...");
        createTask(task.getCronText(), () -> {
            List<?> list = JSONObject.parseObject(job.getTemplateText(), List.class);
            List<String> strings = new ArrayList<>();
            System.out.println(list);
            list.forEach(e -> strings.add(JSONObject.toJSONString(e)));
            String template = MassageTemplate.customGroupTemplate(job.getGroupId(), strings);
            System.out.println(template);
            HttpUtils.post(url, template);
        });
    }

    @Override
    public void startAllTask() {

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