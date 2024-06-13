package top.kirisamemarisa.onebotspring.core.convert;

import cn.hutool.json.JSONObject;

/**
 * @Author: MarisaDAZE
 * @Description: IFieldTypeConvertHandler 描述
 * @Date: 2024/6/13
 */
public interface IFieldTypeConvertHandler {

    /**
     * 将 JSONObject 转换为实体类
     *
     * @param data  JSONObject类型的数据
     * @param clazz 对应的实体类
     * @param <T>   实体类 T
     */
    <T> void convertField(JSONObject data, Class<T> clazz);
}
