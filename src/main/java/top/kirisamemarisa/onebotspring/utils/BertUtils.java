package top.kirisamemarisa.onebotspring.utils;

import com.alibaba.fastjson.JSONObject;
import top.kirisamemarisa.onebotspring.entity.bert.BertRequest;

/**
 * @Author: MarisaDAZE
 * @Description: BertUtils.描述
 * @Date: 2024/1/21
 */
public class BertUtils {
    /**
     * 生成一个默认的语音推理请求模板
     *
     * @param text      推理的内容
     * @param modelName 模型名称
     * @return 请求体字符串
     */
    public static String bertDefaultTemplate(String text, String modelName) {
        Object[] data = new Object[]{
                text, modelName, 0.5, 0.6, 0.9, 1,
                "ZH", false, 1, 0.2, null, "Happy", "", 0.7
        };

        BertRequest bertRequest = new BertRequest();
        bertRequest.setData(data);
        bertRequest.setEvent_data(null);
        bertRequest.setFn_index(2);
        return JSONObject.toJSONString(bertRequest);
    }

    public static void main(String[] args) {
        String res = bertDefaultTemplate("哈吉哈哈", "delta");
        System.out.println(res);
    }
}
