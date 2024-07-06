package top.kirisamemarisa.onebotspring.commands.sexes;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import top.kirisamemarisa.onebotspring.core.annotation.BotCommand;
import top.kirisamemarisa.onebotspring.core.api.ClientApi;
import top.kirisamemarisa.onebotspring.core.command.MrsCommand;
import top.kirisamemarisa.onebotspring.core.entity.reports.base.MrsReport;
import top.kirisamemarisa.onebotspring.core.entity.reports.common.Sender;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.GroupReport;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.PrivateReport;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.base.MessageReport;
import top.kirisamemarisa.onebotspring.core.enums.reports.message.MessageType;
import top.kirisamemarisa.onebotspring.core.util.BotUtil;
import top.kirisamemarisa.onebotspring.entity.onebot.sexes.GroupSexWife;
import top.kirisamemarisa.onebotspring.entity.onebot.system.BotConfig;
import top.kirisamemarisa.onebotspring.service.onebot.sexes.IGroupSexWifeService;
import top.kirisamemarisa.onebotspring.utils.HttpUtils;
import top.kirisamemarisa.onebotspring.core.util.CQMessageTemplate;

import java.util.List;


/**
 * @Author: MarisaDAZE
 * @Description: 查询本群老婆数量
 * @Date: 2024/2/21
 */
@Component
@BotCommand
public class QueryGroupWifeCount implements MrsCommand {

    @Resource
    private BotUtil botUtil;

    @Value("${mrs-bot.default-client-url}")
    private String defaultClientURL;

    @Resource
    private IGroupSexWifeService groupSexWifeService;

    @Override
    public boolean trigger(MrsReport report) {
//        if (!(report instanceof GroupReport groupReport)) {
//            return false;
//        }
//        MessageType messageType = groupReport.getMessageType();
//        // 是否是群聊 并且at了自己
//        if (messageType == MessageType.GROUP) {
//            boolean isAt = CommandUtil.hasAtSelf(groupReport);
//            if (!isAt) return false;
//        }
//
//        Message[] messages = groupReport.getMessage();
//        // 触发
//        for (Message message : messages) {
//            MData data = message.getData();
//            if (data instanceof MText mText) {
//                String context = StrUtil.trim(mText.getText());
//                if (
//                        "查询群老婆人数".equals(context) ||
//                        "查询群老婆".equals(context) ||
//                        "查询老婆".equals(context)
//                ) {
//                    return true;
//                }
//            }
//        }
        return false;
    }

    @Override
    @Transactional
    public void action(MrsReport report) {
        if (!(report instanceof MessageReport messageReport)) {
            return;
        }
        MessageType messageType = messageReport.getMessageType(); // 是群聊消息
        String url = "";
        String template = "";
        switch (messageType) {
            case PRIVATE -> {
                PrivateReport privateReport = (PrivateReport) messageReport;
                BotConfig config = botUtil.getFriendConfig(privateReport);
                url = config.getClientUrl() + ClientApi.SEND_MSG.getApiURL();
                Sender sender = privateReport.getSender();
                String content = "无法在私聊中查询群老婆人数哦~";
                template = CQMessageTemplate.friendTextTemplateSingle(sender.getUserId(), content);
            }
            case GROUP -> {
                GroupReport groupReport = (GroupReport) messageReport;
                String groupId = groupReport.getGroupId();
                Sender sender = groupReport.getSender();
                BotConfig config = botUtil.getGroupConfig(groupReport);
                // 配置文件不存在
                if (ObjectUtils.isEmpty(config)) {
                    String s = "当前账号暂未注册，请先注册后使用哦~";
                    template = CQMessageTemplate.groupTextTemplateSingle(groupReport.getGroupId(), s);
                    url = defaultClientURL + ClientApi.SEND_MSG.getApiURL();
                    HttpUtils.post(url, template);
                    return;
                }
                url = config.getClientUrl() + ClientApi.SEND_MSG.getApiURL();

                // 查询关联关系
                QueryWrapper<GroupSexWife> sexWifeWrapper = new QueryWrapper<>();
                sexWifeWrapper.eq("GROUP_ID", groupId);
                sexWifeWrapper.eq("USER_QQ", sender.getUserId());
                List<GroupSexWife> sexWifeList = groupSexWifeService.list(sexWifeWrapper);

                // 群老婆列表
                String templateContext;
                if (sexWifeList.size() > 0) {
                    StringBuilder context = new StringBuilder("您在本群共有 " + sexWifeList.size() + " 位老婆，她们分别是：");
                    sexWifeList.forEach(wife -> {
                        String nickName = wife.getNickName();
                        String loveName = wife.getLoveName();
                        context.append(loveName)
                                .append("（")
                                .append(nickName)
                                .append("）");

                        context.append("、");
                    });
                    context.delete(context.length() - 1, context.length());
                    templateContext = context.toString();
                } else {
                    templateContext = "您在本群还没有群老婆哟，快来把可爱的群友们娶回家吧！";
                }
                template = CQMessageTemplate.groupTextTemplateSingle(groupReport.getGroupId(), templateContext);
            }
        }
        HttpUtils.post(url, template);
    }
}
