package top.kirisamemarisa.onebotspring.core.enums.reports.request;

import top.kirisamemarisa.onebotspring.core.enums.base.MrsEnums;

/**
 * @Author: MarisaDAZE
 * @Description: 请求子类型
 * @Date: 2024/07/05
 */
public enum RequestSubType implements MrsEnums<String> {

    /**
     * <p>go-cqhttp: 加群请求</p>
     */
    ADD("add"),

    /**
     * <p>go-cqhttp: 邀请登录号入群</p>
     */
    INVITE("invite");

    private final String value;

    RequestSubType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
