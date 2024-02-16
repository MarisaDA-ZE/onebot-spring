package top.kirisamemarisa.onebotspring.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;
import top.kirisamemarisa.onebotspring.core.enums.UserRole;

/**
 * @Author: MarisaDAZE
 * @Description: 发送人信息
 * @Date: 2024/2/15
 */

@Data
@ToString
public class Sender {
    // 发送者QQ号
    @JSONField(name = "user_id")
    private String userId;

    // 发送者昵称
    @JSONField(name = "nickname")
    private String nickname;

    // 发送者头衔
    @JSONField(name = "card")
    private String card;

    // 发送者角色
    @JSONField(name = "role")
    private UserRole role;
}
