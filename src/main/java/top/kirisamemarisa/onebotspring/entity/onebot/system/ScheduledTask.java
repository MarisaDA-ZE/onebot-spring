package top.kirisamemarisa.onebotspring.entity.onebot.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: MarisaDAZE
 * @Description: DynamicSchedule.描述
 * @Date: 2024/1/24
 */
@Data
@ToString
public class ScheduledTask {
    @TableId(type = IdType.AUTO)
    private String id;          // id
    private String userId;      // 用户ID
    private String cronText;    // cron表达式
    private String remark;      // 备注
    private Date createTime;
    private String createBy;
    private Date updateTime;
    private String updateBy;


}
