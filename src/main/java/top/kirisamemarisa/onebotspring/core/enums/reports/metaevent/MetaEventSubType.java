package top.kirisamemarisa.onebotspring.core.enums.reports.metaevent;

import top.kirisamemarisa.onebotspring.core.enums.base.MrsEnums;

/**
 * @Author: MarisaDAZE
 * @Description: 生命周期子类型
 * @Date: 2024/07/05
 */
public enum MetaEventSubType implements MrsEnums<String> {

    ENABLE("enable"),
    DISABLE("disable"),
    CONNECT("connect");

    private final String value;

    MetaEventSubType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
