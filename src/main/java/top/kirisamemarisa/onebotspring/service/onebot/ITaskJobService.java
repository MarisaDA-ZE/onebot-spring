package top.kirisamemarisa.onebotspring.service.onebot;

import com.baomidou.mybatisplus.extension.service.IService;
import top.kirisamemarisa.onebotspring.entity.onebot.system.TaskJob;

/**
 * @Author: MarisaDAZE
 * @Description: ITaskJobService.描述
 * @Date: 2024/1/23
 */
public interface ITaskJobService extends IService<TaskJob> {

    /**
     * 创建一个定时任务内容
     *
     * @param taskId   定时任务ID
     * @param userId 用户ID
     * @param groupId Q群ID
     * @param taskType 任务类型
     * @param chatType 聊天类型
     * @param template 内容模板
     * @return ．
     */
    Boolean createTaskJob(String taskId,String userId,String groupId, String taskType, String chatType, String template);

    /**
     * 根据定时任务Id获取任务内容
     *
     * @param taskId 任务ID
     * @return .
     */
    TaskJob getJobByTaskId(String taskId);
}
