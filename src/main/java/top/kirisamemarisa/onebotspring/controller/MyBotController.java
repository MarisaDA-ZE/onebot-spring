package top.kirisamemarisa.onebotdemo.controller;

import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.kirisamemarisa.onebotdemo.service.IBotService;


@CrossOrigin
@RestController
@RequestMapping("/")
public class MyBotController {

    @Resource
    private IBotService botService;

    @PostMapping("/")
    public void test(@RequestBody JSONObject data) {
        System.out.println("data: " + data);
        botService.mainFun(data);
    }
}
