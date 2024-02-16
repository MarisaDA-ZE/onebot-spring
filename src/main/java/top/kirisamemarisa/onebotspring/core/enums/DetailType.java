package top.kirisamemarisa.onebotspring.core.enums;

/**
 * @Author: MarisaDAZE
 * @Description: 详细类型的枚举
 * @Date: 2024/1/20
 */
public enum DetailType {
    GROUP("group"),     // 群聊消息
    FRIEND("friend"),  // 私聊消息
    PRIVATE("private"); // 私聊消息

    private final String detailType;

    DetailType(String detailType) {
        this.detailType = detailType;
    }

    public String getDetailType() {
        return detailType;
    }

    /**
     * 将字符串形式的类型转换为枚举
     *
     * @param sType 字符串形式的类型
     * @return 转换后的结果
     */
    public static DetailType translate(String sType) {
        DetailType[] types = DetailType.values();
        for (DetailType type : types) {
            int index = type.ordinal();
            String val = type.getDetailType();
            if (val.equals(sType)) {
                return types[index];
            }
        }
        return null;
    }
}
