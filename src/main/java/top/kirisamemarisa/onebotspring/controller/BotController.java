package top.kirisamemarisa.onebotspring.controller;

import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.base.MrsReport;
import top.kirisamemarisa.onebotspring.core.loader.CommandLoader;

@CrossOrigin
@RestController
@RequestMapping("/")
public class BotController {

    @Resource
    private CommandLoader commandLoader;

    /**
     * 前端上报
     *
     * @param jsonObject .
     */
    @PostMapping("/")
    public void test(@RequestBody JSONObject jsonObject) {
        System.out.println("data: " + jsonObject);
        MrsReport report =  MrsReport.translate(jsonObject);
        System.out.println(report);
        commandLoader.execute(report);
    }
}
