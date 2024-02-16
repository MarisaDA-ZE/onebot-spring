package top.kirisamemarisa.onebotspring.commands;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import top.kirisamemarisa.onebotspring.core.annotation.BotCommand;
import top.kirisamemarisa.onebotspring.core.command.MrsCommand;
import top.kirisamemarisa.onebotspring.core.entity.GroupReport;
import top.kirisamemarisa.onebotspring.core.entity.Sender;
import top.kirisamemarisa.onebotspring.core.entity.massage.Massage;
import top.kirisamemarisa.onebotspring.core.entity.massage.data.MAt;
import top.kirisamemarisa.onebotspring.core.entity.massage.data.MText;
import top.kirisamemarisa.onebotspring.core.entity.massage.data.base.MData;
import top.kirisamemarisa.onebotspring.core.enums.ContentType;
import top.kirisamemarisa.onebotspring.core.enums.MassageType;
import top.kirisamemarisa.onebotspring.core.util.BotUtil;
import top.kirisamemarisa.onebotspring.entity.system.BotConfig;
import top.kirisamemarisa.onebotspring.utils.HttpUtils;
import top.kirisamemarisa.onebotspring.utils.MassageTemplate;

import java.util.Arrays;
import java.util.List;


@Component
@BotCommand
public class HelpCommand implements MrsCommand {

    @Resource
    private BotUtil botUtil;

    @Override
    public boolean trigger(GroupReport groupReport) {
        String selfId = groupReport.getSelfId();
        MassageType messageType = groupReport.getMessageType();
        Massage[] messages = groupReport.getMessage();
        // 如果是群聊，则需要被at才做出反应
        if (messageType == MassageType.GROUP) {
            List<?> ats = Arrays.stream(messages).filter(msg -> {
                if (msg.getType() == ContentType.AT) {
                    MAt at = (MAt) msg.getData();
                    String target = at.getMention();
                    return (selfId + "").equals(target);
                }
                return false;
            }).toList();
            if (ObjectUtils.isEmpty(ats)) return false;
        }
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
