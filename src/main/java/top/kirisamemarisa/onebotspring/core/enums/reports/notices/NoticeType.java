package top.kirisamemarisa.onebotspring.core.enums.reports.notices;

import top.kirisamemarisa.onebotspring.core.enums.base.MrsEnums;

/**
 * @Author: MarisaDAZE
 * @Description: 通知类型
 * <p>一个枚举, 传输使用字符串, 表示通知类型.</p>
 * <p>类型参考：<a href="https://docs.go-cqhttp.org/reference/data_struct.html#post-notice-type">notice type</a></p>
 * @Date: 2024/07/05
 */
public enum NoticeType implements MrsEnums<String> {

    /**
     * 群文件上传
     */
    GROUP_UPLOAD("group_upload"),

    /**
     * 群管理员变更
     */
    GROUP_ADMIN("group_admin"),

    /**
     * 群成员减少
     */
    GROUP_DECREASE("group_decrease"),

    /**
     * 群成员增加
     */
    GROUP_INCREASE("group_increase"),

    /**
     * 群成员禁言
     */
    GROUP_BAN("group_ban"),

    /**
     * 好友添加
     */
    FRIEND_ADD("friend_add"),

    /**
     * 群消息撤回
     */
    GROUP_RECALL("group_recall"),

    /**
     * 好友消息撤回
     */
    FRIEND_RECALL("friend_recall"),

    /**
     * 群名片变更
     */
    GROUP_CARD("group_card"),

    /**
     * 离线文件上传
     */
    OFFLINE_FILE("offline_file"),

    /**
     * 客户端状态变更
     */
    CLIENT_STATUS("client_status"),

    /**
     * 精华消息
     */
    ESSENCE("essence"),

    /**
     * 系统通知
     */
    NOTIFY("notify");

    private final String noticeType;

    NoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    @Override
    public String getValue() {
        return noticeType;
    }
}
