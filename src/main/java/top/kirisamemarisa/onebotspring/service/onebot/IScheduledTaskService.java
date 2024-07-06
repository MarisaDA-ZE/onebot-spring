package top.kirisamemarisa.onebotspring.service.onebot;

import com.baomidou.mybatisplus.extension.service.IService;
import top.kirisamemarisa.onebotspring.entity.onebot.system.ScheduledTask;

/**
 * @Author: MarisaDAZE
 * @Description: IBotConfigService.描述
 * @Date: 2024/1/23
 */
public interface IScheduledTaskService extends IService<ScheduledTask> {

    /**
     * 创建一个文本消息的定时任务
     *
     * @param cron     cron表达式
     * @param userId 创建人ID
     * @param groupId Q群ID
     * @param template 消息模板
     * @param remark   任务备注
     * @return .
     */
    boolean createTextTask(String cron,String userId,String groupId, String template, String remark);

    /**
     * 按用户ID启动文本消息的定时任务
     * @param userId    任务ID
     */
    void startTextTaskByUserId(String userId);

    /**
     * 按ID启动一个文本消息的定时任务
     * @param taskId    任务ID
     */
    void startTextTaskById(String taskId);

    /**
     * 启动所有的定时任务
     */
    void startAllTask();
}
