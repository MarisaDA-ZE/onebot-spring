package top.kirisamemarisa.onebotspring.entity.sexes;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("group_wife")
public class GroupWife {
    @TableId(type = IdType.AUTO)
    private String id;      // 主键ID
    private String groupId; // 所属Q群号
    private String selfId;  // 群老婆QQ号
    private Integer mouthSexCount;   // 口角次数（累计）
    private Integer drinkSpermCount; // 饮金次数（累计）
    private Integer toTopCount;      // 绝顶次数（累计）
    private Integer footSexCount;    // 足角次数（累计）
    private Integer udderSexCount;   // 儒角次数（累计）
    private Integer handSexCount;    // 手冲次数（累计）
    private Integer uterusSexCount;  // 笔角次数（累计）
    private Integer analSexCount;    // 港角次数（累计）
    private Integer insideShootCount;    // 内设次数（累计）
    private Integer outsideShootCount;   // 外设次数（累计）
    private Integer remainingEnergy; // 剩余精力
    private Integer comfortValue;    // 舒服程度（当前）
    private Date latestShoot;     // 最近设金的时间
    private Integer lewdnessLevel;   // 银乱程度

    // 初始60
    // 当前情绪（0~9：绝望，10~29：悲伤，30~39：焦虑，40~49：忧郁，50~69：平静，70~79：高兴，80~100+：兴奋）
    private Integer emotion;
    private Integer sensitiveMouth;  // 敏感度（嘴）
    private Integer sensitiveNipple; // 敏感度（奈头）
    private Integer sensitiveUdder;  // 敏感度（欧派）
    private Integer sensitiveClitoris;   // 敏感度（银地）
    private Integer sensitiveUterus;     // 敏感度（紫宫）
    private Integer sensitiveAnal;       // 敏感度（菊部）
    private Integer mouthProficiency;    // 口角熟练度
    private Integer udderProficiency;    // 儒角熟练度
    private Integer handProficiency;     // 手冲熟练度
    private Integer uterusProficiency;   // 笔角熟练度
    private Integer analProficiency;     // 港角熟练度
    private Integer footProficiency;     // 足角熟练度
    private Date createTime;  // 创建时间
    private String createBy;    // 创建人
    private Date updateTime;  // 更新时间
    private String updateBy;    // 更新人
}
