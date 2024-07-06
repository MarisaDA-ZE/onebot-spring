package top.kirisamemarisa.onebotspring.core.enums.reports.notices.honor;

import top.kirisamemarisa.onebotspring.core.enums.base.MrsEnums;

/**
 * @Author: MarisaDAZE
 * @Description: 群成员荣誉类型
 * @Date: 2024/07/05
 */
public enum HonorType implements MrsEnums<String> {

    /**
     * <p>go-cqhttp: 龙王</p>
     */
    TALKATIVE("talkative"),

    /**
     * <p>go-cqhttp: 群聊之火</p>
     */
    PERFORMER("performer"),

    /**
     * <p>go-cqhttp: 快乐源泉</p>
     */
    EMOTION("emotion");

    private final String value;

    HonorType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
