package top.kirisamemarisa.onebotspring.core.entity.massage.data.base;

import top.kirisamemarisa.onebotspring.core.entity.massage.data.MAt;
import top.kirisamemarisa.onebotspring.core.entity.massage.data.MImage;
import top.kirisamemarisa.onebotspring.core.entity.massage.data.MText;
import top.kirisamemarisa.onebotspring.core.entity.massage.data.MUnknown;
import top.kirisamemarisa.onebotspring.core.enums.MassageType;
import top.kirisamemarisa.onebotspring.core.util.MrsUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: MarisaDAZE
 * @Description: BaseMassage.描述
 * @Date: 2024/2/15
 */
public abstract class MData {

    /**
     * 转换为具体的MData对象
     *
     * @param map json对象
     * @return 转换结果
     */
    public static MData translate(Map<?, ?> map) {
        String t = (String) map.get("type");
        LinkedHashMap<?, ?> d = (LinkedHashMap<?, ?>) map.get("data");
        try {
            MassageType type = MassageType.translate(t);
            if (type != null) {
                switch (type) {
                    // at消息
                    case AT -> {
                        return toMassageObject(d, MAt.class);
                    }
                    // 文本消息
                    case TEXT -> {
                        return toMassageObject(d, MText.class);
                    }
                    // 图片消息
                    case IMAGE -> {
                        return toMassageObject(d, MImage.class);
                    }
                    // 其他类型写不了，因为上报的都是unknown
                    // 一些未知类型
                    case UNKNOWN -> {
                        return new MUnknown();
                    }
                }
            }
            return null;
        } catch (Exception e) {
            System.out.println("消息内容转换出错了！");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将LinkedHashMap转换为clazz类型的对象
     *
     * @param map   .
     * @param clazz .
     * @param <T>   .
     * @return .
     */
    private static <T> T toMassageObject(LinkedHashMap<?, ?> map, Class<T> clazz) {
        T res = null;
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            Object instance = constructor.newInstance();
            res = clazz.cast(instance);
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                String underscore = MrsUtil.toUnderscore(fieldName);
                // 两种命名方式都找一下
                if (map.containsKey(fieldName)) {
                    Object value = map.get(fieldName);
                    field.set(res, value);
                } else if (map.containsKey(underscore)) {
                    Object value = map.get(underscore);
                    field.set(res, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
