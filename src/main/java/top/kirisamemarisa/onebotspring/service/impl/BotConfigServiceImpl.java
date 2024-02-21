package top.kirisamemarisa.onebotspring.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.kirisamemarisa.onebotspring.entity.system.BotConfig;
import top.kirisamemarisa.onebotspring.mapper.BotConfigMapper;
import top.kirisamemarisa.onebotspring.service.IBotConfigService;
import top.kirisamemarisa.onebotspring.utils.SnowflakeUtil;

import java.util.concurrent.TimeUnit;

import static top.kirisamemarisa.onebotspring.common.Constant.CONFIG_SUFFIX;
import static top.kirisamemarisa.onebotspring.common.Constant.TIMEOUT;

/**
 * @Author: MarisaDAZE
 * @Description: BotConfigServiceImpl.描述
 * @Date: 2024/1/23
 */
@Service
public class BotConfigServiceImpl extends ServiceImpl<BotConfigMapper, BotConfig> implements IBotConfigService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${mrs-bot.default-client-url}")
    private String defaultClientURL;

    @Override
    public BotConfig getBotConfigByTargetId(String userId) {
        QueryWrapper<BotConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("USER_ID", userId);
        BotConfig config = baseMapper.selectOne(queryWrapper);
        if (ObjectUtils.isEmpty(config)) {
            System.err.println("未查询到关于" + userId + "的任何信息。");
            return null;
        } else {
            System.out.println("数据库中的配置信息: " + config);
        }
        // 将读取到的配置信息放到redis中。
        redisTemplate.opsForValue().set(userId + CONFIG_SUFFIX, config, TIMEOUT, TimeUnit.SECONDS);
        return config;
    }

    @Override
    public BotConfig createConfig(String botId, String qid, String chatType) {
        if (StrUtil.isBlank(botId) || StrUtil.isBlank(qid)) return null;
        String id = SnowflakeUtil.nextId();
        BotConfig config = new BotConfig();
        config.setId(id);
        config.setUserId(qid);
        config.setBotId(botId);
        config.setChatType(chatType);
        config.setClientUrl(defaultClientURL);
        int insert = baseMapper.insert(config);
        return (insert > 0) ? config : null;
    }
}
