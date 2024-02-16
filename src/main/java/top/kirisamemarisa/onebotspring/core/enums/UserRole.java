package top.kirisamemarisa.onebotspring.core.enums;

/**
 * @Author: MarisaDAZE
 * @Description: 用户角色的枚举
 * @Date: 2024/1/20
 */
public enum UserRole {
    OWNER("owner"),      // 群主
    ADMIN("admin"),      // 管理员
    MEMBER("member"),    // 普通群员
    UNKNOWN("unknown");  // 未知角色


    // 1: 群主，2：管理员，3: 普通群员
    private final String roleText;

    UserRole(String roleText) {
        this.roleText = roleText;
    }

    public String getRoleText() {
        return roleText;
    }

    /**
     * 将字符串形式的类型转换为枚举
     *
     * @param sType 字符串形式的类型
     * @return 转换后的结果
     */
    public static UserRole translate(String sType) {
        UserRole[] types = UserRole.values();
        for (UserRole type : types) {
            int index = type.ordinal();
            String val = type.getRoleText();
            if (val.equals(sType)) {
                return types[index];
            }
        }
        return null;
    }
}
