package top.kirisamemarisa.onebotspring.core.util;

import top.kirisamemarisa.onebotspring.core.enums.base.MrsEnums;

/**
 * @Author: MarisaDAZE
 * @Description: 枚举工具类
 * @Date: 2024/2/16
 */
public class EnumUtils {

    /**
     * 尝试将任意类型的clazz转换为E类型
     *
     * @param s     字符串类型的枚举值
     * @param clazz E
     * @param <E>   .
     * @return .
     */
    public static <E extends Enum<E>> E translate(String s, Class<?> clazz) {
        if (s == null || clazz == null) return null;
        try {
            Class<E> eClazz = (Class<E>) clazz;
            return Enum.valueOf(eClazz, s.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * 根据枚举值获取对应的枚举
     *
     * @param value 枚举值
     * @param clazz 枚举class
     * @param <T>   枚举值的类型
     * @param <E>   枚举类value的类型
     * @return 对应的枚举
     */
    public static <T, E extends MrsEnums<T>> E fromValue(T value, Class<E> clazz) {
        for (E enumInstance : clazz.getEnumConstants()) {
            if (enumInstance.getValue().equals(value)) {
                return enumInstance;
            }
        }
        throw new IllegalArgumentException("No enum constant " + clazz.getCanonicalName() + "." + value);
    }
}
