package top.kirisamemarisa.onebotspring.core.serializer;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.reader.ObjectReader;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.CQMessage;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.base.CQData;
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
        Class<? extends CQData> clazz = null;
        switch (type) {
            case ANONYMOUS -> clazz = CQAnonymous.class;
            case AT -> clazz = CQAt.class;
            case CARD_IMAGE -> clazz = CQCardimage.class;
            case CONTACT -> clazz = CQContact.class;
            case DICE -> clazz = CQDice.class;
            case FACE -> clazz = CQFace.class;
            case FORWARD -> clazz = CQForward.class;
            case GIFT -> clazz = CQGift.class;
            case IMAGE -> clazz = CQImage.class;
            case JSON -> clazz = CQJson.class;
            case LOCATION -> clazz = CQLocation.class;
            case MUSIC -> clazz = CQMusic.class;
            case NODE -> clazz = CQNode.class;
            case POKE -> clazz = CQPoke.class;
            case REDBAG -> clazz = CQRedbag.class;
            case REPLY -> clazz = CQReply.class;
            case RPS -> clazz = CQRps.class;
            case SHAKE -> clazz = CQShake.class;
            case SHARE -> clazz = CQShare.class;
            case TEXT -> clazz = CQText.class;
            case VIDEO -> clazz = CQVideo.class;
            case VOICE -> clazz = CQVoice.class;
            case XML -> clazz = CQXml.class;
        }
        String dt = data.toJSONString();
        CQMessage result = new CQMessage();
        result.setType(type);
        result.setData(JSONObject.parseObject(dt, clazz));
        return result;
    }
}
