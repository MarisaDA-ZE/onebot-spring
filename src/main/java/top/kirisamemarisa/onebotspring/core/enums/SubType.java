package top.kirisamemarisa.onebotspring.core.enums;

/**
 * @Author: MarisaDAZE
 * @Description: 消息子类型的枚举
 * @Date: 2024/1/20
 */
public enum SubType {
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
