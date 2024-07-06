package top.kirisamemarisa.onebotspring.core.entity.reports.common;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;
import top.kirisamemarisa.onebotspring.core.enums.common.UserRole;
import top.kirisamemarisa.onebotspring.core.enums.common.UserSex;
import top.kirisamemarisa.onebotspring.core.serializer.MrsEnumSerializer;

/**
 * @Author: MarisaDAZE
 * @Description: 发送人信息
 * @Date: 2024/2/15
 */

@Data
@ToString
public class Sender {

    /**
     * 发送者QQ号
     */
    @JSONField(name = "user_id")
    private String userId;

    /**
     * 昵称
     */
    @JSONField(name = "nickname")
    private String nickname;

    /**
     * 群名片
     */
    @JSONField(name = "card")
    private String card;

    /**
     * 性别
     */
    @JSONField(name = "sex", serializeUsing = MrsEnumSerializer.class)
    private UserSex sex;

    /**
     * 年龄
     */
    @JSONField(name = "age")
    private int age;

    /**
     * 地区
     */
    @JSONField(name = "area")
    private String area;

    /**
     * 专属头衔
     */
    @JSONField(name = "title")
    private String title;

    /**
     * 角色
     */
    @JSONField(name = "role", serializeUsing = MrsEnumSerializer.class)
    private UserRole role;
}
