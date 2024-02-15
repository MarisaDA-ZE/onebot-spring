package top.kirisamemarisa.onebotspring.loader;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import top.kirisamemarisa.onebotspring.commands.base.MrsCommand;
import top.kirisamemarisa.onebotspring.entity.Massage;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 命令加载器
 */
@Component
public class CommandLoader {
    private final List<MrsCommand> commands = new ArrayList<>();

    @Value("${mrs-bot.commands}")
    private String commandPath;

    /**
     * 在这里加载所有的命令
     */
    @Bean
    public void loader() {
        System.out.println(commandPath);
        String relativePath = commandPath.replaceAll("\\.", "/");
        System.out.println(relativePath);
        try {
            Class<?> loaderClass = CommandLoader.class;
            // 使用ClassLoader获取资源URL
            URL resourceUrl = loaderClass.getClassLoader().getResource(relativePath);
            URL rbUrl = loaderClass.getClassLoader().getResource(relativePath+"/base");
            // 将URL转换为文件路径
            File folder = new File(resourceUrl.toURI());
            File baseFile = new File(rbUrl.toURI());
            URLClassLoader loader = URLClassLoader.newInstance(new URL[]{baseFile.toURI().toURL()});
            System.out.println(loader.getName());
            for (File file : folder.listFiles()) {
                if(file.isFile()){
                    String className = file.getName().replace(".class", "");
                    System.out.println(file.getName()+", "+className);
                    Class<?> clazz = Class.forName(className, true, loader);
                    if (MrsCommand.class.isAssignableFrom(clazz)) {
                        MrsCommand cmd = (MrsCommand) clazz.newInstance();
                        commands.add(cmd);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 执行命令
     *
     * @param massage 消息
     */
    public void execute(Massage massage) {
        System.out.println(commands);
        System.out.println(massage);
        for (MrsCommand cmd : commands) {
            if (cmd.trigger(massage)) {
                cmd.action(massage);
            }
        }
    }
}
