package top.kirisamemarisa.onebotspring.commands;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import top.kirisamemarisa.onebotspring.core.annotation.BotCommand;
import top.kirisamemarisa.onebotspring.core.command.MrsCommand;
import top.kirisamemarisa.onebotspring.core.entity.GroupReport;
import top.kirisamemarisa.onebotspring.core.entity.massage.Massage;
import top.kirisamemarisa.onebotspring.core.entity.massage.data.MText;
import top.kirisamemarisa.onebotspring.core.entity.massage.data.base.MData;
import top.kirisamemarisa.onebotspring.entity.system.BotConfig;
import top.kirisamemarisa.onebotspring.service.IBotConfigService;
import top.kirisamemarisa.onebotspring.utils.HttpUtils;
import top.kirisamemarisa.onebotspring.utils.MassageTemplate;

import static top.kirisamemarisa.onebotspring.common.Constant.CONFIG_SUFFIX;

@Component
@BotCommand
public class HelpCommand implements MrsCommand {

    @Resource
    private IBotConfigService botConfigService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean trigger(GroupReport groupReport) {
        System.out.println(groupReport);
        Massage[] messages = groupReport.getMessage();
        for (Massage message : messages) {
            MData data = message.getData();
            if (data instanceof MText mText) {
                String context = StrUtil.trim(mText.getText());
                if ("帮助".equals(context) || "help".equals(context)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void action(GroupReport groupReport) {
        System.out.println("查看帮助...");
        String s =
                """
                        帮助菜单
                        ============
                        @我 <命令><参数>即可使用。
                        -发骚话（文本）：@我 来点骚话
                        -发骚话（语音）：@我 来点语音骚话
                        -复读机（语音）：@我 复读：<内容>
                        -复读机（文本）：@我 复读文本：<内容>
                        -随机图片：@我 来点二次元
                        -开空调：@我 开空调
                        -闭空调：@我 关空调
                        -设置温度：@我 设置温度<温度>
                        -查询温度：@我 查询温度
                        -帮助：@我 帮助
                        -帮助：@我 help
                        ============""";
        System.out.println(s);
        BotConfig config = getConfig(groupReport);
        String clientURL = config.getClientUrl();
        String url = clientURL + "/send_msg";
        String template = MassageTemplate.groupTextTemplateSingle(groupReport.getGroupId(), s);
        System.out.println("模板: " + template);
        HttpUtils.post(url, template);
    }

    private BotConfig getConfig(GroupReport groupReport) {
        String sourceId = groupReport.getGroupId();
        BotConfig config = null;
        try {
            config = (BotConfig) redisTemplate.opsForValue().get(sourceId + CONFIG_SUFFIX);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ObjectUtils.isEmpty(config)) config = botConfigService.getBotConfigByTargetId(sourceId);
        if (ObjectUtils.isEmpty(config)) {
            System.out.println("当前Q群暂无任何配置,正在新建默认配置...");
            config = botConfigService.createConfig(groupReport.getSelfId(), sourceId, groupReport.getMessageType());
        }
        return config;
    }
}
