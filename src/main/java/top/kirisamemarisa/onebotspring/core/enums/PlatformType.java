package top.kirisamemarisa.onebotspring.core.enums;

/**
 * @Author: MarisaDAZE
 * @Description: 平台类型的枚举
 * @Date: 2024/1/20
 */
public enum PlatformType {
    QQ("qq"); // qq平台

    private final String platformType;

    PlatformType(String platformType) {
        this.platformType = platformType;
    }

    public String getPlatformType() {
        return platformType;
    }

    /**
     * 将字符串形式的类型转换为枚举
     *
     * @param sType 字符串形式的类型
     * @return 转换后的结果
     */
    public static PlatformType translate(String sType) {
        PlatformType[] types = PlatformType.values();
        for (PlatformType type : types) {
            int index = type.ordinal();
            String val = type.getPlatformType();
            if (val.equals(sType)) {
                return types[index];
            }
        }
        return null;
    }
}
