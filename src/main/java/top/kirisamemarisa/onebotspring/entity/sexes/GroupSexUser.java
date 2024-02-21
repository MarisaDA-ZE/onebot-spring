package top.kirisamemarisa.onebotspring.entity.sexes;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: MarisaDAZE
 * @Description: 群涩涩用户
 * @Date: 2024/2/21
 */
@Data
@ToString
public class GroupSexUser {
    @TableId(type = IdType.AUTO)
    private String id;              // 主键ID
    private String groupId;          // 群号
    private String userId;          // 用户ID(保留字段，计划是用来关联user表)
    private String userQq;          // 用户QQ号
    private int redFlowerCount;     // 红色花朵数
    private int blueFlowerCount;    // 蓝色花朵数
    private int energyCount;        // 精力药剂数
    private int aphrodisiacCount;   // 媚药数
    private int wineCount;          // 葡萄酒数
    private Date createTime;        // 创建时间
    private String createBy;        // 创建人
    private Date updateTime;        // 更新时间
    private String updateBy;        // 更新人

}
