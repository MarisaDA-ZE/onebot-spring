package top.kirisamemarisa.onebotspring.core.enums.reports;

import top.kirisamemarisa.onebotspring.core.enums.base.MrsEnums;

/**
 * @Author: MarisaDAZE
 * @Description: 消息子类型
 * <p>一个枚举, 传输使用字符串, 表示消息子类型.</p>
 * @Date: 2024/1/20
 */
public enum SubType implements MrsEnums<String> {

    /**
     * <p>go-cqhttp: 好友</p>
     */
    FRIEND("friend"),

    /**
     * <p>go-cqhttp: 群聊</p>
     */
    NORMAL("normal"),

    /**
     * <p>go-cqhttp: 匿名</p>
     */
    ANONYMOUS("anonymous"),

    /**
     * <p>go-cqhttp: 群中自身发送</p>
     */
    GROUP_SELF("group_self"),

    /**
     * <p>go-cqhttp: 群临时会话</p>
     */
    GROUP("group"),

    /**
     * <p>go-cqhttp: 系统提示（如「管理员已禁止群内匿名聊天」）</p>
     */
    NOTICE("notice");

    private final String value;

    SubType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
