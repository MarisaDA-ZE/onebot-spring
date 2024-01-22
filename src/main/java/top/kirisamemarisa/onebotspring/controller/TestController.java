package top.kirisamemarisa.onebotspring.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.kirisamemarisa.onebotspring.entity.system.BotConfig;
import top.kirisamemarisa.onebotspring.service.IBotConfigService;

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

    @GetMapping("/getConfigById")
    public String test() {
        System.out.println(">>>>>>>>>");
        BotConfig config = botConfigService.getBotConfigByUserId("1001");
        System.out.println(config);
        return config.getContext();
    }
}
