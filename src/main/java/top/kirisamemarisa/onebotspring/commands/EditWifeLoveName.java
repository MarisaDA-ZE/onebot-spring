package top.kirisamemarisa.onebotspring.commands;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import top.kirisamemarisa.onebotspring.core.annotation.BotCommand;
import top.kirisamemarisa.onebotspring.core.api.ClientApi;
import top.kirisamemarisa.onebotspring.core.command.MrsCommand;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.GroupReport;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.Sender;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.Message;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.MAt;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.MText;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.base.MData;
import top.kirisamemarisa.onebotspring.core.enums.ContentType;
import top.kirisamemarisa.onebotspring.core.enums.MassageType;
import top.kirisamemarisa.onebotspring.core.util.BotUtil;
import top.kirisamemarisa.onebotspring.entity.sexes.GroupSexWife;
import top.kirisamemarisa.onebotspring.entity.system.BotConfig;
import top.kirisamemarisa.onebotspring.service.sexes.IGroupSexWifeService;
import top.kirisamemarisa.onebotspring.utils.CommandUtil;
import top.kirisamemarisa.onebotspring.utils.HttpUtils;
import top.kirisamemarisa.onebotspring.utils.MessageTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @Author: MarisaDAZE
 * @Description: 修改你对群老婆的爱称
 * @Date: 2024/2/22
 */
@Component
@BotCommand
public class EditWifeLoveName implements MrsCommand {

    @Resource
    private BotUtil botUtil;

    @Resource
    private IGroupSexWifeService groupSexWifeService;

    @Override
    public boolean trigger(GroupReport groupReport) {
        MassageType messageType = groupReport.getMessageType();
        // 是否是群聊 并且at了自己
        if (messageType == MassageType.GROUP) {
            boolean isAt = CommandUtil.hasAtSelf(groupReport);
            if (!isAt) return false;
        }

        Message[] messages = groupReport.getMessage();
        // 触发
        for (Message message : messages) {
            MData data = message.getData();
            if (data instanceof MText mText) {
                String context = StrUtil.trim(mText.getText());
                if (triggerText(context)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void action(GroupReport groupReport) {
        System.out.println("修改昵称...");
        MassageType messageType = groupReport.getMessageType();
        String url = null;
        String template = null;
        switch (messageType) {
            case PRIVATE -> {
                BotConfig config = botUtil.getFriendConfig(groupReport);
                url = config.getClientUrl() + ClientApi.SEND_MSG.getApiURL();
                Sender sender = groupReport.getSender();
                String s = "暂不支持私聊中修改昵称！";
                template = MessageTemplate.friendTextTemplateSingle(sender.getUserId(), s);
            }
            case GROUP -> {
                Sender sender = groupReport.getSender();
                String groupId = groupReport.getGroupId();
                BotConfig config = botUtil.getGroupConfig(groupReport);
                url = config.getClientUrl() + ClientApi.SEND_MSG.getApiURL();
                Message[] messages = groupReport.getMessage();
                String target = "";

                // at的形式寻找到目标
                List<String> mentions = Arrays.stream(messages)
                        .filter(msg -> msg.getType() == ContentType.AT)
                        .map(msg -> {
                            MAt at = (MAt) msg.getData();
                            return at.getMention();
                        }).toList();
                switch (mentions.size()) {
                    // 只有一个at并且触发了action，说明这个at一定是at的机器人
                    case 1:
                        break;
                    // 有两个at，说明其中一个是at的机器人，另一个是at的目标
                    case 2:
                        String self = groupReport.getSelfId();
                        String v0 = mentions.get(0);
                        String v1 = mentions.get(1);
                        if (self.equals(v0) && self.equals(v1)) target = self;
                        if (self.equals(v0) && !v0.equals(v1)) target = v1;
                        if (self.equals(v1) && !v0.equals(v1)) target = v0;
                        break;
                    default:
                        String s = "命令格式有误哦！";
                        template = MessageTemplate.groupTextTemplateSingle(groupId, s);
                        HttpUtils.post(url, template);
                        return;
                }
                String loveName = "";
                // 分割 目标（at情况下没有）和新名字
                for (Message message : messages) {
                    ContentType mType = message.getType();
                    // 是连续文本，则按空格分割
                    if (mType == ContentType.TEXT) {
                        MText mText = (MText) message.getData();
                        String text = mText.getText();
                        text = text.trim();
                        String[] textArray = text.split(" ");
                        List<String> sl = Arrays.stream(textArray).filter(s -> !triggerText(s)).toList();
                        switch (sl.size()) {
                            // 只有一段，则这一段就是新昵称
                            case 1 -> loveName = sl.get(0);
                            // 有两段，则第一段是target，第二段是新昵称
                            case 2 -> {
                                target = sl.get(0);
                                loveName = sl.get(1);
                            }
                            default -> {
                                String s = "命令格式有误哦！";
                                template = MessageTemplate.groupTextTemplateSingle(groupId, s);
                                HttpUtils.post(url, template);
                                return;
                            }
                        }
                    }
                }

                System.out.println("target: " + target + ", newNickName: " + loveName);
                QueryWrapper<GroupSexWife> sexWifeWrapper = new QueryWrapper<>();
                sexWifeWrapper.eq("GROUP_ID", groupId);
                sexWifeWrapper.eq("USER_QQ", sender.getUserId());
                sexWifeWrapper.eq("WIFE_QQ", target).or().eq("LOVE_NAME", target);
                GroupSexWife sexWife = groupSexWifeService.getOne(sexWifeWrapper);
                if (ObjectUtils.isEmpty(sexWife)) {
                    String s = "没有找到昵称或QQ为 " + target + " 的老婆，请检查输入";
                    template = MessageTemplate.groupTextTemplateSingle(groupId, s);
                    HttpUtils.post(url, template);
                    return;
                }

                // 同一个人在同一个群里的老婆的昵称不能重复
                sexWifeWrapper.clear();
                sexWifeWrapper.eq("GROUP_ID", groupId);
                sexWifeWrapper.eq("USER_QQ", sender.getUserId());
                sexWifeWrapper.eq("LOVE_NAME", loveName);
                List<GroupSexWife> list = groupSexWifeService.list(sexWifeWrapper);
                if (!ObjectUtils.isEmpty(list)) {
                    String s = "爱称" + loveName + "重复，请重新想一个吧！";
                    template = MessageTemplate.groupTextTemplateSingle(groupId, s);
                    HttpUtils.post(url, template);
                    return;
                }

                System.out.println("修改对象: " + sexWife);
                GroupSexWife sexWifeUpdate = new GroupSexWife();
                sexWifeUpdate.setId(sexWife.getId());
                sexWifeUpdate.setLoveName(loveName);
                groupSexWifeService.updateById(sexWifeUpdate);
                template = MessageTemplate.groupTextTemplateSingle(groupId, "修改成功！");
            }
        }
        // System.out.println("模板: " + template);
        HttpUtils.post(url, template);
    }

    private boolean triggerText(String key) {
        List<String> cmdList = new ArrayList<>();
        cmdList.add("修改老婆爱称");
        cmdList.add("编辑老婆爱称");
        cmdList.add("更改老婆爱称");
        List<String> filter = cmdList.stream().filter(key::contains).toList();
        return filter.size() > 0;
    }
}
