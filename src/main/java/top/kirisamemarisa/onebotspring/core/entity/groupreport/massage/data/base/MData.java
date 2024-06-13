package top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.base;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.ObjectUtils;
import top.kirisamemarisa.onebotspring.annotation.MessageField;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.*;
import top.kirisamemarisa.onebotspring.core.enums.ContentType;
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
     * @param o json对象
     * @return 转换结果
     */
    public static MData translate(JSONObject o) {
        String t = o.getString("type");
        JSONObject d = o.getJSONObject("data");
        return translateByType(t, d);
    }

    /**
     * 转换为具体的MData对象
     *
     * @param map Map
     * @return 转换结果
     */
    public static MData translate(Map<?, ?> map) {
        String t = (String) map.get("type");
        LinkedHashMap<?, ?> d = (LinkedHashMap<?, ?>) map.get("data");
        return translateByType(t, d);
    }

    /**
     * 根据类型和内容MData
     *
     * @param t 类型
     * @param d 内容
     * @return .
     */
    private static MData translateByType(String t, Object d) {
        try {
            ContentType type = ContentType.translate(t);
            if (type != null) {
                switch (type) {
                    // at消息
                    case AT -> {
                        return toMDataChild(d, MAt.class);
                    }
                    // 回复消息
                    case REPLY -> {
                        return toMDataChild(d, MReply.class);
                    }
                    // 文本消息
                    case TEXT -> {
                        return toMDataChild(d, MText.class);
                    }
                    // 图片消息
                    case IMAGE -> {
                        return toMDataChild(d, MImage.class);
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
            System.err.println("消息内容转换出错了！");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 转换为MData对象的具体子类
     * <p>如MAt、MReply、MText...等，具体由clazz决定</p>
     *
     * @param o     内容
     * @param clazz 类型
     * @param <T>   .
     * @return .
     */
    private static <T> T toMDataChild(Object o, Class<T> clazz) {
        if (o instanceof JSONObject params) {
            System.out.println(params);
            return toMassageObject(params, clazz);
        } else if (o instanceof LinkedHashMap<?, ?> params) {
            return toMassageObject(params, clazz);
        }
        return null;
    }

    /**
     * 将JSONObject转换为clazz类型的对象
     *
     * @param o     .
     * @param clazz .
     * @param <T>   .
     * @return .
     */
    private static <T> T toMassageObject(JSONObject o, Class<T> clazz) {
        T res = null;
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            Object instance = constructor.newInstance();
            res = clazz.cast(instance);
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                MessageField annotation = field.getAnnotation(MessageField.class);
                String fieldName;
                if (annotation == null) {
                    fieldName = field.getName();
                } else {
                    fieldName = annotation.value();
                }
                Object value = o.get(fieldName);
                field.set(res, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
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
                MessageField annotation = field.getAnnotation(MessageField.class);
                String fieldName;
                if (annotation == null) {
                    fieldName = field.getName();
                } else {
                    fieldName = annotation.value();
                }
                Object value = map.get(fieldName);
                field.set(res, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
