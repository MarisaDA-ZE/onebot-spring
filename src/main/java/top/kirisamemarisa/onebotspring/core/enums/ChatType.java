package top.kirisamemarisa.onebotspring.core.enums;

/**
 * @Author: MarisaDAZE
 * @Description: 聊天类型的枚举
 * @Date: 2024/1/20
 */
public enum ChatType {
    GROUP("group"),     // 群聊消息
    PRIVATE("private"); // 私聊消息

    private final String messageType;

    ChatType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageType() {
        return messageType;
    }

    /**
     * 将字符串形式的类型转换为枚举
     *
     * @param sType 字符串形式的类型
     * @return 转换后的结果
     */
    public static ChatType translate(String sType) {
        ChatType[] types = ChatType.values();
        for (ChatType type : types) {
            int index = type.ordinal();
            String val = type.getMessageType();
            if (val.equals(sType)) {
                return types[index];
            }
        }
        return null;
    }
}
