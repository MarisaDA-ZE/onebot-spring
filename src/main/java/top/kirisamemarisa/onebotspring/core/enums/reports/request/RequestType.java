package top.kirisamemarisa.onebotspring.core.enums.reports.request;

import top.kirisamemarisa.onebotspring.core.enums.base.MrsEnums;

/**
 * @Author: MarisaDAZE
 * @Description: 请求类型
 * <p>一个枚举, 传输使用字符串, 表示请求类型.</p>
 * <p>类型参考：<a href="https://docs.go-cqhttp.org/reference/data_struct.html#post-request-type">request type</a></p>
 * @Date: 2024/07/05
 */
public enum RequestType implements MrsEnums<String> {

    /**
     * <p>go-cqhttp: 好友请求</p>
     */
    FRIEND("friend"),

    /**
     * <p>go-cqhttp: 群请求</p>
     */
    GROUP("group");

    private final String value;

    RequestType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
