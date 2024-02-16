package top.kirisamemarisa.onebotspring.core.util;

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
    @SuppressWarnings("unchecked")
    public static <E extends Enum<E>> E translate(String s, Class<?> clazz) {
        if (s == null || clazz == null) return null;
        try {
            Class<E> eClazz = (Class<E>) clazz;
            return Enum.valueOf(eClazz, s.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
