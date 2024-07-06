package top.kirisamemarisa.onebotspring.core.enums.common;

import top.kirisamemarisa.onebotspring.core.enums.base.MrsEnums;

/**
 * @Author: MarisaDAZE
 * @Description: 用户角色的枚举
 * @Date: 2024/1/20
 */
public enum UserSex implements MrsEnums<String> {
    MALE("male"),       // 男
    FEMALE("female"),   // 女
    UNKNOWN("unknown"); // 未知


    // 1: 群主，2：管理员，3: 普通群员
    private final String value;

    UserSex(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

}
