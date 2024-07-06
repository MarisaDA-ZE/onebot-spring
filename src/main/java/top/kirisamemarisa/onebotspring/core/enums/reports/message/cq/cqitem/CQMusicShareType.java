package top.kirisamemarisa.onebotspring.core.enums.reports.message.cq.cqitem;

import top.kirisamemarisa.onebotspring.core.enums.base.MrsEnums;

/**
 * @Author: MarisaDAZE
 * @Description: 音乐分享类型
 * @Date: 2024/7/03
 */
public enum CQMusicShareType implements MrsEnums<String> {
    /**
     * QQ音乐
     */
    QQ("qq"),

    /**
     * 网易云音乐
     */
    NETEASE("163"),

    /**
     * 虾米音乐
     */
    XM("xm"),

    /**
     * 自定义分享
     */
    CUSTOM("custom");

    private final String value;

    CQMusicShareType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
