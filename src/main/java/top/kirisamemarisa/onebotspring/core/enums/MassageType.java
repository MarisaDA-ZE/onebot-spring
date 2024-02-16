package top.kirisamemarisa.onebotspring.core.enums;

/**
 * @Author: MarisaDAZE
 * @Description: 聊天类型的枚举
 * @Date: 2024/1/20
 */
public enum MassageType {
    GROUP("group"),     // 群聊消息
    PRIVATE("private"); // 私聊消息

    private final String messageType;

    MassageType(String messageType) {
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
    public static MassageType translate(String sType) {
        MassageType[] types = MassageType.values();
        for (MassageType type : types) {
            int index = type.ordinal();
            String val = type.getMessageType();
            if (val.equals(sType)) {
                return types[index];
            }
        }
        return null;
    }
}
