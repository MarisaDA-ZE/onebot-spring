package top.kirisamemarisa.onebotspring.service.onebot.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.kirisamemarisa.onebotspring.entity.onebot.system.TaskJob;
import top.kirisamemarisa.onebotspring.mapper.onebot.TaskJobMapper;
import top.kirisamemarisa.onebotspring.service.onebot.ITaskJobService;
import top.kirisamemarisa.onebotspring.utils.SnowflakeUtil;

import java.util.Date;

/**
 * @Author: MarisaDAZE
 * @Description: TaskJobServiceImpl.描述
 * @Date: 2024/1/24
 */

@Service
public class TaskJobServiceImpl extends ServiceImpl<TaskJobMapper, TaskJob> implements ITaskJobService {


    @Override
    public Boolean createTaskJob(String taskId, String userId, String groupId, String taskType, String chatType, String template) {
        String id = SnowflakeUtil.nextId();
        TaskJob taskJob = new TaskJob();
        taskJob.setId(id);
        taskJob.setTaskId(taskId);
        taskJob.setTargetId(userId);
        taskJob.setGroupId(groupId);
        taskJob.setTaskType(taskType);
        taskJob.setChatType(chatType);
        taskJob.setTemplateText(template);
        taskJob.setCreateBy("system");
        taskJob.setCreateTime(new Date());
        int insert = baseMapper.insert(taskJob);
        return insert > 0;
    }

    @Override
    public TaskJob getJobByTaskId(String taskId) {
        QueryWrapper<TaskJob> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("TASK_ID", taskId);
        return baseMapper.selectOne(queryWrapper);
    }
}