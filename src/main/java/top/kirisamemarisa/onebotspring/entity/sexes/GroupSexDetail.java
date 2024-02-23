package top.kirisamemarisa.onebotspring.entity.sexes;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: MarisaDAZE
 * @Description: 涩涩详情表
 * @Date: 2024/2/23
 */
@Data
@ToString
public class GroupSexDetail {
    private String id;      // 主键ID
    private String groupId;     // 群号
    private String userQq;      // 用户QQ
    private String wifeQq;      // 群老婆QQ
    private int sexType;        // 涩涩方式
    private int operationsCount;        // 操作次数
    private int isShootOutside;     // 是否外设
    private int isShootMouth;       // 是否口设
    private int isShootInside;      // 是否内设
    private int isToTop;        // 是否绝顶
    private int isDrinkSperm;       // 是否饮金
    private int remainingEnergy;        // 剩余精力
    private int comfortValue;       // 舒服程度（当前）
    private int intimateLevel;      // 当前亲密程度
    private int lewdnessLevel;      // 当前银乱程度
    private String emotion;     // 当前情绪
    private int sensitiveMouth;     // 当前敏感度（嘴）
    private int sensitiveBreast;        // 当前敏感度（欧派）
    private int sensitiveClitoris;      // 当前敏感度（indi）
    private int sensitiveUterus;        // 当前敏感度（紫宫）
    private String createTime;      // 创建时间
    private String createBy;        // 创建人
    private String updateTime;      // 更新时间
    private String updateBy;        // 更新人

}
