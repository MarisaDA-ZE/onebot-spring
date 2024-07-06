package top.kirisamemarisa.onebotspring.entity.onebot.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: MarisaDAZE
 * @Description: TaskJob.描述
 * @Date: 2024/1/26
 */
@Data
@ToString
public class TaskJob {
    @TableId(type = IdType.AUTO)
    private String id;          // 主键ID
    private String taskId;      // 任务ID
    private String targetId;    // 目标ID
    private String groupId;     // 群聊ID
    private String taskType;    // 任务类型（massage）
    private String chatType;    // 聊天类型（group、private、other）
    private String templateText;// 模板信息
    private Date createTime;
    private String createBy;
    private Date updateTime;
    private String updateBy;

}
