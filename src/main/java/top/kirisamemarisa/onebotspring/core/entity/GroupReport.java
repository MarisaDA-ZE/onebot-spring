package top.kirisamemarisa.onebotspring.core.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.ToString;
import top.kirisamemarisa.onebotspring.core.entity.massage.Massage;
import top.kirisamemarisa.onebotspring.core.entity.massage.data.base.MData;
import top.kirisamemarisa.onebotspring.core.enums.DetailType;
import top.kirisamemarisa.onebotspring.core.enums.ContentType;
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

    // 内容类型
    private DetailType detailType;

    // 原始消息内容
    private String rawMessage;

    // 收到事件的机器人QQ号
    private String selfId;

    // 消息类型
    private String messageType;

    // 消息ID
    private String messageId;

    // 消息类型
    private String type;

    // 消息内容
    private Massage[] message;

    // 消息子类型，如果是好友则是 friend，如果是群临时会话则是 group
    private String subType;

    // QQ群号
    private String groupId;

    // 发送者QQ号
    private String userId;

    // 发送人信息
    private Sender sender;

    // 自己账号的信息
    private Self self;

    // 上报类型
    private String postType;

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
                        Map<?, ?> msg = (LinkedHashMap<?, ?>) array.get(k);
                        String t = (String) msg.get("type");
                        ContentType type = ContentType.translate(t);
                        massage.setType(type);
                        MData md = MData.translate(msg);
                        massage.setData(md);
                        massages[k] = massage;
                    }
                    groupReport.setMessage(massages);
                }
                // 发送人信息
                case "sender" -> {
                    JSONObject sd = data.getJSONObject("sender");
                    groupReport.setSender(sd.toJavaObject(Sender.class));
                }
                // 自己账号的信息
                case "self" -> {
                    JSONObject sf = data.getJSONObject("self");
                    groupReport.setSelf(sf.toJavaObject(Self.class));
                }
                // 其他字段
                default -> {
                    String name = MrsUtil.toCamelCase(key);
                    try {
                        Class<?> clazz = groupReport.getClass();
                        Field field = clazz.getDeclaredField(name);
                        field.setAccessible(true);
                        Object val = data.get(key);
                        field.set(groupReport, val);
                    } catch (Exception e) {
                        System.out.println("转换失败的字段名: " + name);
                        e.printStackTrace();
                    }
                }
            }
        }
        return groupReport;
    }
}


