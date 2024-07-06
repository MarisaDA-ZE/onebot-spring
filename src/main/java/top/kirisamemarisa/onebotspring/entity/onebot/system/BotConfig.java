package top.kirisamemarisa.onebotspring.entity.onebot.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

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

    @TableField(exist = false)
    @Value("${mrs-bot.default-client-url}")
    String clientURL;

    @TableField(exist = false)
    @Value("${mrs-bot.bot-qq}")
    String defaultBotQQ;

    public BotConfig defaultConfig(String userId, String chatType) {
        BotConfig botConfig = new BotConfig();
        botConfig.setUserId(userId);
        botConfig.setBotId("0");
        botConfig.setChatType(chatType);
        botConfig.setClientUrl(clientURL);

        return this;
    }
}
