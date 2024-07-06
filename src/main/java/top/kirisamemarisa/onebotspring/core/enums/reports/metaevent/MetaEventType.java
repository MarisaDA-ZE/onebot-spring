package top.kirisamemarisa.onebotspring.core.enums.reports.metaevent;

import top.kirisamemarisa.onebotspring.core.enums.base.MrsEnums;

/**
 * @Author: MarisaDAZE
 * @Description: 元事件类型
 * <p>一个枚举, 传输使用字符串, 表示元事件类型.</p>
 * @Date: 2024/1/20
 */
public enum MetaEventType implements MrsEnums<String> {

    /**
     * 生命周期
     */
    LIFECYCLE("lifecycle"),

    /**
     * 心跳包
     */
    HEARTBEAT("heartbeat");

    private final String value;

    MetaEventType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
