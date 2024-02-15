package top.kirisamemarisa.onebotspring.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: MarisaDAZE
 * @Description: 机器人命令注解
 * <p>在类上标注此注解，将会将这个类加载到命令池中</p>
 * @Date: 2024/2/15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BotCommand {

}
