package top.kirisamemarisa.onebotspring.commands.sexes;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import top.kirisamemarisa.onebotspring.core.annotation.BotCommand;
import top.kirisamemarisa.onebotspring.core.api.ClientApi;
import top.kirisamemarisa.onebotspring.core.command.MrsCommand;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.GroupReport;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.Sender;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.MAt;
import top.kirisamemarisa.onebotspring.core.enums.MassageType;
import top.kirisamemarisa.onebotspring.core.util.BotUtil;
import top.kirisamemarisa.onebotspring.entity.sexes.GroupSexUser;
import top.kirisamemarisa.onebotspring.entity.sexes.GroupSexWife;
import top.kirisamemarisa.onebotspring.entity.system.BotConfig;
import top.kirisamemarisa.onebotspring.enums.CommandType;
import top.kirisamemarisa.onebotspring.service.sexes.IGroupSexDetailService;
import top.kirisamemarisa.onebotspring.service.sexes.IGroupSexUserService;
import top.kirisamemarisa.onebotspring.service.sexes.IGroupSexWifeService;
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

    @Resource
    private IGroupSexUserService sexUserService;

    @Resource
    private IGroupSexWifeService sexWifeService;

    @Resource
    private IGroupSexDetailService sexDetailService;

    /*
     * 命令格式:
     *   @<target> /cmd [count<次>]
     *   /cmd <target> [count<次>]
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

    @Transactional
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
                Sender sender = groupReport.getSender();
                String userId = sender.getUserId();
                List<MAt> ats = CommandUtil.getAts(groupReport);
                String target;
                int count;
                switch (ats.size()) {
                    // /指令 <target> [count次]
                    case 0 -> {
                        String command = CommandUtil.concatenateMText(groupReport);
                        String trimmed = CommandUtil.trimCommand(command, "/摸头");
                        if (trimmed == null) {
                            String content = "指令格式有误哦，详情请查看涩涩帮助(´•ω•̥`)";
                            sendErrorMessage(url, groupId, content);
                            return;
                        }
                        String[] array = CommandUtil.getTargetAndCount(trimmed, CommandType.TYPE_2);
                        if (array == null) {
                            String content = "指令格式有误哦，详情请查看涩涩帮助(´•ω•̥`)";
                            sendErrorMessage(url, groupId, content);
                            return;
                        }
                        target = array[0];
                        count = Integer.parseInt(array[0]);
                    }
                    // @<target> /指令 [count次]
                    case 1 -> {
                        target = ats.get(0).getMention();
                        String command = CommandUtil.concatenateMText(groupReport);
                        String trimmed = CommandUtil.trimCommand(command, "/摸头");
                        if (trimmed == null) {
                            String content = "指令格式有误哦，详情请查看涩涩帮助(´•ω•̥`)";
                            sendErrorMessage(url, groupId, content);
                            return;
                        }
                        String[] array = CommandUtil.getTargetAndCount(trimmed, CommandType.TYPE_1);
                        if (array == null) {
                            String content = "指令格式有误哦，详情请查看涩涩帮助(´•ω•̥`)";
                            sendErrorMessage(url, groupId, content);
                            return;
                        }
                        count = Integer.parseInt(array[1]);
                    }
                    // 其它情况
                    default -> {
                        String content = "指令格式有误哦，详情请查看涩涩帮助(´•ω•̥`)";
                        sendErrorMessage(url, groupId, content);
                        return;
                    }
                }
                System.out.println(target + ", " + count);

                QueryWrapper<GroupSexWife> sexWifeWrapper = new QueryWrapper<>();
                sexWifeWrapper.eq("GROUP_ID", groupId);
                sexWifeWrapper.eq("USER_QQ", userId);
                sexWifeWrapper.eq("WIFE_QQ", target)
                        .or()
                        .eq("LOVE_NAME", target);
                List<GroupSexWife> sexWives = sexWifeService.list(sexWifeWrapper);
                switch (sexWives.size()) {
                    // 没有找到群老婆
                    case 0 -> {
                        String content = "没有找到爱称／QQ为“" + target + "”的群老婆，是不是群老婆未绑定或者QQ／爱称输入有误？";
                        sendErrorMessage(url, groupId, content);
                        return;
                    }

                    // 找到一个群老婆
                    case 1 -> {
                        GroupSexWife sexWife = sexWives.get(0);

                    }

                    // 找到多个群老婆
                    default -> {
                        StringBuilder sb = new StringBuilder();
                        sexWives.forEach(wife -> sb.append(wife.getLoveName()).append("、"));
                        if (sb.length() > 1) sb.delete(sb.length() - 1, sb.length());
                        String content = "爱称似乎有点模糊，您要找" + sb + "中的哪一位呢？";
                        sendErrorMessage(url, groupId, content);
                        return;
                    }
                }
                template = MassageTemplate.groupTextTemplateSingle(groupId, "0");
            }
        }
        // System.out.println("模板: " + template);
        HttpUtils.post(url, template);
    }

    private void sendErrorMessage(String url, String groupId, String msg) {
        String template = MassageTemplate.groupTextTemplateSingle(groupId, msg);
        HttpUtils.post(url, template);
    }
}
