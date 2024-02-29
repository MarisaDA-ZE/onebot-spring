package top.kirisamemarisa.onebotspring.utils;

import cn.hutool.core.util.StrUtil;
import org.springframework.util.ObjectUtils;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.GroupReport;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.Message;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.MAt;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.MText;
import top.kirisamemarisa.onebotspring.core.enums.ContentType;
import top.kirisamemarisa.onebotspring.core.enums.MassageType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: MarisaDAZE
 * @Description: 命令工具类
 * @Date: 2024/2/16
 */
public class CommandUtil {

    /**
     * 是否含有at（自己的）消息
     *
     * @param groupReport 上报消息
     * @return .
     */
    public static boolean hasAtSelf(GroupReport groupReport) {
        String selfId = groupReport.getSelfId();
        MassageType messageType = groupReport.getMessageType();
        Message[] messages = groupReport.getMessage();
        if (messageType == MassageType.GROUP) {
            List<?> ats = Arrays.stream(messages).filter(msg -> {
                // 存在at则判断at的是不是自己
                if (msg.getType() == ContentType.AT) {
                    MAt at = (MAt) msg.getData();
                    String target = at.getMention();
                    return (selfId + "").equals(target);
                }
                return false;
            }).toList();
            return !ObjectUtils.isEmpty(ats);
        }
        return false;
    }

    /**
     * 获取消息中at消息的次数
     *
     * @param groupReport .
     * @return .
     */
    public static int getMsgAtCount(GroupReport groupReport) {
        MassageType messageType = groupReport.getMessageType();
        if (messageType == MassageType.PRIVATE) return 0;
        Message[] messages = groupReport.getMessage();
        int count = 0;
        for (Message msg : messages) {
            ContentType type = msg.getType();
            if (type == ContentType.AT) count++;
        }
        return count;
    }

    /**
     * 获取At列表
     *
     * @param groupReport .
     * @return .
     */
    public static List<MAt> getAts(GroupReport groupReport) {
        MassageType messageType = groupReport.getMessageType();
        if (messageType == MassageType.PRIVATE) return new ArrayList<>();
        Message[] messages = groupReport.getMessage();
        List<MAt> ats = new ArrayList<>();
        for (Message msg : messages) {
            ContentType type = msg.getType();
            if (type == ContentType.AT) {
                MAt at = (MAt) msg.getData();
                ats.add(at);
            }
        }
        return ats;
    }

    /**
     * 排除第一次at自己
     * <p>如果一个上报中有多条at自己的消息，那只会被排除掉一次</p>
     * <p>这个方法还会去除掉空的消息元素</p>
     *
     * @param groupReport 上报消息
     * @return 排除后的上报
     */
    public static GroupReport excludeAtSelfOne(GroupReport groupReport) {
        Message[] messages = groupReport.getMessage();
        String selfId = groupReport.getSelfId();
        List<Message> messageList = new ArrayList<>();
        boolean first = true;
        for (Message message : messages) {
            ContentType type = message.getType();
            switch (type) {
                case AT -> {
                    MAt at = (MAt) message.getData();
                    String atId = at.getMention();
                    if (first) {
                        // 是第一次at自己
                        if ((atId + "").equals(selfId)) {
                            first = false;
                        } else {
                            messageList.add(message);
                        }
                    } else {
                        messageList.add(message);
                    }
                }
                case TEXT -> {
                    MText mText = (MText) message.getData();
                    String text = mText.getText();
                    if (StrUtil.isNotBlank(text.trim())) {
                        messageList.add(message);
                    }
                }
                default -> messageList.add(message);
            }
        }
        groupReport.setMessage(messageList.toArray(new Message[0]));
        return groupReport;
    }

    /**
     * 将上报消息中的字符串信息拼成一段
     *
     * @param groupReport 上报消息
     * @return .
     */
    public static String concatenateMText(GroupReport groupReport) {
        Message[] messages = groupReport.getMessage();
        return concatenateMText(messages);
    }

    /**
     * 将上报消息中的字符串信息拼成一段
     *
     * @param messages 消息数组
     * @return .
     */
    public static String concatenateMText(Message[] messages) {
        return concatenateMText(messages, " ");
    }

    /**
     * 将上报消息中的字符串信息拼成一段
     *
     * @param messages 消息数组
     * @param split    分隔符
     * @return .
     */
    public static String concatenateMText(Message[] messages, String split) {
        StringBuilder sb = new StringBuilder();
        for (Message msg : messages) {
            ContentType type = msg.getType();
            if (type == ContentType.TEXT) {
                MText mText = (MText) msg.getData();
                String text = mText.getText();
                String trimmed = text.trim();
                if (StrUtil.isNotBlank(trimmed)) sb.append(trimmed).append(split);
            }
        }
        int start = sb.length() - split.length();
        sb.delete(Math.max(start, 0), sb.length());
        return sb.toString();
    }

    /**
     * 修剪掉多余的命令
     * @param text  待修剪的字符串
     * @param trims
     * @return
     */
    public static String trimText(String text, List<String> trims) {
        return null;
    }

}
