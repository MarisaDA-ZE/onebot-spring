package top.kirisamemarisa.onebotspring.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: MarisaDAZE
 * @Description: 发送者的基本信息
 * @Date: 2024-01-20
 */
@Data
@ToString
public class Sender {

    private String user_id;     // 用户ID
    private String nickname;    // 用户昵称
    private String card;        // 用户卡片
    private String role;        // 用户角色
}
