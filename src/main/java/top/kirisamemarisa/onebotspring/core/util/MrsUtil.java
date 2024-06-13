package top.kirisamemarisa.onebotspring.core.util;

/**
 * @Author: MarisaDAZE
 * @Description: 工具类
 * @Date: 2024/2/16
 */
public class MrsUtil {

    /**
     * 将下划线命名转换为驼峰命名
     *
     * @param underscoreName 下划线命名字符串
     * @return 驼峰命名字符串
     */
    public static String toCamelCase(String underscoreName) {
        StringBuilder camelCase = new StringBuilder();
        boolean nextIsUpper = false;
        for (int i = 0; i < underscoreName.length(); i++) {
            char c = underscoreName.charAt(i);
            if (c == '_') {
                nextIsUpper = true;
            } else {
                if (nextIsUpper) {
                    camelCase.append(Character.toUpperCase(c));
                    nextIsUpper = false;
                } else {
                    camelCase.append(c);
                }
            }
        }
        return camelCase.toString();
    }

    /**
     * 将驼峰命名转换为下划线命名
     *
     * @param camelCaseName 驼峰命名字符串
     * @return 下划线命名字符串
     */
    public static String toUnderscore(String camelCaseName) {
        StringBuilder underscore = new StringBuilder();
        for (int i = 0; i < camelCaseName.length(); i++) {
            char c = camelCaseName.charAt(i);
            if (Character.isUpperCase(c)) {
                underscore.append('_');
                underscore.append(Character.toLowerCase(c));
            } else {
                underscore.append(c);
            }
        }
        return underscore.toString();
    }

    /**
     * 判断给定的类类型是否为复杂数据类型。
     * @param type 要检查的类类型
     * @return 如果是复杂数据类型返回true，否则返回false
     */
    public static boolean isComplexDataType(Class<?> type) {
        if (type.isArray()) {
            // 检查数组的组件类型
            Class<?> componentType = type.getComponentType();
            return isComplexDataType(componentType);
        } else {
            return !type.isPrimitive() &&
                    type != String.class &&
                    !type.getName().startsWith("java.lang.");
        }
    }
}
