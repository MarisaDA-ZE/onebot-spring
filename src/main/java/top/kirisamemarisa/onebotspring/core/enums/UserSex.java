package top.kirisamemarisa.onebotspring.core.enums;

/**
 * @Author: MarisaDAZE
 * @Description: 用户角色的枚举
 * @Date: 2024/1/20
 */
public enum UserSex {
    MALE("male"),       // 男
    FEMALE("female"),   // 女
    UNKNOWN("unknown"); // 未知


    // 1: 群主，2：管理员，3: 普通群员
    private final String sex;

    UserSex(String sexText) {
        this.sex = sexText;
    }

    public String getRoleText() {
        return sex;
    }

    /**
     * 将字符串形式的类型转换为枚举
     *
     * @param sType 字符串形式的类型
     * @return 转换后的结果
     */
    public static UserSex translate(String sType) {
        UserSex[] types = UserSex.values();
        for (UserSex type : types) {
            int index = type.ordinal();
            String val = type.getRoleText();
            if (val.equals(sType)) {
                return types[index];
            }
        }
        return null;
    }
}
