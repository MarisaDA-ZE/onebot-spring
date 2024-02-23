package top.kirisamemarisa.onebotspring.entity.sexes;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import top.kirisamemarisa.onebotspring.enums.SexType;

import java.util.Date;

/**
 * @Author: MarisaDAZE
 * @Description: 涩涩详情表
 * @Date: 2024/2/23
 */
@Data
@ToString
@TableName("group_sex_detail")
public class GroupSexDetail {
    @TableId(type = IdType.AUTO)
    private String id;          // 主键ID
    private String groupId;     // 群号
    private String userId;      // 用户ID
    private String userQq;      // 用户QQ
    private String wifeId;      // 群老婆ID
    private String wifeQq;      // 群老婆QQ
    private SexType sexType;    // 涩涩类型
    private Integer operationsCount;    // 操作次数
    private Integer isShootOutside;     // 是否外设（0：否，1：是）
    private Integer isShootMouth;       // 是否口设（0：否，1：是）
    private Integer isShootInside;      // 是否内设（0：否，1：是）
    private Integer isToTop;            // 是否绝顶（0：否，1：是）
    private Integer isDrinkSperm;       // 是否饮金（0：否，1：是）
    private Integer expendEnergy;       // 本次消耗精力
    private Integer generateComfort;    // 产生舒服值

    // 初始 45
    // 累计亲密度（0~9：厌恶，10~19：讨厌，20~39：陌生，40~49：熟悉，50~59：朋友，60~79：喜欢，80~100+：夫妻）
    private Integer intimate;
    private Integer generateLewdness;           // 产生银乱度
    private Integer generateEmotion;            // 产生情绪值
    private Integer generateSensitiveMouth;     // 增加敏感度（嘴）
    private Integer generateSensitiveNipple;    // 增加敏感度（奈头）
    private Integer generateSensitiveUdder;     // 增加敏感度（欧派）
    private Integer generateSensitiveClitoris;  // 增加敏感度（银地）
    private Integer generateSensitiveUterus;    // 增加敏感度（紫宫）
    private Integer generateSensitiveAnal;      // 增加敏感度（菊部）
    private Integer mouthProficiency;   // 口角熟练度
    private Integer udderProficiency;   // 儒角熟练度
    private Integer handProficiency;    // 手冲熟练度
    private Integer uterusProficiency;  // 笔角熟练度
    private Integer analProficiency;    // 港角熟练度
    private Integer footProficiency;    // 足角熟练度
    private Date createTime;    // 创建时间
    private String createBy;    // 创建人
    private Date updateTime;    // 更新时间
    private String updateBy;    // 更新人


}
