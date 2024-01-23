package top.kirisamemarisa.onebotspring.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

/**
 * @Author: MarisaDAZE
 * @Description: 机器人配置
 * @Date: 2024/1/23
 */
@Data
@ToString
public class BotConfig {
    @TableId(type = IdType.AUTO)
    private String id;      // id
    private String userId;  // 用户ID
    private String botId;   // 机器人ID
    private String chatType; // 聊天类型（group、friend）
    private String clientUrl;// 机器人客户端地址
}
