package top.kirisamemarisa.onebotspring.commands.sexes;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import top.kirisamemarisa.onebotspring.core.annotation.BotCommand;
import top.kirisamemarisa.onebotspring.core.api.ClientApi;
import top.kirisamemarisa.onebotspring.core.command.MrsCommand;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.GroupReport;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.Sender;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.Message;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.MAt;
import top.kirisamemarisa.onebotspring.core.enums.MassageType;
import top.kirisamemarisa.onebotspring.core.util.BotUtil;
import top.kirisamemarisa.onebotspring.entity.system.BotConfig;
import top.kirisamemarisa.onebotspring.utils.CommandUtil;
import top.kirisamemarisa.onebotspring.utils.HttpUtils;
import top.kirisamemarisa.onebotspring.utils.MassageTemplate;

import java.util.List;


/**
 * @Author: MarisaDAZE
 * @Description: 摸头
 * @Date: 2024/2/28
 */
@Component
@BotCommand
public class TouchHead implements MrsCommand {

    @Resource
    private BotUtil botUtil;

    /*
     * 命令格式:
     *   @<target> /cmd [count<次>] 或 /cmd <target> [count<次>]
     *   /cmd
     *   /cmd 10次
     *   /cmd 目标
     *   /cmd 目标 11次
     * */
    @Override
    public boolean trigger(GroupReport groupReport) {
        String cmdString = CommandUtil.concatenateMText(groupReport);
        return (cmdString + "").contains("/摸头");
    }

    @Override
    public void action(GroupReport groupReport) {
        System.out.println("摸头...");
        MassageType messageType = groupReport.getMessageType();
        String url = null;
        String template = null;
        switch (messageType) {
            case PRIVATE -> {
                BotConfig config = botUtil.getFriendConfig(groupReport);
                url = config.getClientUrl() + ClientApi.SEND_MSG.getApiURL();
                Sender sender = groupReport.getSender();
                String s = "私聊不允许摸头(´•ω•̥`)";
                template = MassageTemplate.friendTextTemplateSingle(sender.getUserId(), s);
            }
            case GROUP -> {
                BotConfig config = botUtil.getGroupConfig(groupReport);
                url = config.getClientUrl() + ClientApi.SEND_MSG.getApiURL();
                String groupId = groupReport.getGroupId();
                Message[] messages = groupReport.getMessage();
                List<MAt> ats = CommandUtil.getAts(groupReport);
                switch (ats.size()) {
                    // /指令 <target> [count次]
                    case 0 -> {
                        String command = CommandUtil.concatenateMText(groupReport);
                        System.out.println(command);
                    }
                    // @<target> /指令 [count次]
                    case 1 -> {
                        MAt target = ats.get(0);
                        String command = CommandUtil.concatenateMText(groupReport);
                        System.out.println(target.getMention() + ", " + command);
                    }
                    default -> {
                        String content = "指令格式有误哦，详情请查看涩涩帮助(´•ω•̥`)";
                        template = MassageTemplate.groupTextTemplateSingle(groupId, content);
                        HttpUtils.post(url, template);
                        return;
                    }
                }
                template = MassageTemplate.groupTextTemplateSingle(groupId, "0");
            }
        }
        // System.out.println("模板: " + template);
        HttpUtils.post(url, template);
    }
}
