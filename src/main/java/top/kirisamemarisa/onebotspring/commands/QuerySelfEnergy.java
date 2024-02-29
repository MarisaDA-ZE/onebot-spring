package top.kirisamemarisa.onebotspring.commands;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import top.kirisamemarisa.onebotspring.common.Constant;
import top.kirisamemarisa.onebotspring.core.annotation.BotCommand;
import top.kirisamemarisa.onebotspring.core.api.ClientApi;
import top.kirisamemarisa.onebotspring.core.command.MrsCommand;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.GroupReport;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.Sender;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.Message;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.MText;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.base.MData;
import top.kirisamemarisa.onebotspring.core.enums.MassageType;
import top.kirisamemarisa.onebotspring.core.util.BotUtil;
import top.kirisamemarisa.onebotspring.entity.sexes.GroupSexUser;
import top.kirisamemarisa.onebotspring.entity.system.BotConfig;
import top.kirisamemarisa.onebotspring.service.sexes.IGroupSexUserService;
import top.kirisamemarisa.onebotspring.utils.CommandUtil;
import top.kirisamemarisa.onebotspring.utils.HttpUtils;
import top.kirisamemarisa.onebotspring.utils.MassageTemplate;
import top.kirisamemarisa.onebotspring.utils.Utils;

import java.util.Date;


/**
 * @Author: MarisaDAZE
 * @Description: 查询自身精力
 * @Date: 2024/2/16
 */
@Component
@BotCommand
public class QuerySelfEnergy implements MrsCommand {

    @Resource
    private BotUtil botUtil;

    @Resource
    private IGroupSexUserService groupSexUserService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

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
                if ("查询精力".equals(context) || "查询自身精力".equals(context) || "查询剩余精力".equals(context)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void action(GroupReport groupReport) {
        System.out.println("查询自身精力...");
        int maxEnergy = 100;    // 最多100精力
        MassageType messageType = groupReport.getMessageType();
        String url = null;
        String template = null;
        switch (messageType) {
            case PRIVATE -> {
                BotConfig config = botUtil.getFriendConfig(groupReport);
                url = config.getClientUrl() + ClientApi.SEND_MSG.getApiURL();
                Sender sender = groupReport.getSender();
                String s = "私聊下无法查看自身精力哦~";
                template = MassageTemplate.friendTextTemplateSingle(sender.getUserId(), s);
            }
            case GROUP -> {
                BotConfig config = botUtil.getGroupConfig(groupReport);
                url = config.getClientUrl() + ClientApi.SEND_MSG.getApiURL();
                String groupId = groupReport.getGroupId();
                Sender sender = groupReport.getSender();
                Object lastRecover = redisTemplate.opsForValue().get("last_recover_energy_time");
                Date lastRecoverTime = (Date) lastRecover;
                QueryWrapper<GroupSexUser> sexUserWrapper = new QueryWrapper<>();
                sexUserWrapper.eq("GROUP_ID", groupId);
                sexUserWrapper.eq("USER_QQ", sender.getUserId());
                GroupSexUser sexUser = groupSexUserService.getOne(sexUserWrapper);
                if (ObjectUtils.isEmpty(sexUser)) {
                    String s = "账号还未注册，请先绑定群老婆后再试~";
                    template = MassageTemplate.groupTextTemplateSingle(groupId, s);
                } else {
                    long l = System.currentTimeMillis();
                    if (!ObjectUtils.isEmpty(lastRecoverTime)) {
                        l = lastRecoverTime.getTime();
                    }
                    l += Constant.MILLIS_15;
                    String time = Utils.getRemainingTime(new Date(l));
                    int currentEnergy = sexUser.getRemainingEnergy();
                    String s0 = currentEnergy == maxEnergy ? "" : "（" + time + "后回复15点精力）";
                    String s = "当前账号精力: " + currentEnergy + "/" + maxEnergy + s0;
                    template = MassageTemplate.groupTextTemplateSingle(groupId, s);
                }
            }
        }
        HttpUtils.post(url, template);
    }
}
