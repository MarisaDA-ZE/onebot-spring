package top.kirisamemarisa.onebotspring.commands;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import top.kirisamemarisa.onebotspring.core.annotation.BotCommand;
import top.kirisamemarisa.onebotspring.core.api.ClientApi;
import top.kirisamemarisa.onebotspring.core.command.MrsCommand;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.GroupReport;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.Sender;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.Massage;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.MText;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.base.MData;
import top.kirisamemarisa.onebotspring.core.enums.MassageType;
import top.kirisamemarisa.onebotspring.core.util.BotUtil;
import top.kirisamemarisa.onebotspring.entity.sexes.GroupSexWife;
import top.kirisamemarisa.onebotspring.entity.sexes.GroupWife;
import top.kirisamemarisa.onebotspring.entity.system.BotConfig;
import top.kirisamemarisa.onebotspring.service.sexes.IGroupSexWifeService;
import top.kirisamemarisa.onebotspring.service.sexes.IGroupWifeService;
import top.kirisamemarisa.onebotspring.utils.CommandUtil;
import top.kirisamemarisa.onebotspring.utils.HttpUtils;
import top.kirisamemarisa.onebotspring.utils.MassageTemplate;

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
    private IGroupWifeService groupWifeService;

    @Resource
    private IGroupSexWifeService groupSexWifeService;

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
                if ("查询群老婆人数".equals(context)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    @Transactional
    public void action(GroupReport groupReport) {
        MassageType messageType = groupReport.getMessageType(); // 是群聊消息
        String url = "";
        String template = "";
        switch (messageType) {
            case PRIVATE -> {
                BotConfig config = botUtil.getFriendConfig(groupReport);
                url = config.getClientUrl() + ClientApi.SEND_MSG.getApiURL();
                Sender sender = groupReport.getSender();
                String content = "无法在私聊中查询群老婆人数哦~";
                template = MassageTemplate.friendTextTemplateSingle(sender.getUserId(), content);
            }
            case GROUP -> {
                String groupId = groupReport.getGroupId();
                Sender sender = groupReport.getSender();
                BotConfig config = botUtil.getFriendConfig(groupReport);
                // 配置文件不存在
                if (ObjectUtils.isEmpty(config)) {
                    String s = "当前账号暂未注册，请先注册后使用哦~";
                    template = MassageTemplate.groupTextTemplateSingle(groupReport.getGroupId(), s);
                    url = defaultClientURL + ClientApi.SEND_MSG.getApiURL();
                    HttpUtils.post(url, template);
                    return;
                }
                url = config.getClientUrl() + ClientApi.SEND_MSG.getApiURL();

                // 查询关联关系
                QueryWrapper<GroupSexWife> sexWifeWrapper = new QueryWrapper<>();
                sexWifeWrapper.eq("group_id", groupId);
                sexWifeWrapper.eq("user_qq", sender.getUserId());
                List<GroupSexWife> sexWifeList = groupSexWifeService.list(sexWifeWrapper);
                List<String> qqNums = sexWifeList.stream().map(GroupSexWife::getWifeQq).toList();

                // 群老婆列表
                QueryWrapper<GroupWife> wifeWrapper = new QueryWrapper<>();
                wifeWrapper.eq("group_id", groupId);
                wifeWrapper.eq("user_qq", sender.getUserId());
                List<GroupWife> wifeList = groupWifeService.list(wifeWrapper);
                String templateContext;
                if (wifeList.size() > 0) {
                    StringBuilder context = new StringBuilder("您在本群共有 " + wifeList.size() + " 位老婆，她们分别是：");
                    wifeList.forEach(wife -> {
                        String nickName = wife.getNickName();
                        String qq = wife.getWifeQq();
                        int i = qqNums.indexOf(qq);
                        String pNickName;
                        if (i != -1) {
                            GroupSexWife groupSexWife = sexWifeList.get(i);
                            pNickName = groupSexWife.getNickName();
                            context.append(pNickName)
                                    .append("（")
                                    .append(nickName)
                                    .append("）");
                        } else {
                            context.append(nickName);
                        }
                        context.append("、");
                    });
                    context.delete(context.length() - 1, context.length());
                    templateContext = context.toString();
                } else {
                    templateContext = "您在本群还没有群老婆，快点把群友们娶回家吧！";
                }
                template = MassageTemplate.groupTextTemplateSingle(groupReport.getGroupId(), templateContext);
            }
        }
        HttpUtils.post(url, template);
    }
}
