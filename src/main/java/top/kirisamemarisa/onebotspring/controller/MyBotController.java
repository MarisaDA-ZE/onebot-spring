package top.kirisamemarisa.onebotspring.controller;

import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.GroupReport;
import top.kirisamemarisa.onebotspring.core.enums.MassageType;
import top.kirisamemarisa.onebotspring.core.loader.CommandLoader;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static top.kirisamemarisa.onebotspring.common.Constant.MASSAGE_ID_MAP;
import static top.kirisamemarisa.onebotspring.common.Constant.MILLIS_30;

@CrossOrigin
@RestController
@RequestMapping("/")
public class MyBotController {

    @Resource
    private CommandLoader commandLoader;

    @Resource
    private RedisTemplate<String, GroupReport> redisTemplate;

    @Resource
    private RedisTemplate<String, String> stringRedis;


    /**
     * 前端上报
     *
     * @param data .
     */
    @PostMapping("/")
    public void test(@RequestBody JSONObject data) {
        System.out.println("data: " + data);
        GroupReport groupReport = GroupReport.translate(data);
        MassageType mType = groupReport.getMessageType();
        // 把最近的群聊消息保存起来
        if (mType == MassageType.GROUP) {
            String groupId = groupReport.getGroupId();
            String senderId = (groupReport.getSender()).getUserId();
            String key = groupId + ":" + senderId;
            ListOperations<String, GroupReport> massages = redisTemplate.opsForList();
            massages.rightPush(key, groupReport);
            Optional<Long> optionalSize = Optional.ofNullable(massages.size(key));
            int maxLen = 10;
            // 每人最多保存最近10条消息
            if (optionalSize.orElse(0L) > maxLen) {
                GroupReport remove = massages.leftPop(key);
                if (!ObjectUtils.isEmpty(remove)) {
                    String dk = groupId + MASSAGE_ID_MAP + ":" + remove.getMessageId();
                    stringRedis.delete(dk);
                }
            }

            // 保存MESSAGE_ID和发送者QQ的对应关系
            long mid = groupReport.getMessageId();
            String sk = groupId + MASSAGE_ID_MAP + ":" + mid;
            // TODO: 这样写有一定的隐患，过期时间更新不一致可能会出现找不到值的情况
            // 键message_id，是单个存储的，值是用列表存储的，所以值的更新是全部更新
            // 建议使用这个键时如果没有找到值就删除掉对应的键。
            stringRedis.opsForValue().set(sk, senderId, MILLIS_30, TimeUnit.MILLISECONDS);

            // 30分钟过期
            redisTemplate.expire(key, MILLIS_30, TimeUnit.MILLISECONDS);
        }
        commandLoader.execute(groupReport);
        // botService.mainFun(data);
    }

    /**
     * 测试用
     *
     * @param data .
     */
    @PostMapping("/test")
    public void test2(@RequestBody JSONObject data) {
        GroupReport groupReport = GroupReport.translate(data);
        System.out.println(groupReport);
    }
}
