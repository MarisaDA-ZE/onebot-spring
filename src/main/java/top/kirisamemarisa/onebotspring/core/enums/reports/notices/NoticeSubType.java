package top.kirisamemarisa.onebotspring.core.enums.reports.notices;

import top.kirisamemarisa.onebotspring.core.enums.base.MrsEnums;

/**
 * @Author: MarisaDAZE
 * @Description: 群通知子类型
 * @Date: 2024/07/05
 */
public enum NoticeSubType implements MrsEnums<String> {

    /**
     * <p>go-cqhttp: 管理员已同意入群</p>
     * <p style="color: #FF9933;">所属子类：群成员增加</p>
     */
    APPROVE("approve"),

    /**
     * <p>go-cqhttp: 管理员邀请入群</p>
     * <p style="color: #FF9933;">所属子类：群成员增加</p>
     */
    INVITE("invite"),

    /**
     * <p>go-cqhttp: 主动退群</p>
     * <p style="color: #FF6666;">所属子类：群成员减少</p>
     */
    LEAVE("leave"),

    /**
     * <p>go-cqhttp: 成员被踢</p>
     * <p style="color: #FF6666;">所属子类：群成员减少</p>
     */
    KICK("kick"),

    /**
     * <p>go-cqhttp: 登录号被踢</p>
     * <p style="color: #FF6666;">所属子类：群成员减少</p>
     */
    KICK_ME("kick_me"),


    /**
     * <p>go-cqhttp: 设置管理员</p>
     * <p style="color: #00CCFF;">所属子类：群管理员变动</p>
     */
    SET("set"),

    /**
     * <p>go-cqhttp: 取消管理员</p>
     * <p style="color: #00CCFF;">所属子类：群管理员变动</p>
     */
    UNSET("unset"),

    /**
     * <p>go-cqhttp: 禁言</p>
     * <p style="color: #FF3333;">所属子类：群禁言</p>
     */
    BAN("ban"),

    /**
     * <p>go-cqhttp: 解除禁言</p>
     * <p style="color: #FF3333;">所属子类：群禁言</p>
     */
    LIFT_BAN("lift_ban"),

    /**
     * <p>go-cqhttp: 戳一戳</p>
     * <p style="color: CC33FF;">所属子类：戳一戳（双击头像）</p>
     */
    POKE("poke"),

    /**
     * <p>go-cqhttp: 运气王提示</p>
     * <p style="color: FFFF00;">所属子类：群红包运气王提示</p>
     */
    LUCKY_KING("lucky_king"),

    /**
     * <p>go-cqhttp: 荣誉变更</p>
     * <p style="color: FFCC00;">所属子类：群成员荣誉变更提示</p>
     */
    HONOR("honor"),

    /**
     * <p>go-cqhttp: 头衔变更</p>
     * <p style="color: 9966FF;">所属子类：群成员头衔变更</p>
     */
    TITLE("title"),

    /**
     * <p>go-cqhttp: 添加精华消息</p>
     * <p style="color: 6666FF;">所属子类：精华消息变更</p>
     */
    ADD("add"),

    /**
     * <p>go-cqhttp: 移除精华消息</p>
     * <p style="color: 3366FF;">所属子类：精华消息变更</p>
     */
    DELETE("delete");

    private final String value;

    NoticeSubType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
