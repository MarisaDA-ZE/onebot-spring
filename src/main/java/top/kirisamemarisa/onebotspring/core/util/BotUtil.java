package top.kirisamemarisa.onebotspring.core.util;

import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.GroupReport;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.Sender;
import top.kirisamemarisa.onebotspring.entity.system.BotConfig;
import top.kirisamemarisa.onebotspring.service.IBotConfigService;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    /**
     * 从redis中获取消息列表
     * @param key   key
     * @param clazz 类型
     * @return  List<?>
     * @param <T>   类型
     */
    public <T> List<T> getMassageList(String key, Class<T> clazz) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        Optional<Long> optionalSize = Optional.ofNullable(list.size(key));
        long size = optionalSize.orElse(0L);
        List<Object> range = list.range(key, 0, size - 1);
        if (ObjectUtils.isEmpty(range)) return new ArrayList<>();
        return range.stream().map(e -> parseT(e, clazz)).collect(Collectors.toList());
    }

    /**
     * 将对象转换为T对象
     *
     * @param o     .
     * @param clazz .
     * @param <T>   .
     * @return .
     */
    private <T> T parseT(Object o, Class<T> clazz) {
        T instance = null;
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            instance = constructor.newInstance();
            BeanUtils.copyProperties(o, instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }
}
