package top.kirisamemarisa.onebotspring.core.enums;

/**
 * @Author: MarisaDAZE
 * @Description: 消息子类型的枚举
 * @Date: 2024/1/20
 */
public enum SubType {
    GROUP("group"),         // 群聊消息
    FRIEND("friend"),       // 私聊消息

    // 下面这仨是文档上写的，但不知道为啥，没有这三种类型，估计是作者还没改
    NORMAL("normal"),       // 正常消息
    NOTICE("notice"),       // 匿名消息
    ANONYMOUS("anonymous"); // 系统提示（如「管理员已禁止群内匿名聊天」）

    private final String subType;

    SubType(String subType) {
        this.subType = subType;
    }

    public String getSubType() {
        return subType;
    }

    /**
     * 将字符串形式的类型转换为枚举
     *
     * @param sType 字符串形式的类型
     * @return 转换后的结果
     */
    public static SubType translate(String sType) {
        SubType[] types = SubType.values();
        for (SubType type : types) {
            int index = type.ordinal();
            String val = type.getSubType();
            if (val.equals(sType)) {
                return types[index];
            }
        }
        return null;
    }
}
