package top.kirisamemarisa.onebotspring.core.entity.groupreport;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.ToString;
import org.springframework.util.ObjectUtils;
import top.kirisamemarisa.onebotspring.annotation.MessageField;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.Message;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.base.MData;
import top.kirisamemarisa.onebotspring.core.enums.*;
import top.kirisamemarisa.onebotspring.core.util.EnumUtils;
import top.kirisamemarisa.onebotspring.core.util.MrsUtil;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: MarisaDAZE
 * @Description: 群聊消息上报
 * @Date: 2024/1/20
 */
@Data
@ToString
public class GroupReport {

    /**
     * 事件发生的时间戳
     */
    @MessageField(value = "time")
    private long time;

    /**
     * 收到事件的机器人QQ号
     */
    @MessageField(value = "self_id")
    private String selfId;

    /**
     * 上报类型
     */
    @MessageField("post_type")
    private PostType postType;

    /**
     * 消息类型
     */
    @MessageField("message_type")
    private MassageType messageType;

    /**
     * 消息子类型, 正常消息是 normal, 匿名消息是 anonymous, 系统提示 ( 如「管理员已禁止群内匿名聊天」 ) 是 notice
     */
    @MessageField("sub_type")
    private SubType subType;

    /**
     * 消息ID
     */
    @MessageField("message_id")
    private long messageId;

    /**
     * 发送者QQ号
     */
    @MessageField("user_id")
    private String userId;

    /**
     * 消息内容
     */
    @MessageField("message")
    private Message[] message;

    /**
     * 原始消息内容
     */
    @MessageField("raw_message")
    private String rawMessage;

    /**
     * 字体
     */
    @MessageField("font")
    private int font;

    /**
     * 发送人信息
     */
    @MessageField("sender")
    private Sender sender;

    /**
     * QQ群号
     */
    @MessageField("group_id")
    private String groupId;

    /**
     * 匿名信息，如果不是匿名消息则为 null
     */
    @MessageField("anonymous")
    private Anonymous anonymous;

    /**
     * 真实ID
     */
    @MessageField("real_id")
    private String realId;


    /**
     * JSONObject转GroupReport
     *
     * @param data JSON对象格式的消息数据
     * @return GroupReport对象
     */
    public static GroupReport translate(JSONObject data) {
        GroupReport groupReport = new GroupReport();
        Class<? extends GroupReport> clazz = groupReport.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            MessageField annotation = field.getAnnotation(MessageField.class);
            String fieldName = annotation.value();
            Class<?> fieldType = field.getType();
            try {
                // 基础数据类型
                if (!MrsUtil.isComplexDataType(fieldType)) {
                    Object value = data.getObject(fieldName, fieldType);
                    field.set(groupReport, value);
                    continue;
                }

                // 枚举类型
                if (fieldType.isEnum()) {
                    Object value = EnumUtils.translate(data.getString(fieldName), fieldType);
                    field.set(groupReport, value);
                    continue;
                }

                // 自定义的复杂数据类型
                switch (fieldName) {
                    // 消息内容
                    case "message" -> {
                        JSONArray array = data.getJSONArray(fieldName);
                        Message[] messages = new Message[array.size()];
                        for (int k = 0; k < array.size(); k++) {
                            Message message = new Message();
                            ContentType type = ContentType.UNKNOWN;
                            MData mData = null;
                            Object o = array.get(k);
                            if (o instanceof JSONObject) {
                                JSONObject msg = array.getJSONObject(k);
                                type = ContentType.translate((String) msg.get("type"));
                                mData = MData.translate(msg);
                            } else if (o instanceof LinkedHashMap) {
                                Map<?, ?> msg = (LinkedHashMap<?, ?>) array.get(k);
                                type = ContentType.translate((String) msg.get("type"));
                                mData = MData.translate(msg);
                            }
                            message.setType(type);
                            message.setData(mData);
                            messages[k] = message;
                        }
                        groupReport.setMessage(messages);
                    }
                    // 匿名信息、发送者
                    case "anonymous", "sender" -> {
                        JSONObject sd = data.getJSONObject(fieldName);
                        if (ObjectUtils.isEmpty(sd)) {
                            field.set(groupReport, null);
                        } else {
                            field.set(groupReport, sd.toJavaObject(fieldType));
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                String msg = e.getMessage();
                String name = field.getName();
                System.err.println("异常类型: " + msg + ", 键: " + name + "设置值时出错.");
            }
        }
        return groupReport;
    }
}


