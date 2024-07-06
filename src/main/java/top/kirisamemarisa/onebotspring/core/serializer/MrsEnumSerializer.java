package top.kirisamemarisa.onebotspring.core.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import top.kirisamemarisa.onebotspring.core.enums.base.MrsEnums;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @Author: MarisaDAZE
 * @Description: 枚举类自定义序列化
 * @Date: 2024/7/6
 */
public class MrsEnumSerializer implements ObjectSerializer {
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        if (object instanceof MrsEnums<?> em) {
            try {
                Object value = em.getValue();
                serializer.write(value.toString());
            } catch (Exception e) {
                // 处理异常，例如打印错误日志
                e.printStackTrace();
                serializer.write(object.toString());
            }
        } else {
            // 默认处理逻辑
            serializer.write(object);
        }
    }
}
