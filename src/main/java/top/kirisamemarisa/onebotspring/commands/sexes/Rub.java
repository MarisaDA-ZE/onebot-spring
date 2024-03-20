package top.kirisamemarisa.onebotspring.commands.sexes;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import top.kirisamemarisa.onebotspring.common.Constant;
import top.kirisamemarisa.onebotspring.core.annotation.BotCommand;
import top.kirisamemarisa.onebotspring.core.api.ClientApi;
import top.kirisamemarisa.onebotspring.core.command.MrsCommand;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.GroupReport;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.Sender;
import top.kirisamemarisa.onebotspring.core.enums.MassageType;
import top.kirisamemarisa.onebotspring.core.util.BotUtil;
import top.kirisamemarisa.onebotspring.entity.sexes.*;
import top.kirisamemarisa.onebotspring.entity.system.BotConfig;
import top.kirisamemarisa.onebotspring.enums.Emoji;
import top.kirisamemarisa.onebotspring.enums.SexType;
import top.kirisamemarisa.onebotspring.service.sexes.IGroupSexDetailService;
import top.kirisamemarisa.onebotspring.service.sexes.IGroupSexUserService;
import top.kirisamemarisa.onebotspring.service.sexes.IGroupSexWifeService;
import top.kirisamemarisa.onebotspring.service.sexes.IGroupWifeService;
import top.kirisamemarisa.onebotspring.utils.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: MarisaDAZE
 * @Description: 蹭蹭
 * @Date: 2024/3/18
 */
@Component
@BotCommand
public class Rub implements MrsCommand {

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
        cmds.add("/蹭蹭");
    }

    @Override
    public boolean trigger(GroupReport groupReport) {
        String cmdString = CommandUtil.concatenateMText(groupReport);
        return CommandUtil.containsCommands(cmdString, cmds);
    }

    @Override
    public void action(GroupReport groupReport) {
        System.out.println("蹭蹭...");
        MassageType messageType = groupReport.getMessageType();
        String url = null;
        String template = null;

        switch (messageType) {
            // 私聊操作
            case PRIVATE -> {
                BotConfig config = botUtil.getFriendConfig(groupReport);
                url = config.getClientUrl() + ClientApi.SEND_MSG.getApiURL();
                Sender sender = groupReport.getSender();
                String s = "私聊不允许蹭蹭操作" + Emoji.DISGUST_8.getEmoji();
                template = MessageTemplate.friendTextTemplateSingle(sender.getUserId(), s);
            }

            // 群聊操作
            case GROUP -> {
                BotConfig config = botUtil.getGroupConfig(groupReport);
                url = config.getClientUrl() + ClientApi.SEND_MSG.getApiURL();
                String groupId = groupReport.getGroupId();
                Sender sender = groupReport.getSender();
                String userQq = sender.getUserId();
                CmdPreParam params = SexObjectUtil.getPreParam(groupReport, cmds);

                // 如果包含错误信息
                if (StrUtil.isNotEmpty(params.getError())) {
                    MessageTemplate.sendGroupMessage(url, groupId, params.getError());
                    return;
                }

                // 限制次数
                if (params.getCount() > 20) {
                    String content = "蹭太多还是会累的（不能超过20次哦）" + Emoji.SORROW_9.getEmoji();
                    MessageTemplate.sendGroupMessage(url, groupId, content);
                    return;
                }

                // 获取群登录用户
                GroupSexUser sexUser = sexUserService.getGroupUserByQQ(groupId, userQq);
                if (ObjectUtils.isEmpty(sexUser)) {
                    System.err.println(userQq + "账号未注册(Rub.class)");
                    String content = "您（在本群）还未注册账号，请先注册后使用" + Emoji.SORROW_9.getEmoji();
                    MessageTemplate.sendGroupMessage(url, groupId, content);
                    return;
                }

                // 获取群老婆列表
                List<GroupSexWife> sexWives = sexWifeService.getGroupWifeByQQ(groupId, userQq, params.getUserId());
                String text;
                switch (sexWives.size()) {
                    // 没有满足条件的群老婆
                    case 0 -> {
                        String content = "没有找到爱称／QQ为“" + params.getUserId() + "”的群老婆，可能为群老婆未绑定或者QQ／爱称输入有误";
                        MessageTemplate.sendGroupMessage(url, groupId, content);
                        return;
                    }
                    // 找到对应的群老婆
                    case 1 -> {
                        GroupSexWife sexWife = sexWives.get(0);
                        String wifeId = sexWife.getWifeId();
                        GroupWife wife = wifeService.getById(wifeId);
                        int count = params.getCount();

                        // 计算值
                        int expendEnergy = Math.round(Math.max(Math.min(count * 0.42f, 8), 1));     // 消耗精力值 每回消耗1~8点
                        int sensitiveVal = Math.round(Math.max(Math.min(count * 0.3f, 5), 1));      // 产生敏感度 每回产生1~5点
                        int comfortVal = Math.round(Math.max(Math.min(count * 1.2f, 5), 1));        // 产生快感度 每回产生1~5点
                        int lewdnessVal = Math.round(Math.max(Math.min(count * 0.5f, 5), 1));       // 产生银乱值 每回产生1~5点
                        // 亲密度：低于65（一般朋友） 则减1+round(count * 0.3)否则加（单次范围：±1~3）
                        int intimate = sexWife.getIntimateLevel();  // 亲密度（初始值是45）
                        int currentIntimate = Math.round(Math.max(Math.min(count * 0.3f, 3), 1));   // 产生亲密度 每回产生±1~3点
                        currentIntimate = intimate > 65 ? currentIntimate : -currentIntimate;
                        intimate += currentIntimate;
                        // 情绪值：亲密度为正 则加 (0.8 * 亲密度) 否则减 （单次范围±1~2）
                        Integer emotion = wife.getEmotion();
                        int currentEmotion = Math.round(currentIntimate * 0.8f);
                        emotion += currentEmotion;

                        // 生成一条涩涩记录
                        GroupSexDetail sexDetail = new GroupSexDetail();
                        sexDetail.setId(SnowflakeUtil.nextId());    // 主键ID
                        sexDetail.setGroupId(groupId);              // 群号
                        sexDetail.setUserId(sexWife.getUserId());   // 用户ID
                        sexDetail.setUserQq(sexWife.getUserQq());   // 用户QQ
                        sexDetail.setWifeId(sexWife.getWifeId());   // 老婆ID
                        sexDetail.setWifeQq(sexWife.getWifeQq());   // 老婆QQ
                        sexDetail.setSexType(SexType.SEX_HARASS);   // 涩涩类型（骚扰）
                        sexDetail.setOperationsCount(count);        // 操作次数
                        sexDetail.setExpendEnergy(expendEnergy);    // 消耗精力
                        sexDetail.setGenerateComfort(comfortVal);   // 快感度
                        sexDetail.setGenerateSensitiveClitoris(sensitiveVal);  // 银地敏感度
                        sexDetail.setGenerateLewdness(lewdnessVal);     // 银乱值
                        sexDetail.setIntimate(currentIntimate);         // 设置本次亲密度
                        sexDetail.setGenerateEmotion(currentEmotion);   // 设置本次情绪值
                        sexDetail.setCreateTime(new Date());            // 创建时间
                        sexDetail.setCreateBy(sexWife.getUserId());     // 创建人

                        // 更新群老婆的相关属性
                        GroupWife updateWife = new GroupWife();
                        updateWife.setId(wife.getId());     // 设置老婆的ID
                        updateWife.setEmotion(emotion);     // 设置老婆的情绪值
                        updateWife.setComfortValue(wife.getComfortValue() + comfortVal);        // 快感度
                        updateWife.setLewdnessLevel(wife.getLewdnessLevel() + lewdnessVal);     // 银乱度
                        updateWife.setSensitiveClitoris(wife.getSensitiveClitoris() + sensitiveVal);  // 银地敏感度
                        updateWife.setRemainingEnergy(wife.getRemainingEnergy() - expendEnergy);// 更新精力
                        updateWife.setUpdateBy(sexWife.getUserId()); // 更新人
                        updateWife.setUpdateTime(new Date());        // 更新时间

                        // 更新关联表
                        GroupSexWife updateSexWife = new GroupSexWife();
                        updateSexWife.setId(sexWife.getId());           // ID
                        updateSexWife.setIntimateLevel(intimate);       // 亲密度
                        updateSexWife.setUpdateTime(new Date());        // 更新时间
                        updateSexWife.setUpdateBy(sexWife.getUserId()); // 更新人

                        // 更新自身精力
                        GroupSexUser updateSexUser = new GroupSexUser();
                        updateSexUser.setId(sexUser.getId());           // ID
                        updateSexUser.setRemainingEnergy(sexUser.getRemainingEnergy() - expendEnergy);  // 自身精力
                        updateSexUser.setUpdateTime(new Date());        // 更新时间
                        updateSexUser.setUpdateBy(sexUser.getId());     // 更新人


                        // 操作数据库进行更新
                        wifeService.updateById(updateWife);         // 更新群老婆
                        sexUserService.updateById(updateSexUser);   // 更新涩涩群友
                        sexWifeService.updateById(updateSexWife);   // 更新老婆亲密度
                        sexDetailService.save(sexDetail);           // 保存涩涩记录

                        // 返回字符串
                        StringBuilder sb = new StringBuilder("蹭蹭成功，");
                        sb.append(sexWife.getLoveName()).append(sexWife.getCallName());
                        sb.append("快感+")
                                .append(comfortVal)
                                .append("（")
                                .append(updateWife.getComfortValue())
                                .append("）；");
                        sb.append("银乱度+")
                                .append(lewdnessVal)
                                .append("（")
                                .append(updateWife.getLewdnessLevel())
                                .append("）；");
                        sb.append("银地敏感度+")
                                .append(sensitiveVal)
                                .append("（")
                                .append(updateWife.getSensitiveClitoris())
                                .append("）；");
                        sb.append("亲密度");
                        if (currentIntimate > 0) sb.append("+");
                        sb.append(currentIntimate)
                                .append("（")
                                .append(updateSexWife.getIntimateLevel())
                                .append("／")
                                .append(Constant.getIntimateDictText(updateSexWife.getIntimateLevel()))
                                .append("）；");
                        sb.append("情绪值");
                        if (currentEmotion > 0) sb.append("+");
                        sb.append(currentEmotion)
                                .append("（")
                                .append(updateWife.getEmotion())
                                .append("／")
                                .append(Constant.getEmotionDictText(updateWife.getEmotion()))
                                .append("）");
                        sb.append("自身精力-").append(expendEnergy)
                                .append("（")
                                .append(updateSexUser.getRemainingEnergy())
                                .append("）；");
                        sb.append(sexWife.getCallName())
                                .append("精力-").append(expendEnergy)
                                .append("（")
                                .append(updateWife.getRemainingEnergy())
                                .append("）");
                        text = sb.toString();
                    }
                    // 条件不够准确导致找到了
                    default -> {
                        String content = SexObjectUtil.moreOneTarget(sexWives);
                        MessageTemplate.sendGroupMessage(url, groupId, content);
                        return;
                    }
                }
                template = MessageTemplate.groupTextTemplateSingle(groupId, text);
            }
        }
        HttpUtils.post(url, template);
    }
}
