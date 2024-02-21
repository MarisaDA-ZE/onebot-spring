package top.kirisamemarisa.onebotspring.core.entity.groupreport;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.ToString;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.Massage;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.base.MData;
import top.kirisamemarisa.onebotspring.core.enums.*;
import top.kirisamemarisa.onebotspring.core.util.EnumUtils;
import top.kirisamemarisa.onebotspring.core.util.MrsUtil;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: MarisaDAZE
 * @Description: 群聊消息上报
 * @Date: 2024/1/20
 */
@Data
@ToString
public class GroupReport {

    // 原始消息内容
    private String rawMessage;

    // 收到事件的机器人QQ号
    private String selfId;

    // 消息类型
    private MassageType messageType;

    // 消息ID
    private long messageId;

    // 消息内容
    private Massage[] message;

    // 消息子类型，如果是好友则是 friend，如果是群临时会话则是 group
    private SubType subType;

    // QQ群号
    private String groupId;

    // 发送者QQ号
    private String userId;

    // 匿名信息，如果不是匿名消息则为 null
    private Anonymous anonymous;

    // 真实ID
    private String realId;

    // 发送人信息
    private Sender sender;

    // 上报类型
    private PostType postType;

    // 事件发生的时间戳
    private long time;

    // 字体
    private int font;

    /**
     * JSONObject转GroupReport
     *
     * @param data .
     * @return .
     */
    public static GroupReport translate(JSONObject data) {
        Set<String> keySet = data.keySet();
        GroupReport groupReport = new GroupReport();
        for (String key : keySet) {
            switch (key) {
                // 消息内容
                case "message" -> {
                    JSONArray array = data.getJSONArray(key);
                    Massage[] massages = new Massage[array.size()];
                    for (int k = 0; k < array.size(); k++) {
                        Massage massage = new Massage();
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
                        massage.setType(type);
                        massage.setData(mData);
                        massages[k] = massage;
                    }
                    groupReport.setMessage(massages);
                }
                // 匿名信息
                case "anonymous" -> {
                    JSONObject sd = data.getJSONObject("anonymous");
                    groupReport.setAnonymous(sd.toJavaObject(Anonymous.class));
                }
                // 发送人信息
                case "sender" -> {
                    JSONObject sd = data.getJSONObject("sender");
                    groupReport.setSender(sd.toJavaObject(Sender.class));
                }
                // 其他字段
                default -> {
                    String name = MrsUtil.toCamelCase(key);
                    try {
                        Class<?> clazz = groupReport.getClass();
                        Field field;
                        try {
                            field = clazz.getDeclaredField(name);
                        } catch (NoSuchFieldException e) {
                            System.err.println("字段不存在: " + name + ",尝试通过key查找。");
                            field = clazz.getDeclaredField(key);
                        }
                        field.setAccessible(true);
                        Object val = data.get(key);
                        // 如果是枚举则调用工具方法进行处理
                        if (field.getType().isEnum()) {
                            Class<?> eType = field.getType();
                            val = EnumUtils.translate((String) val, eType);
                        }
                        field.set(groupReport, val);
                    } catch (Exception e) {
                        System.err.println("出错了,字段为 " + name + ",或者 " + key +
                                ";该错误不会影响到代码执行,只是有某些字段没有值，可能是字段不存在，" +
                                "也可能是类型不兼容，请开发者自行排查。");
                    }
                }
            }
        }
        return groupReport;
    }
}


