package top.kirisamemarisa.onebotspring.enums;


/**
 * @Author: MarisaDAZE
 * @Description: 涩涩类型的枚举
 * @Date: 2024/2/23
 */
public enum SexType {
    // tips: 如果要改枚举名，那要将数据库中存储的枚举名一并修改
    NORMAL("正常的"),      // 正常活动
    SEX_HARASS("悻骚扰"),  // 性骚扰
    NIPPLE("奈头"),       // 奈头
    CLITORIS("小红豆"),    // 银地
    ORAL_SEX("口角"),     // 口角
    UDDER("儒角"),        // 儒角
    HAND_SEX("手冲"),     // 手冲
    COITUS_SEX("烧杯"),   // 笔角
    ANAL_SEX("港角"),     // 刚角
    FOOT_SEX("足角");     // 足角

    private final String sexType;

    SexType(String sexType) {
        this.sexType = sexType;
    }

    public String getTypeText() {
        return this.sexType;
    }

    /**
     * 将字符串形式的类型转换为枚举
     *
     * @param sType 字符串形式的类型
     * @return 转换后的结果
     */
    public static SexType translate(String sType) {
        SexType[] types = SexType.values();
        for (SexType type : types) {
            int index = type.ordinal();
            String val = type.getTypeText();
            if (val.equals(sType)) {
                return types[index];
            }
        }
        return null;
    }
}
