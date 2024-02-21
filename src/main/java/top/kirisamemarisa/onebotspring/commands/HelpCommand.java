package top.kirisamemarisa.onebotspring.commands;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import top.kirisamemarisa.onebotspring.core.annotation.BotCommand;
import top.kirisamemarisa.onebotspring.core.command.MrsCommand;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.GroupReport;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.Sender;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.Massage;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.MText;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.base.MData;
import top.kirisamemarisa.onebotspring.core.enums.MassageType;
import top.kirisamemarisa.onebotspring.core.util.BotUtil;
import top.kirisamemarisa.onebotspring.entity.system.BotConfig;
import top.kirisamemarisa.onebotspring.utils.CommandUtil;
import top.kirisamemarisa.onebotspring.utils.HttpUtils;
import top.kirisamemarisa.onebotspring.utils.MassageTemplate;


/**
 * @Author: MarisaDAZE
 * @Description: 查看帮助菜单
 * @Date: 2024/2/16
 */
@Component
@BotCommand
public class HelpCommand implements MrsCommand {

    @Resource
    private BotUtil botUtil;

    @Override
    public boolean trigger(GroupReport groupReport) {
        MassageType messageType = groupReport.getMessageType();
        // 是否是群聊 并且at了自己
        if (messageType == MassageType.GROUP) {
            boolean isAt = CommandUtil.groupBeAt(groupReport);
            if (!isAt) return false;
        }

        Massage[] messages = groupReport.getMessage();
        // 触发
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
                        -绑定群老婆：[回复ta的消息] @<目标> 绑定群老婆
                        -群老婆人数：@我 查询群老婆人数
                        -查自身精力：@我 查询自身精力
                        -随机图片：@我 来点二次元 或 随机图片 或 来点涩图 或 来点色图
                        -帮助：@我 帮助 或 help
                        ============""";
        MassageType messageType = groupReport.getMessageType();
        String url = null;
        String template = null;
        switch (messageType) {
            case PRIVATE -> {
                BotConfig config = botUtil.getFriendConfig(groupReport);
                url = config.getClientUrl() + "/send_msg";
                Sender sender = groupReport.getSender();
                template = MassageTemplate.friendTextTemplateSingle(sender.getUserId(), s);
            }
            case GROUP -> {
                BotConfig config = botUtil.getGroupConfig(groupReport);
                url = config.getClientUrl() + "/send_msg";
                String groupId = groupReport.getGroupId();
                template = MassageTemplate.groupTextTemplateSingle(groupId, s);
            }
        }
        // System.out.println("模板: " + template);
        HttpUtils.post(url, template);
    }
}
