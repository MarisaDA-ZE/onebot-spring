package top.kirisamemarisa.onebotspring.core.loader;


import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import top.kirisamemarisa.onebotspring.core.annotation.BotCommand;
import top.kirisamemarisa.onebotspring.core.command.MrsCommand;
import top.kirisamemarisa.onebotspring.core.entity.GroupReport;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 命令加载器
 */
@Component
public class CommandLoader {
    private final List<MrsCommand> commands = new ArrayList<>();

    @Value("${mrs-bot.commands}")
    private String packageName;

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 在这里加载所有的命令
     */
    @Bean
    public void loader() {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(BotCommand.class);
        try {
            for (Class<?> clazz : annotatedClasses) {
                MrsCommand command = (MrsCommand) applicationContext.getBean(clazz);
                commands.add(command);
                System.out.println("加载指令对象: " + command);
                /*
                 * clazz.newInstance(); 此方法在java9中已经弃用，故使用以下的方式进行实例化
                 * 这种方式子类不太好托管给Spring，故废弃
                 * Constructor<?> constructor = clazz.getDeclaredConstructor();
                 * Object instance = constructor.newInstance();
                 * commands.add((MrsCommand) instance);
                 * */
            }
            System.out.println("加载完毕，共 " + commands.size() + " 条命令。");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行命令
     *
     * @param groupReport 消息
     */
    public void execute(GroupReport groupReport) {
        System.out.println(commands);
        System.out.println(groupReport);
        for (MrsCommand cmd : commands) {
            if (cmd.trigger(groupReport)) {
                cmd.action(groupReport);
            }
        }
    }
}
