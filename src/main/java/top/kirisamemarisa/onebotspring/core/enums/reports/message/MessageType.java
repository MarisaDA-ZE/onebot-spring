package top.kirisamemarisa.onebotspring.core.enums.reports.message;

import top.kirisamemarisa.onebotspring.core.enums.base.MrsEnums;

/**
 * @Author: MarisaDAZE
 * @Description: 聊天类型
 * <p>一个枚举, 传输使用字符串, 表示消息类型.</p>
 * @Date: 2024/1/20
 */
public enum MessageType implements MrsEnums<String> {

    /**
     * 群聊消息
     */
    GROUP("group"),

    /**
     * 私聊消息
     */
    PRIVATE("private");

    private final String value;

    MessageType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
