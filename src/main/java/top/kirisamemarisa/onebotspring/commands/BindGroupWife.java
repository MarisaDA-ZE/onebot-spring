package top.kirisamemarisa.onebotspring.commands;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import top.kirisamemarisa.onebotspring.core.annotation.BotCommand;
import top.kirisamemarisa.onebotspring.core.command.MrsCommand;
import top.kirisamemarisa.onebotspring.core.entity.GroupReport;
import top.kirisamemarisa.onebotspring.core.entity.Sender;
import top.kirisamemarisa.onebotspring.core.entity.massage.Massage;
import top.kirisamemarisa.onebotspring.core.entity.massage.data.MAt;
import top.kirisamemarisa.onebotspring.core.entity.massage.data.MText;
import top.kirisamemarisa.onebotspring.core.enums.ContentType;
import top.kirisamemarisa.onebotspring.core.enums.MassageType;
import top.kirisamemarisa.onebotspring.core.util.BotUtil;
import top.kirisamemarisa.onebotspring.entity.system.BotConfig;
import top.kirisamemarisa.onebotspring.utils.HttpUtils;
import top.kirisamemarisa.onebotspring.utils.MassageTemplate;


/**
 * @Author: MarisaDAZE
 * @Description: 绑定群老婆
 * @Date: 2024/2/21
 */
@Component
@BotCommand
public class BindGroupWife implements MrsCommand {

    @Resource
    private BotUtil botUtil;

    @Resource
    private RedisTemplate<String, GroupReport> redisTemplate;

    @Override
    public boolean trigger(GroupReport groupReport) {
        MassageType messageType = groupReport.getMessageType();
        // 是群聊消息
        int flag = 0;
        if (messageType == MassageType.GROUP) {
            Massage[] messages = groupReport.getMessage();
            for (Massage message : messages) {
                // 消息中有回复
                if (message.getType() == ContentType.REPLY) {
                    flag++;
                }
                // 文本关键字
                if (message.getType() == ContentType.TEXT) {
                    MText context = (MText) message.getData();
                    String text = context.getText().trim();
                    if ("绑定群老婆".equals(text)) {
                        flag++;
                        break;
                    }
                }
            }
        }
        return flag == 2;
    }

    @Override
    public void action(GroupReport groupReport) {
        MassageType messageType = groupReport.getMessageType(); // 是群聊消息
        String url = "";
        String template = "";
        switch (messageType) {
            case PRIVATE -> {
                BotConfig config = botUtil.getFriendConfig(groupReport);
                url = config.getClientUrl() + "/send_msg";
                Sender sender = groupReport.getSender();
                String content = "在私聊中无法绑定群老婆哦~";
                template = MassageTemplate.friendTextTemplateSingle(sender.getUserId(), content);
            }
            case GROUP -> {
                BotConfig config = botUtil.getFriendConfig(groupReport);
                url = config.getClientUrl() + "/send_msg";
                Massage[] messages = groupReport.getMessage();
                String target = "";
                for (Massage message : messages) {
                    if (message.getType() == ContentType.AT) {
                        MAt at = (MAt) message.getData();
                        target = at.getMention();
                        break;
                    }
                }
                System.out.println("绑定: " + target);
                String s = MassageTemplate.getGroupMemberInfo(groupReport.getGroupId(), target);
                String res = HttpUtils.post(config.getClientUrl() + "/get_group_member_info", s);
                System.out.println(res);

                String content = "绑定成功！";
                template = MassageTemplate.groupTextTemplateSingle(groupReport.getGroupId(), content);
            }
        }
        HttpUtils.post(url, template);
    }
}
