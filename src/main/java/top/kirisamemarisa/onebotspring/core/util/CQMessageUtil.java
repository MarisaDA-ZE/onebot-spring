package top.kirisamemarisa.onebotspring.core.util;

import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.CQMessage;
import top.kirisamemarisa.onebotspring.core.enums.reports.message.MessageType;
import top.kirisamemarisa.onebotspring.utils.HttpUtils;

import java.util.List;

/**
 * @Author: MarisaDAZE
 * @Description: CQ消息发送工具类
 * @Date: 2024/07/02
 */
public class CQMessageUtil {

    /**
     * 私聊发送多段消息
     *
     * @param url      机器人地址
     * @param target   好友QQ号
     * @param messages 消息
     */
    public static void sendFriendMessage(String url, String target, List<CQMessage> messages) {
        sendMessage(url, MessageType.PRIVATE, target, messages);
    }

    /**
     * 群聊发送多段消息
     *
     * @param url      机器人地址
     * @param target   QQ群号
     * @param messages 消息
     */
    public static void sendGroupMessage(String url, String target, List<CQMessage> messages) {
        sendMessage(url, MessageType.GROUP, target, messages);
    }

    /**
     * 发送多段消息
     *
     * @param url      机器人地址
     * @param type     消息类型
     * @param target   群号/好友QQ
     * @param messages 消息列表
     */
    public static void sendMessage(String url, MessageType type, String target, List<CQMessage> messages) {
        String template = CQMessageTemplate.mixListTemplate(type, target, messages);
        HttpUtils.post(url, template);
    }

    /**
     * 发送多段消息
     *
     * @param url    机器人地址
     * @param type   消息类型
     * @param target 群号/好友QQ
     * @param str    消息内容
     */
    public static void sendTextMessage(String url, MessageType type, String target, String str) {
        String template = CQMessageTemplate.textListTemplate(type, target, List.of(str));
        HttpUtils.post(url, template);
    }
}
