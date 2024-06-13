package top.kirisamemarisa.onebotspring.annotation;

import java.lang.annotation.*;

/**
 * @Author: MarisaDAZE
 * @Description: 消息字段所对应的列
 * @Date: 2024/6/13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface MessageField {
    String value();
}
