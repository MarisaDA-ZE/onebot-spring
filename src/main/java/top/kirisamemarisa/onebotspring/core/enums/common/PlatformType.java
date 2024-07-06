package top.kirisamemarisa.onebotspring.core.enums.common;

import top.kirisamemarisa.onebotspring.core.enums.base.MrsEnums;

/**
 * @Author: MarisaDAZE
 * @Description: 平台类型的枚举
 * @Date: 2024/1/20
 */
public enum PlatformType implements MrsEnums<String> {
    QQ("qq"); // qq平台

    private final String value;

    PlatformType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
