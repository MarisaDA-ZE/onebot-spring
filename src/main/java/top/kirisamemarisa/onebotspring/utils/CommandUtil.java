package top.kirisamemarisa.onebotspring.utils;

import cn.hutool.core.util.StrUtil;
import org.springframework.util.ObjectUtils;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.GroupReport;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.CQMessage;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.CQAt;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.CQText;
import top.kirisamemarisa.onebotspring.core.enums.reports.message.MessageType;
import top.kirisamemarisa.onebotspring.core.enums.reports.message.cq.CQMessageType;
import top.kirisamemarisa.onebotspring.enums.CommandType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static top.kirisamemarisa.onebotspring.common.Constant.DEFAULT_CMD_SEPARATOR;

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
        MessageType messageType = groupReport.getMessageType();
        CQMessage[] messages = groupReport.getMessages();
        if (messageType == MessageType.GROUP) {
            List<?> ats = Arrays.stream(messages).filter(msg -> {
                // 存在at则判断at的是不是自己
                if (msg.getType() == CQMessageType.AT) {
                    CQAt at = (CQAt) msg.getData();
                    String target = at.getQq();
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
        MessageType messageType = groupReport.getMessageType();
        if (messageType == MessageType.PRIVATE) return 0;
        CQMessage[] messages = groupReport.getMessages();
        int count = 0;
        for (CQMessage msg : messages) {
            CQMessageType type = msg.getType();
            if (type == CQMessageType.AT) count++;
        }
        return count;
    }

    /**
     * 获取At列表
     *
     * @param groupReport .
     * @return .
     */
    public static List<CQAt> getAts(GroupReport groupReport) {
        MessageType messageType = groupReport.getMessageType();
        if (messageType == MessageType.PRIVATE) return new ArrayList<>();
        CQMessage[] messages = groupReport.getMessages();
        List<CQAt> ats = new ArrayList<>();
        for (CQMessage msg : messages) {
            CQMessageType type = msg.getType();
            if (type == CQMessageType.AT) {
                CQAt at = (CQAt) msg.getData();
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
        CQMessage[] messages = groupReport.getMessages();
        String selfId = groupReport.getSelfId();
        List<CQMessage> messageList = new ArrayList<>();
        boolean first = true;
        for (CQMessage message : messages) {
            CQMessageType type = message.getType();
            switch (type) {
                case AT -> {
                    CQAt at = (CQAt) message.getData();
                    String atId = at.getQq();
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
                    CQText cqText = (CQText) message.getData();
                    String text = cqText.getText();
                    if (StrUtil.isNotBlank(text.trim())) {
                        messageList.add(message);
                    }
                }
                default -> messageList.add(message);
            }
        }
        groupReport.setMessages(messageList.toArray(new CQMessage[0]));
        return groupReport;
    }

    /**
     * 检查command中是否有满足任何一个cmds列表的情况
     *
     * @param command 命令字符串
     * @param cmds    命令列表
     * @return 是否匹配
     */
    public static boolean containsCommands(String command, List<String> cmds) {
        return containsCommands(command, DEFAULT_CMD_SEPARATOR, cmds);
    }

    /**
     * 检查command中是否有满足任何一个cmds列表的情况
     *
     * @param command   命令字符串
     * @param separator 分隔符
     * @param cmds      命令列表
     * @return 是否匹配
     */
    public static boolean containsCommands(String command, String separator, List<String> cmds) {
        String[] split = command.split(separator);
        boolean flag = false;
        for (String str : split) {
            String v = str.trim();
            if (cmds.contains(v + "")) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 将上报消息中的字符串信息拼成一段
     *
     * @param groupReport 上报消息
     * @return .
     */
    public static String concatenateMText(GroupReport groupReport) {
        CQMessage[] messages = groupReport.getMessages();
        return concatenateMText(messages);
    }

    /**
     * 将上报消息中的字符串信息拼成一段
     *
     * @param messages 消息数组
     * @return .
     */
    public static String concatenateMText(CQMessage[] messages) {
        return concatenateMText(messages, DEFAULT_CMD_SEPARATOR);
    }

    /**
     * 将上报消息中的字符串信息拼成一段
     *
     * @param messages 消息数组
     * @param split    分隔符
     * @return .
     */
    public static String concatenateMText(CQMessage[] messages, String split) {
        StringBuilder sb = new StringBuilder();
        for (CQMessage msg : messages) {
            CQMessageType type = msg.getType();
            if (type == CQMessageType.TEXT) {
                CQText cqText = (CQText) msg.getData();
                String text = cqText.getText();
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
     *
     * @param text 待修剪的字符串
     * @param trim 要修建的内容
     * @return .
     */
    public static String trimCommand(String text, String trim) {
        return trimCommand(text, DEFAULT_CMD_SEPARATOR, Collections.singletonList(trim));
    }

    /**
     * 修剪掉多余的命令
     *
     * @param text  待修剪的字符串
     * @param trims 要修建的内容
     * @return .
     */
    public static String trimCommand(String text, List<String> trims) {
        return trimCommand(text, DEFAULT_CMD_SEPARATOR, trims);
    }

    /**
     * 修剪掉多余的命令
     *
     * @param text      待修剪的字符串
     * @param separator 分隔符
     * @param trims     要修剪的内容
     * @return .
     */
    public static String trimCommand(String text, String separator, List<String> trims) {
        if (StrUtil.isBlank(text)) return null;
        String[] split = text.trim().split(separator);
        if (split.length == 0) return null;
        StringBuilder sb = new StringBuilder();
        for (String str : split) {
            String s = str.trim();
            if (!trims.contains(s) && StrUtil.isNotBlank(s))
                sb.append(s).append(separator);
        }
        int start = sb.length() - separator.length();
        if (start > 0) sb.delete(start, sb.length());
        return sb.toString();
    }

    /**
     * 从指令中获取目标和数量
     *
     * @param command 指令（不带指令头）
     * @param type    参数类型
     * @return 结果[target|null, count|1]
     */
    public static String[] getTargetAndCount(String command, CommandType type) {
        return getTargetAndCount(command, DEFAULT_CMD_SEPARATOR, type);
    }

    /**
     * 从指令中获取目标和数量
     *
     * @param command   指令（不带指令头）
     * @param separator 分隔符
     * @param type      参数类型
     * @return 结果[target|null, count|1]
     */
    public static String[] getTargetAndCount(String command, String separator, CommandType type) {
        String[] array = command.split(separator);
        String[] split = Arrays.stream(array).filter(StrUtil::isNotBlank).toArray(String[]::new);
        // 切分失败 或者长度不对
        if (split.length > 2) return null;
        String[] res = new String[2];
        switch (type) {
            // @<target> /cmd [count次]
            case TYPE_1 -> {
                // 没有传count，默认为1
                if (split.length == 0) return new String[]{null, "1"};
                String countStr = split[0];
                String count = getCommandCount(countStr);
                return ObjectUtils.isEmpty(count) ? null : new String[]{null, count};
            }

            // /cmd <target> [count次]
            // <target> [count次]
            case TYPE_2 -> {
                // 没有包含必填的target，错误
                if (split.length == 0) return null;
                switch (split.length) {
                    // target
                    case 1 -> {
                        return new String[]{split[0], "1"};
                    }
                    // target count次
                    case 2 -> {
                        String target = split[0];
                        // target有问题
                        if (StrUtil.isBlank(target)) return null;
                        String count = getCommandCount(split[1]);
                        // count 有问题
                        if (StrUtil.isEmpty(count)) return null;
                        return new String[]{target, count};
                    }
                    default -> {
                        return null;
                    }
                }
            }
        }
        return res;
    }

    /**
     * 从指令字符串中取出次数
     *
     * @param cmd 指令
     * @return 次数(String | null)
     */
    private static String getCommandCount(String cmd) {
        String regex = "\\d+次";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cmd);
        if (matcher.matches()) {
            String group = matcher.group(0);
            // 找到的字符串有问题
            if (StrUtil.isBlank(group)) return null;
            String s = group.replaceAll("[^0-9]", "");
            // 只保留数字后的值有问题
            if (StrUtil.isBlank(s)) return null;
            // 没问题了
            return s;
        } else {
            // 指令有误
            return null;
        }
    }
}
