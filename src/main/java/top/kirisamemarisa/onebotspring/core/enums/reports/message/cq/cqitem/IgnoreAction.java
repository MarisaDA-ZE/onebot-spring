package top.kirisamemarisa.onebotspring.core.enums.reports.message.cq.cqitem;

import top.kirisamemarisa.onebotspring.core.enums.base.MrsEnums;

/**
 * @Author: MarisaDAZE
 * @Description: 无法匿名时是否继续发送
 * @Date: 2024/7/03
 */
public enum IgnoreAction implements MrsEnums<Integer> {
    /**
     * 无法匿名时取消发送
     */
    NOT_SEND(0),

    /**
     * 即使无法匿名也继续发送
     */
    IGNORE(1);

    private final Integer value;

    IgnoreAction(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
