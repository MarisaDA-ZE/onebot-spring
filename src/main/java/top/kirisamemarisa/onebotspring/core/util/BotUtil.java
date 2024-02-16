package top.kirisamemarisa.onebotspring.core.util;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import top.kirisamemarisa.onebotspring.core.entity.GroupReport;
import top.kirisamemarisa.onebotspring.core.entity.Sender;
import top.kirisamemarisa.onebotspring.entity.system.BotConfig;
import top.kirisamemarisa.onebotspring.service.IBotConfigService;

import static top.kirisamemarisa.onebotspring.common.Constant.CONFIG_SUFFIX;

/**
 * @Author: MarisaDAZE
 * @Description: BotUtil.描述
 * @Date: 2024/2/16
 */
@Component
public class BotUtil {
    @Resource
    private IBotConfigService botConfigService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取机器人对该群的配置信息
     *
     * @param groupReport 消息对象
     * @return 配置信息
     */
    public BotConfig getGroupConfig(GroupReport groupReport) {
        String sourceId = groupReport.getGroupId();
        BotConfig config = null;
        // 尝试从redis中读
        try {
            config = (BotConfig) redisTemplate.opsForValue().get(sourceId + CONFIG_SUFFIX);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 尝试从数据库中读
        if (ObjectUtils.isEmpty(config)) config = botConfigService.getBotConfigByTargetId(sourceId);
        return config;
    }

    /**
     * 获取机器人对该用户的配置信息
     *
     * @param groupReport 消息对象
     * @return 配置信息
     */
    public BotConfig getFriendConfig(GroupReport groupReport) {
        Sender sender = groupReport.getSender();
        String userId = sender.getUserId();
        BotConfig config = null;
        // 尝试从redis中读
        try {
            config = (BotConfig) redisTemplate.opsForValue().get(userId + CONFIG_SUFFIX);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 尝试从数据库中读
        if (ObjectUtils.isEmpty(config)) config = botConfigService.getBotConfigByTargetId(userId);
        return config;
    }
}
