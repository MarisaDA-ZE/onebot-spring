package top.kirisamemarisa.onebotspring.core.enums.common;

import top.kirisamemarisa.onebotspring.core.enums.base.MrsEnums;

/**
 * @Author: MarisaDAZE
 * @Description: 用户角色的枚举
 * @Date: 2024/1/20
 */
public enum UserRole implements MrsEnums<String> {

    /**
     * 群主
     */
    OWNER("owner"),

    /**
     * 管理员
     */
    ADMIN("admin"),

    /**
     * 普通群员
     */
    MEMBER("member");


    // 1: 群主，2：管理员，3: 普通群员
    private final String roleText;

    UserRole(String roleText) {
        this.roleText = roleText;
    }

    @Override
    public String getValue() {
        return roleText;
    }
}
