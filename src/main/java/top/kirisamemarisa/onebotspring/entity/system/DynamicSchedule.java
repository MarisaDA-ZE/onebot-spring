package top.kirisamemarisa.onebotspring.entity.system;

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
public class DynamicSchedule {
    @TableId(type = IdType.AUTO)
    private String id;
    private String taskId;
    private String cronText;
    private String remark;
    private Date createTime;
    private String createBy;
    private String updateTime;
    private String updateBy;


}
