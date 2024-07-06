package top.kirisamemarisa.onebotspring.core.serializer;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.reader.ObjectReader;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.CQMessage;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.*;
import top.kirisamemarisa.onebotspring.core.enums.reports.message.cq.CQMessageType;
import top.kirisamemarisa.onebotspring.core.util.EnumUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author: MarisaDAZE
 * @Description: CQ消息段反序列化处理器
 * @Date: 2024/07/03
 */
public class CQDataDeserializer implements ObjectReader<CQMessage[]> {
    @Override
    public CQMessage[] readObject(JSONReader jsonReader, Type fieldType, Object fieldName, long features) {
        String str = jsonReader.readString();
        JSONArray jsonArray = JSONObject.parseObject(str, JSONArray.class);
        List<CQMessage> messages = new ArrayList<>();
        for (Object item : jsonArray) {
            if (item instanceof JSONObject msg) {
                String st = msg.getString("type");
                CQMessageType type = EnumUtils.fromValue(st, CQMessageType.class);
                JSONObject data = msg.getJSONObject("data");
                messages.add(cqTranslate(type, data));
            }
        }
        return messages.toArray(new CQMessage[0]);
    }

    /**
     * cq消息段类型转换
     *
     * @param type 类型
     * @param data 数据内容
     * @return 转换结果
     */
    public CQMessage cqTranslate(CQMessageType type, JSONObject data) {
        CQMessage result = new CQMessage();
        result.setType(type);
        switch (type) {
            case ANONYMOUS -> result.setData(JSONObject.parseObject(data.toJSONString(), CQAnonymous.class));
            case AT -> result.setData(JSONObject.parseObject(data.toJSONString(), CQAt.class));
            case CARD_IMAGE -> result.setData(JSONObject.parseObject(data.toJSONString(), CQCardimage.class));
            case CONTACT -> result.setData(JSONObject.parseObject(data.toJSONString(), CQContact.class));
            case DICE -> result.setData(JSONObject.parseObject(data.toJSONString(), CQDice.class));
            case FACE -> result.setData(JSONObject.parseObject(data.toJSONString(), CQFace.class));
            case FORWARD -> result.setData(JSONObject.parseObject(data.toJSONString(), CQForward.class));
            case GIFT -> result.setData(JSONObject.parseObject(data.toJSONString(), CQGift.class));
            case IMAGE -> result.setData(JSONObject.parseObject(data.toJSONString(), CQImage.class));
            case JSON -> result.setData(JSONObject.parseObject(data.toJSONString(), CQJson.class));
            case LOCATION -> result.setData(JSONObject.parseObject(data.toJSONString(), CQLocation.class));
            case MUSIC -> result.setData(JSONObject.parseObject(data.toJSONString(), CQMusic.class));
            case NODE -> result.setData(JSONObject.parseObject(data.toJSONString(), CQNode.class));
            case POKE -> result.setData(JSONObject.parseObject(data.toJSONString(), CQPoke.class));
            case REDBAG -> result.setData(JSONObject.parseObject(data.toJSONString(), CQRedbag.class));
            case REPLY -> result.setData(JSONObject.parseObject(data.toJSONString(), CQReply.class));
            case RPS -> result.setData(JSONObject.parseObject(data.toJSONString(), CQRps.class));
            case SHAKE -> result.setData(JSONObject.parseObject(data.toJSONString(), CQShake.class));
            case SHARE -> result.setData(JSONObject.parseObject(data.toJSONString(), CQShare.class));
            case TEXT -> result.setData(JSONObject.parseObject(data.toJSONString(), CQText.class));
            case VIDEO -> result.setData(JSONObject.parseObject(data.toJSONString(), CQVideo.class));
            case VOICE -> result.setData(JSONObject.parseObject(data.toJSONString(), CQVoice.class));
            case XML -> result.setData(JSONObject.parseObject(data.toJSONString(), CQXml.class));
        }
        return result;
    }
}
