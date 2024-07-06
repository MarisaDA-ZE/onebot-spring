package top.kirisamemarisa.onebotspring.core.util;

import com.alibaba.fastjson.JSONObject;

import top.kirisamemarisa.onebotspring.core.entity.reports.message.GroupReport;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.PrivateReport;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.CQMessage;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.CQAt;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.CQImage;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.CQText;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.CQVoice;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.base.MessageReport;
import top.kirisamemarisa.onebotspring.core.enums.reports.message.MessageType;
import top.kirisamemarisa.onebotspring.core.enums.reports.message.cq.CQMessageType;

import java.util.List;

/**
 * @Author: MarisaDAZE
 * @Description: CQ消息模板
 * @Date: 2024/1/21
 */
public class CQMessageTemplate {

    public static String friendTextTemplateSingle(String a, String b) {
        return null;
    }

    public static String groupTextTemplateSingle(String a, String b) {
        return null;
    }

    public static void sendTextMessage(String a, String b, String c) {
    }

    /**
     * 获取群成员信息的模板
     *
     * @param groupId 群号
     * @param userId  目标QQ
     * @return 模板
     */
    public static String getGroupMemberInfo(String groupId, String userId) {
        return null;
    }

    /**
     * at某人
     *
     * @param type   消息类型
     * @param target 目标群聊
     * @param at     要at的qq号
     * @return .
     */
    public static String atTemplate(MessageType type, String target, String at) {
        if (type != MessageType.GROUP) return null;
        MessageReport report = getMessage(type, target);
        CQMessage cqAt = new CQMessage(CQMessageType.AT, new CQAt(at));
        report.setMessages(new CQMessage[]{cqAt});
        return JSONObject.toJSONString(report);
    }

    /**
     * 生成一段语音的模板
     *
     * @param type     消息类型
     * @param target   目标对象
     * @param voiceURL 音频URL
     * @return 消息模板
     */
    public static String voiceTemplate(MessageType type, String target, String voiceURL) {
        MessageReport report = getMessage(type, target);
        CQMessage voice = new CQMessage(CQMessageType.VOICE, new CQVoice(voiceURL));
        report.setMessages(new CQMessage[]{voice});
        return JSONObject.toJSONString(report);
    }

    /**
     * 生成多段文本的模板
     *
     * @param type   消息类型
     * @param target 目标对象
     * @param texts  文本列表
     * @return 消息模板
     */
    public static String textListTemplate(MessageType type, String target, List<String> texts) {
        MessageReport report = getMessage(type, target);
        List<CQMessage> itemList = texts.stream().map(text ->
                new CQMessage(CQMessageType.TEXT, new CQText(text))
        ).toList();
        report.setMessages(itemList.toArray(new CQMessage[0]));
        return JSONObject.toJSONString(report);
    }

    /**
     * 生成多张图片的模板
     *
     * @param type   消息类型
     * @param target 目标对象
     * @param urls   图片URL列表
     * @return 消息模板
     */
    public static String imageListTemplate(MessageType type, String target, List<String> urls) {
        MessageReport report = getMessage(type, target);
        List<CQMessage> images = urls.stream().map(url ->
                new CQMessage(CQMessageType.IMAGE, new CQImage(url))
        ).toList();
        report.setMessages(images.toArray(new CQMessage[0]));
        return JSONObject.toJSONString(report);
    }

    /**
     * 生成混合消息段类型的模板
     *
     * @param type     消息类型
     * @param target   目标对象
     * @param messages 消息段列表
     * @return 消息模板
     */
    public static String mixListTemplate(MessageType type, String target, List<CQMessage> messages) {
        MessageReport report = getMessage(type, target);
        report.setMessages(messages.toArray(new CQMessage[0]));
        return JSONObject.toJSONString(report);
    }

    /**
     * 创建返回消息对象
     *
     * @param type   消息类型
     * @param target 目标对象
     * @return 返回消息对象
     */
    private static MessageReport getMessage(MessageType type, String target) {
        MessageReport report = null;
        switch (type) {
            case GROUP -> {
                GroupReport rpt = new GroupReport();
                rpt.setMessageType(type);
                rpt.setGroupId(target);
                report = rpt;
            }
            case PRIVATE -> {
                PrivateReport rpt = new PrivateReport();
                rpt.setMessageType(type);
                rpt.setUserId(target);
                report = rpt;
            }
        }
        return report;
    }

    public static void main(String[] args) {
//        String at = paragraphAtTemplate("3038488053");
//        String text1 = paragraphTextTemplate(" 你好，这是一条测试消息。");
//        String url = "https://img1.baidu.com/it/u=2206336034,3499768820&fm=253&fmt=auto&app=138&f=JPEG?w=952&h=500";
//        String image = paragraphImageTemplate(url);
//        String text2 = paragraphTextTemplate("这是测试消息的第二个段落");
//        System.out.println("at: " + at);
//        System.out.println("text1: " + text1);
//        System.out.println("image: " + image);
//        System.out.println("text2: " + text2);
//        List<String> massageList = Arrays.asList(at, text1, image, text2);
//        String s = massageList.toString();
//        System.out.println(s);
//        List<?> list = JSONObject.parseObject(s, List.class);
//        List<String> strings = new ArrayList<>();
//        list.forEach(e -> {
//            if (e instanceof String) strings.add((String) e);
//        });
//
//        System.out.println(list);

//        String massage = customGroupTemplate("701339984", massageList);
//        System.out.println("massage: " + massage);
    }
}
