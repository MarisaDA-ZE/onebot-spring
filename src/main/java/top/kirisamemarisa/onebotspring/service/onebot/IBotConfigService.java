package top.kirisamemarisa.onebotspring.service.onebot;

import com.baomidou.mybatisplus.extension.service.IService;
import top.kirisamemarisa.onebotspring.entity.onebot.system.BotConfig;

/**
 * @Author: MarisaDAZE
 * @Description: IBotConfigService.描述
 * @Date: 2024/1/23
 */
public interface IBotConfigService extends IService<BotConfig> {

    /**
     * 根据用户QQ号、QQ群号查询对应的配置信息
     *
     * @param userId QQ号、Q群号
     * @return 配置信息
     */
    BotConfig getBotConfigByTargetId(String userId);

    /**
     * 创建一个机器人配置信息
     *
     * @param botId    机器人QQ号
     * @param qid      QQ号、QQ群号
     * @param chatType 聊天类型
     * @return .
     */
    BotConfig createConfig(String botId, String qid, String chatType);
}
