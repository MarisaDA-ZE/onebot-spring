package top.kirisamemarisa.onebotspring.core.enums.reports.message.cq.cqitem;

import top.kirisamemarisa.onebotspring.core.enums.base.MrsEnums;

/**
 * @Author: MarisaDAZE
 * @Description: 推荐好友/群
 * @Date: 2024/7/03
 */
public enum CQRecType implements MrsEnums<String> {
    /**
     * 推荐为好友
     */
    PRIVATE("qq"),

    /**
     * 推荐为群聊
     */
    GROUP("group");

    private final String value;

    CQRecType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
