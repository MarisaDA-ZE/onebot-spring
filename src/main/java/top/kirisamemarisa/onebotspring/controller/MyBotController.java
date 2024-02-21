package top.kirisamemarisa.onebotspring.controller;

import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.GroupReport;
import top.kirisamemarisa.onebotspring.core.enums.MassageType;
import top.kirisamemarisa.onebotspring.core.loader.CommandLoader;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
@RequestMapping("/")
public class MyBotController {

    @Resource
    private CommandLoader commandLoader;

    @Resource
    private RedisTemplate<String, GroupReport> redisTemplate;

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
            // 每人最多保存最近10条消息
            if(optionalSize.orElse(0L) > 10){
                massages.leftPop(key);
            }
            // 30分钟过期
            redisTemplate.expire(key, 30 * 60, TimeUnit.SECONDS);
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
