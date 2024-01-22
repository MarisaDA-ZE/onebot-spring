package top.kirisamemarisa.onebotspring.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author: MarisaDAZE
 * @Description: IBotService.描述
 * @Date: 2024/1/21
 */
public interface IBotService {

    /**
     * 处理请求数据
     *
     * @param data .
     */
    void mainFun(JSONObject data);
}
