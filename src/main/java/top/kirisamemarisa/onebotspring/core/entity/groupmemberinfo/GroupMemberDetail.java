package top.kirisamemarisa.onebotspring.core.entity.groupmemberinfo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;
import top.kirisamemarisa.onebotspring.core.enums.UserRole;
import top.kirisamemarisa.onebotspring.core.enums.UserSex;

/**
 * @Author: MarisaDAZE
 * @Description: 群成员信息
 * @Date: 2024/2/21
 */
@Data
@ToString
public class GroupMemberDetail {

    // 群号
    @JSONField(name = "group_id")
    private long groupId;

    // QQ 号
    @JSONField(name = "user_id")
    private long userId;

    // 昵称
    @JSONField(name = "nickname")
    private String nickName;

    // 群名片／备注
    @JSONField(name = "card")
    private String card;

    // 性别，male 或 female 或 unknown
    @JSONField(name = "sex")
    private UserSex sex;

    //  	年龄
    @JSONField(name = "age")
    private int age;

    // 地区
    @JSONField(name = "area")
    private String area;

    // 加群时间戳
    @JSONField(name = "join_time")
    private long joinTime;

    // 最后发言时间戳
    @JSONField(name = "last_sent_time")
    private long lastSentTime;

    // 成员等级
    @JSONField(name = "level")
    private String level;

    // 角色，owner 或 admin 或 member
    @JSONField(name = "role")
    private UserRole role;

    // 是否不良记录成员
    @JSONField(name = "unfriendly")
    private boolean unfriendly;

    // 专属头衔
    @JSONField(name = "title")
    private String title;

    // 专属头衔过期时间戳
    @JSONField(name = "title_expire_time")
    private long titleExpireTime;

    // 是否允许修改群名片
    @JSONField(name = "card_changeable")
    private boolean cardChangeable;
}
