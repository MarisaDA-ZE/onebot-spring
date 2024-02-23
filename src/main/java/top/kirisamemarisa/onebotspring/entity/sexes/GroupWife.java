package top.kirisamemarisa.onebotspring.entity.sexes;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: MarisaDAZE
 * @Description: 群老婆
 * @Date: 2024/2/21
 */
@Data
@ToString
public class GroupWife {
    @TableId(type = IdType.AUTO)
    private String id;              // 主键ID
    private String groupId;         // 所属Q群号
    private String selfId;          // 群老婆QQ号
    private int shootOutsideCount;  // 外设次数
    private int shootMouthCount;    // 口设次数
    private int shootInsideCount;   // 内设次数
    private int toTopCount;         // 绝顶次数
    private int drinkSpermCount;    // 饮金次数
    private int remainingEnergy;    // 当前剩余精力
    private int comfortValue;       // 舒服程度（当前）
    private Date latestShoot;       // 最近设的时间
    private int intimateLevel;      // 亲密程度
    private int lewdnessLevel;      // 银乱程度
    private String emotion;         // 情绪
    private int sensitiveMouth;     // 敏感度（嘴）
    private int sensitiveBreast;    // 敏感度（熊）
    private int sensitiveClitoris;  // 敏感度（因地）
    private int sensitiveUterus;    // 敏感度（紫宫）
    private Date createTime;        // 创建时间
    private String createBy;        // 创建人
    private Date updateTime;        // 更新时间
    private String updateBy;        // 更新人

}
