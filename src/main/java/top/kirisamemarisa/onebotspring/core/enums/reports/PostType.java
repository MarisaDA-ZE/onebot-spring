package top.kirisamemarisa.onebotspring.core.enums.reports;

import top.kirisamemarisa.onebotspring.core.enums.base.MrsEnums;

/**
 * @Author: MarisaDAZE
 * @Description: 上报类型
 * <p>一个枚举, 传输使用字符串, 表示上报类型</p>
 * <p>类型参考：<a href="https://docs.go-cqhttp.org/reference/data_struct.html#post-type">Post_Type</a></p>
 * @Date: 2024/07/05
 */
public enum PostType implements MrsEnums<String> {


    /**
     * <p>go-cqhttp: 消息, 例如, 群聊消息</p>
     */
    MESSAGE("message"),

    /**
     * <p>go-cqhttp: 消息发送，例如，bot发送在群里的消息</p>
     */
    MESSAGE_SENT("message_sent"),

    /**
     * <p>go-cqhttp: 请求, 例如, 好友申请</p>
     */
    REQUEST("request"),

    /**
     * <p>go-cqhttp: 通知, 例如, 群成员增加</p>
     */
    NOTICE("notice"),

    /**
     * <p>go-cqhttp: 元事件, 例如, go-cqhttp 心跳包</p>
     */
    META_EVENT("meta_event");

    private final String postType;

    PostType(String postType) {
        this.postType = postType;
    }

    @Override
    public String getValue() {
        return postType;
    }
}
