package top.kirisamemarisa.onebotspring.core.enums.reports.message.cq;

import top.kirisamemarisa.onebotspring.core.enums.base.MrsEnums;

/**
 * @Author: MarisaDAZE
 * @Description: CQ消息段类型
 * <p><a href="https://docs.go-cqhttp.org/cqcode">go-cqhttp 参考</a></p>
 * <p><a href="https://github.com/botuniverse/onebot-11/blob/master/message/segment.md">onebot-11 参考</a></p>
 * @Date: 2024/07/03
 */
public enum CQMessageType implements MrsEnums<String> {

    /**
     * <p>go-cqhttp: 纯文本</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    TEXT("text"),

    /**
     * <p>go-cqhttp: QQ表情</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    FACE("face"),

    /**
     * <p>go-cqhttp: 语音</p>
     * <p>onebot-11: 同go-cqhttp</p>
     * <p style="color: orange;">
     * 注意: 虽然在onebot-11和go-cqhttp的标准中，
     * 语音都是record，但是LLOnebot的作者语音段用的是voice，所以我这里也用voice
     * </p>
     */
    VOICE("voice"),

    /**
     * <p>go-cqhttp: 视频</p>
     * <p style="color:orange">注意：go-cqhttp-v0.9.38起开始支持发送，需要依赖ffmpeg</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    VIDEO("video"),

    /**
     * <p>go-cqhttp: @某人</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    AT("at"),

    /**
     * <p>go-cqhttp: 猜拳魔法表情</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    RPS("rps"),

    /**
     * <p>go-cqhttp: 掷骰子魔法表情</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    DICE("dice"),

    /**
     * <p>go-cqhttp: 窗口抖动（戳一戳）</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    SHAKE("shake"),

    /**
     * <p>go-cqhttp: 匿名发消息</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    ANONYMOUS("anonymous"),

    /**
     * <p>go-cqhttp: 链接分享</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    SHARE("share"),

    /**
     * <p>go-cqhttp: 推荐好友、推荐群</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    CONTACT("contact"),

    /**
     * <p>go-cqhttp: 位置</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    LOCATION("location"),

    /**
     * <p>go-cqhttp: 音乐分享、音乐自定义分享</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    MUSIC("music"),

    /**
     * <p>go-cqhttp: 图片</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    IMAGE("image"),

    /**
     * <p>go-cqhttp: 回复</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    REPLY("reply"),

    /**
     * <p>go-cqhttp: 红包</p>
     * <p style="color: orange;">onebot-11: 不支持</p>
     */
    REDBAG("redbag"),

    /**
     * <p>go-cqhttp: 戳一戳</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    POKE("poke"),

    /**
     * <p>go-cqhttp: 礼物</p>
     * <p style="color: orange;">onebot-11: 不支持</p>
     */
    GIFT("gift"),

    /**
     * <p>go-cqhttp:  合并转发</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    FORWARD("forward"),

    /**
     * <p>go-cqhttp: 合并转发节点、合并转发自定义节点</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    NODE("node"),

    /**
     * <p>go-cqhttp: XML 消息</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    XML("xml"),

    /**
     * <p>go-cqhttp: JSON 消息</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    JSON("json"),

    /**
     * <p>go-cqhttp: 一种装逼大图</p>
     * <p style="color: orange;">onebot-11: 不支持</p>
     */
    CARD_IMAGE("cardimage");

    private final String value;

    CQMessageType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
