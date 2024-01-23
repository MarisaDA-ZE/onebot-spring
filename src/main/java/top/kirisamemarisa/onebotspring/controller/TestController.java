package top.kirisamemarisa.onebotspring.controller;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.kirisamemarisa.onebotspring.entity.system.BotConfig;
import top.kirisamemarisa.onebotspring.service.IBotConfigService;

import java.util.concurrent.TimeUnit;

/**
 * @Author: MarisaDAZE
 * @Description: TestController.描述
 * @Date: 2024/1/23
 */
@CrossOrigin
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private IBotConfigService botConfigService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/getConfigById")
    public String test() {
        System.out.println(">>>>>>>>>");
        BotConfig config = botConfigService.getBotConfigByTargetId("1001");
        System.out.println("数据库配置: "+config);
        redisTemplate.opsForValue().set("test.1001", "测试...", 200, TimeUnit.SECONDS);
        String s = redisTemplate.opsForValue().get("test.1001");
        System.out.println("redis配置: "+s);
        return "";
    }
}
