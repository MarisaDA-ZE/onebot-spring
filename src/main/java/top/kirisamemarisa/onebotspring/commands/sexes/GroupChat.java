package top.kirisamemarisa.onebotspring.commands.sexes;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import top.kirisamemarisa.onebotspring.common.Constant;
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
import top.kirisamemarisa.onebotspring.core.util.CQMessageTemplate;
import top.kirisamemarisa.onebotspring.entity.onebot.sexes.*;
import top.kirisamemarisa.onebotspring.entity.onebot.system.BotConfig;
import top.kirisamemarisa.onebotspring.enums.Emoji;
import top.kirisamemarisa.onebotspring.enums.SexType;
import top.kirisamemarisa.onebotspring.service.onebot.sexes.IGroupSexDetailService;
import top.kirisamemarisa.onebotspring.service.onebot.sexes.IGroupSexUserService;
import top.kirisamemarisa.onebotspring.service.onebot.sexes.IGroupSexWifeService;
import top.kirisamemarisa.onebotspring.service.onebot.sexes.IGroupWifeService;
import top.kirisamemarisa.onebotspring.utils.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @Author: MarisaDAZE
 * @Description: 闲聊
 * @Date: 2024/2/28
 */
@Component
@BotCommand
public class GroupChat implements MrsCommand {

    @Resource
    private BotUtil botUtil;
    @Resource
    private IGroupWifeService wifeService;
    @Resource
    private IGroupSexWifeService sexWifeService;
    @Resource
    private IGroupSexUserService sexUserService;
    @Resource
    private IGroupSexDetailService sexDetailService;

    private final List<String> cmds = new ArrayList<>();

    {
        cmds.add("/闲聊");
        cmds.add("/闲谈");
    }

    /**
     * 命令格式:
     *
     * @<target> /cmd [count<次>]
     * /cmd <target> [count<次>]
     * /cmd
     * /cmd 10次
     * /cmd 目标
     * /cmd 目标 11次
     */
    @Override
    public boolean trigger(MrsReport report) {
//        if (!(report instanceof GroupReport groupReport)) {
//            return false;
//        }
//        String cmdString = CommandUtil.concatenateMText(groupReport);
//        return CommandUtil.containsCommands(cmdString, cmds);
        return false;
    }

    @Transactional
    @Override
    public void action(MrsReport report) {
        System.out.println("闲聊/闲谈...");
        if (!(report instanceof MessageReport messageReport)) {
            return;
        }
        MessageType messageType = messageReport.getMessageType();
        String url = null;
        String template = null;
        switch (messageType) {
            case PRIVATE -> {
                PrivateReport privateReport = (PrivateReport) messageReport;
                BotConfig config = botUtil.getFriendConfig(privateReport);
                url = config.getClientUrl() + ClientApi.SEND_MSG.getApiURL();
                Sender sender = privateReport.getSender();
                String s = "私聊不允许闲谈操作" + Emoji.OTHER_1.getEmoji();
                template = CQMessageTemplate.friendTextTemplateSingle(sender.getUserId(), s);
            }
            case GROUP -> {
                GroupReport groupReport = (GroupReport) messageReport;
                BotConfig config = botUtil.getGroupConfig(groupReport);
                url = config.getClientUrl() + ClientApi.SEND_MSG.getApiURL();
                String groupId = groupReport.getGroupId();
                Sender sender = groupReport.getSender();
                String userQq = sender.getUserId();
                CmdPreParam params = SexObjectUtil.getPreParam(groupReport, cmds);

                // 如果包含错误信息
                if (StrUtil.isNotEmpty(params.getError())) {
                    CQMessageTemplate.sendTextMessage(url, groupId, params.getError());
                    return;
                }

                // 限制次数
                if (params.getCount() > 50) {
                    String content = "嘴都要累费了（不能超过50次哦）" + Emoji.SORROW_9.getEmoji();
                    CQMessageTemplate.sendTextMessage(url, groupId, content);
                    return;
                }

                // 获取群登录用户
                GroupSexUser sexUser = sexUserService.getGroupUserByQQ(groupId, userQq);
                if (ObjectUtils.isEmpty(sexUser)) {
                    System.err.println(userQq + "账号未注册(GroupChat.class)");
                    String content = "您（在本群）还未注册账号，请先注册后使用" + Emoji.SORROW_9.getEmoji();
                    CQMessageTemplate.sendTextMessage(url, groupId, content);
                    return;
                }

                // 获取群老婆列表
                List<GroupSexWife> sexWives = sexWifeService.getGroupWifeByQQ(groupId, userQq, params.getUserId());
                String text;   // 机器人反馈的文本
                switch (sexWives.size()) {
                    // 没有找到群老婆
                    case 0 -> {
                        String content = "没有找到爱称／QQ为“" + params.getUserId() + "”的群老婆，可能为群老婆未绑定或者QQ／爱称输入有误";
                        CQMessageTemplate.sendTextMessage(url, groupId, content);
                        return;
                    }
                    // 找到一个群老婆
                    case 1 -> {
                        GroupSexWife sexWife = sexWives.get(0);
                        String wifeId = sexWife.getWifeId();
                        GroupWife wife = wifeService.getById(wifeId);
                        int count = params.getCount();

                        // 数值计算
                        int expendEnergy = Math.round(Math.max(Math.min(count * 0.35f, 6), 1));     // 本次消耗精力值（1~6）
                        int generateEmotion = Math.round(Math.max(Math.min(count * 0.42f, 4), 1));  // 本次产生情绪值（1~4）
                        int currentIntimate = Math.round(generateEmotion * 0.85f);                  // 本次产生亲密度
                        int intimate = sexWife.getIntimateLevel() + currentIntimate;  // 累计亲密度

                        // 构建涩涩详情
                        GroupSexDetail sexDetail = new GroupSexDetail();
                        sexDetail.setId(SnowflakeUtil.nextId());    // id
                        sexDetail.setGroupId(groupId);              // 群号
                        sexDetail.setUserId(sexWife.getUserId());   // 用户ID
                        sexDetail.setUserQq(userQq);                // 用户QQ
                        sexDetail.setWifeId(wifeId);                // 群老婆ID
                        sexDetail.setWifeQq(wife.getSelfId());      // 群老婆QQ
                        sexDetail.setSexType(SexType.NORMAL);       // 涩涩类型（没有涩涩）
                        sexDetail.setOperationsCount(count);        // 操作次数
                        sexDetail.setExpendEnergy(expendEnergy);        // 本次消耗的精力值
                        sexDetail.setGenerateEmotion(generateEmotion);  // 本次产生的情绪值
                        sexDetail.setIntimate(currentIntimate);         // 本次产生的亲密度
                        sexDetail.setCreateBy(sexWife.getUserId()); // 创建人
                        sexDetail.setCreateTime(new Date());        // 创建时间

                        // 更新群老婆对象
                        GroupWife updateWife = new GroupWife();
                        updateWife.setId(wifeId);       // ID
                        updateWife.setEmotion(wife.getEmotion() + generateEmotion);                 // 情绪值
                        updateWife.setRemainingEnergy(wife.getRemainingEnergy() - expendEnergy);    // 剩余精力
                        updateWife.setUpdateTime(new Date());        // 更新时间
                        updateWife.setUpdateBy(sexWife.getUserId()); // 更新人

                        // 更新涩涩用户
                        GroupSexUser updateSexUser = new GroupSexUser();
                        updateSexUser.setId(sexUser.getId());       // ID
                        updateSexUser.setRemainingEnergy(sexUser.getRemainingEnergy() - expendEnergy);// 消耗精力
                        updateSexUser.setUpdateTime(new Date());    // 更新时间
                        updateSexUser.setUpdateBy(sexUser.getId()); // 更新人

                        // 更新关联表
                        GroupSexWife updateSexWife = new GroupSexWife();
                        updateSexWife.setId(sexWife.getId());           //ID
                        updateSexWife.setIntimateLevel(intimate);       // 累计亲密度
                        updateSexWife.setUpdateTime(new Date());        // 更新时间
                        updateSexWife.setUpdateBy(sexWife.getUserId()); // 更新人

                        // 操作数据库进行更新
                        wifeService.updateById(updateWife);         // 更新群老婆
                        sexUserService.updateById(updateSexUser);   // 更新涩涩群友
                        sexWifeService.updateById(updateSexWife);   // 更新老婆亲密度
                        sexDetailService.save(sexDetail);           // 保存涩涩记录

                        // 返回字符串
                        text = "闲聊成功，" + sexWife.getLoveName() + sexWife.getCallName() +
                                "情绪值+" + generateEmotion + "（" + updateWife.getEmotion() + "／" + Constant.getEmotionDictText(updateWife.getEmotion()) + "）；" +
                                "亲密度+" + currentIntimate + "（" + intimate + "／" + Constant.getIntimateDictText(intimate) + "）；" +
                                "自身精力-" + expendEnergy + "（" + updateSexUser.getRemainingEnergy() + "）；" +
                                sexWife.getCallName() + "精力-" + expendEnergy + "（" + updateWife.getRemainingEnergy() + "）";
                    }
                    // 找到多个群老婆
                    default -> {
                        String content = SexObjectUtil.moreOneTarget(sexWives);
                        CQMessageTemplate.sendTextMessage(url, groupId, content);
                        return;
                    }
                }
                template = CQMessageTemplate.groupTextTemplateSingle(groupId, text);
            }
        }
        HttpUtils.post(url, template);
    }
}
