package top.kirisamemarisa.onebotspring.core.enums;

/**
 * @Author: MarisaDAZE
 * @Description: 消息内容的类型
 * @Date: 2024/1/20
 */
public enum ContentType {
    TEXT("text"),   // 纯文本
    FACE("face"),   // QQ表情
    IMAGE("image"), // 图片
    VOICE("voice"), // 语音
    VIDEO("video"), // 视频
    AT("at"),       // @某人
    RPS("rps"),     // 猜拳魔法表情
    DICE("dice"),   // 掷骰子魔法表情
    SHAKE("shake"), // 窗口抖动（戳一戳）
    POKE("poke"),   // 戳一戳
    ANONYMOUS("anonymous"), // 匿名发消息
    CONTACT("contact"),     // 推荐好友、推荐群
    LOCATION("location"),   // 位置
    MUSIC("music"), // 音乐分享、音乐自定义分享
    REPLY("reply"), // 回复
    FORWARD("forward"),     // 合并转发
    NODE("node"),           // 合并转发节点、合并转发自定义节点
    XML("xml"),     // XML 消息
    JSON("json"),   // JSON 消息
    UNKNOWN("unknown");// 一些不知道类型的消息统一是返回的这个

    private final String contentType;

    ContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    /**
     * 将字符串形式的类型转换为枚举
     *
     * @param sType 字符串形式的类型
     * @return 转换后的结果
     */
    public static ContentType translate(String sType) {
        ContentType[] types = ContentType.values();
        for (ContentType type : types) {
            int index = type.ordinal();
            String val = type.getContentType();
            if (val.equals(sType)) {
                return types[index];
            }
        }
        return null;
    }
}
