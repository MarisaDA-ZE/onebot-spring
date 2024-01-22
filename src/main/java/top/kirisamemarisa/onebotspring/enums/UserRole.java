package top.kirisamemarisa.onebotdemo.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: MarisaDAZE
 * @Description: UserRole.描述
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

    public static Set<String> getTextKeySet() {
        UserRole[] values = UserRole.values();
        return Arrays.stream(values).map(e -> e.roleText).collect(Collectors.toSet());
    }

    public static UserRole getRoleText(String text) {
        switch (text) {
            case "owner" -> {
                return UserRole.OWNER;
            }
            case "admin" -> {
                return UserRole.ADMIN;
            }
            case "member" -> {
                return UserRole.MEMBER;
            }
            default -> {
                return UserRole.UNKNOWN;
            }
        }
    }
}
