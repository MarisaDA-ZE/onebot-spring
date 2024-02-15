package top.kirisamemarisa.onebotspring.controller;

import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.kirisamemarisa.onebotspring.entity.Massage;
import top.kirisamemarisa.onebotspring.loader.CommandLoader;
import top.kirisamemarisa.onebotspring.service.IBotService;


@CrossOrigin
@RestController
@RequestMapping("/")
public class MyBotController {

    @Resource
    private IBotService botService;

    @Resource
    private CommandLoader commandLoader;

    @PostMapping("/")
    public void test(@RequestBody JSONObject data) {
        System.out.println("data: " + data);
        botService.mainFun(data);
    }

    @PostMapping("/test")
    public void test2(@RequestBody JSONObject data){
        commandLoader.execute(new Massage());
    }
}
