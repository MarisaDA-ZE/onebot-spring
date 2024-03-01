package top.kirisamemarisa.onebotspring.commands.sexes;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import top.kirisamemarisa.onebotspring.common.Constant;
import top.kirisamemarisa.onebotspring.enums.CommandType;
import top.kirisamemarisa.onebotspring.enums.SexType;
import top.kirisamemarisa.onebotspring.core.annotation.BotCommand;
import top.kirisamemarisa.onebotspring.core.api.ClientApi;
import top.kirisamemarisa.onebotspring.core.command.MrsCommand;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.GroupReport;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.Sender;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.Message;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.MAt;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.MText;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.base.MData;
import top.kirisamemarisa.onebotspring.core.enums.MassageType;
import top.kirisamemarisa.onebotspring.core.util.BotUtil;
import top.kirisamemarisa.onebotspring.entity.sexes.GroupSexDetail;
import top.kirisamemarisa.onebotspring.entity.sexes.GroupSexUser;
import top.kirisamemarisa.onebotspring.entity.sexes.GroupSexWife;
import top.kirisamemarisa.onebotspring.entity.sexes.GroupWife;
import top.kirisamemarisa.onebotspring.entity.system.BotConfig;
import top.kirisamemarisa.onebotspring.service.sexes.IGroupSexDetailService;
import top.kirisamemarisa.onebotspring.service.sexes.IGroupSexUserService;
import top.kirisamemarisa.onebotspring.service.sexes.IGroupSexWifeService;
import top.kirisamemarisa.onebotspring.service.sexes.IGroupWifeService;
import top.kirisamemarisa.onebotspring.utils.CommandUtil;
import top.kirisamemarisa.onebotspring.utils.HttpUtils;
import top.kirisamemarisa.onebotspring.utils.MessageTemplate;
import top.kirisamemarisa.onebotspring.utils.SnowflakeUtil;

import java.util.*;

/**
 * @Author: MarisaDAZE
 * @Description: 揉胸指令
 * @Date: 2024/2/22
 */
@Component
@BotCommand
public class AttackChest implements MrsCommand {

    @Resource
    private BotUtil botUtil;

    @Resource
    private IGroupWifeService wifeService;

    @Resource
    private IGroupSexUserService sexUserService;

    @Resource
    private IGroupSexWifeService sexWifeService;

    @Resource
    private IGroupSexDetailService sexDetailService;

    // 触发命令（可能有多条命令且只需要指令中带有任意一段命令字符时好用）
    private final List<String> cmds = new ArrayList<>();

    {
        cmds.add("/揉熊");
        cmds.add("/揉胸");
    }

    @Override
    public boolean trigger(GroupReport groupReport) {
        MassageType messageType = groupReport.getMessageType();
        Message[] messages = groupReport.getMessage();
        // 只监控群聊
        if (messageType != MassageType.GROUP) return false;

        // 触发
        for (Message message : messages) {
            MData data = message.getData();
            if (data instanceof MText mText) {
                String context = StrUtil.trim(mText.getText());
                if (containsText(cmds, context)) return true;
            }
        }
        return false;
    }

    @Transactional
    @Override
    public void action(GroupReport groupReport) {
        System.out.println("揉胸...");
        MassageType messageType = groupReport.getMessageType();
        String url = null;
        String template = null;
        switch (messageType) {
            case PRIVATE -> {
                BotConfig config = botUtil.getFriendConfig(groupReport);
                url = config.getClientUrl() + ClientApi.SEND_MSG.getApiURL();
                Sender sender = groupReport.getSender();
                String s = "私聊不能揉胸(´•ω•̥`)";
                template = MessageTemplate.friendTextTemplateSingle(sender.getUserId(), s);
            }
            case GROUP -> {
                BotConfig config = botUtil.getGroupConfig(groupReport);
                url = config.getClientUrl() + ClientApi.SEND_MSG.getApiURL();
                Sender sender = groupReport.getSender();
                String userQq = sender.getUserId();
                String groupId = groupReport.getGroupId();
                List<MAt> ats = CommandUtil.getAts(groupReport);
                String target;
                int count;
                switch (ats.size()) {
                    // /指令 <target> [count次]
                    case 0 -> {
                        String command = CommandUtil.concatenateMText(groupReport);
                        String trimmed = CommandUtil.trimCommand(command, cmds);
                        if (trimmed == null) {
                            System.err.println("attack chest: 0-1");
                            String content = "指令格式有误哦，详情请查看涩涩帮助(´•ω•̥`)";
                            MessageTemplate.sendGroupMessage(url, groupId, content);
                            return;
                        }
                        String[] array = CommandUtil.getTargetAndCount(trimmed, CommandType.TYPE_2);
                        if (array == null) {
                            System.err.println("attack chest: 0-2");
                            String content = "指令格式有误哦，详情请查看涩涩帮助(´•ω•̥`)";
                            MessageTemplate.sendGroupMessage(url, groupId, content);
                            return;
                        }
                        target = array[0];
                        count = Integer.parseInt(array[1]);
                    }
                    // @<target> /指令 [count次]
                    case 1 -> {
                        target = ats.get(0).getMention();
                        String command = CommandUtil.concatenateMText(groupReport);
                        String trimmed = CommandUtil.trimCommand(command, cmds);
                        if (trimmed == null) {
                            System.err.println("attack chest: 1-1");
                            String content = "指令格式有误哦，详情请查看涩涩帮助(´•ω•̥`)";
                            MessageTemplate.sendGroupMessage(url, groupId, content);
                            return;
                        }
                        String[] array = CommandUtil.getTargetAndCount(trimmed, CommandType.TYPE_1);
                        if (array == null) {
                            System.err.println("attack chest: 1-2");
                            String content = "指令格式有误哦，详情请查看涩涩帮助(´•ω•̥`)";
                            MessageTemplate.sendGroupMessage(url, groupId, content);
                            return;
                        }
                        count = Integer.parseInt(array[1]);
                    }
                    // 其它情况
                    default -> {
                        System.err.println("attack chest: 2-1");
                        String content = "指令格式有误哦，详情请查看涩涩帮助(´•ω•̥`)";
                        MessageTemplate.sendGroupMessage(url, groupId, content);
                        return;
                    }
                }

                // target 和 count 处理完成
                System.out.println(target + ", " + count);
                if (count > 50) {
                    String content = "揉太多次啦（每回最多揉50次）(´•ω•̥`)";
                    MessageTemplate.sendGroupMessage(url, groupId, content);
                    return;
                }

                QueryWrapper<GroupSexUser> sexUserWrapper = new QueryWrapper<>();
                sexUserWrapper.eq("USER_QQ", userQq);
                sexUserWrapper.eq("GROUP_ID", groupId);
                GroupSexUser sexUser = sexUserService.getOne(sexUserWrapper);
                if (ObjectUtils.isEmpty(sexUser)) {
                    System.err.println(userQq + "账号未注册(attackChest)");
                    String content = "您（在本群）还未注册账号，请先注册后使用(´•ω•̥`)";
                    MessageTemplate.sendGroupMessage(url, groupId, content);
                    return;
                }
                QueryWrapper<GroupSexWife> sexWifeWrapper = new QueryWrapper<>();
                sexWifeWrapper.eq("USER_QQ", userQq);
                sexWifeWrapper.eq("GROUP_ID", groupId);
                sexWifeWrapper.eq("WIFE_QQ", target)
                        .or()
                        .like("LOVE_NAME", target);
                List<GroupSexWife> sexWives = sexWifeService.list(sexWifeWrapper);
                String text;   // 机器人反馈的文本
                switch (sexWives.size()) {
                    // 没有找到群老婆
                    case 0 -> {
                        String content = "没有找到爱称／QQ为“" + target + "”的群老婆，是不是群老婆未绑定或者QQ／爱称输入有误？";
                        MessageTemplate.sendGroupMessage(url, groupId, content);
                        return;
                    }
                    // 找到一个群老婆
                    case 1 -> {
                        GroupSexWife sexWife = sexWives.get(0);
                        String wifeId = sexWife.getWifeId();
                        GroupWife wife = wifeService.getById(wifeId);

                        // 计算值
                        int expendEnergy = Math.round(Math.max(Math.min(count * 0.42f, 8), 1));     // 消耗精力值 每回消耗1~8点
                        int sensitiveVal = Math.round(Math.max(Math.min(count * 0.3f, 3), 1));      // 产生敏感度 每回产生1~3点
                        int comfortVal = Math.round(Math.max(Math.min(count * 1.2f, 5), 1));        // 产生快感度 每回产生1~5点
                        int lewdnessVal = Math.round(Math.max(Math.min(count * 0.5f, 3), 1));       // 产生银乱值 每回产生1~3点
                        // 亲密度：低于65（一般朋友） 则减1+round(count * 0.2)否则加（单次范围：±1~3）
                        int intimate = sexWife.getIntimateLevel();  // 亲密度（初始值是45）
                        int currentIntimate = Math.round(Math.max(Math.min(count * 0.2f, 3), 1));   // 产生亲密度
                        currentIntimate = intimate > 60 ? currentIntimate : -currentIntimate;
                        intimate += currentIntimate;
                        // 情绪值：亲密度为正 则加 (0.75 * 亲密度) 否则减 （单次范围±1~2）
                        Integer emotion = wife.getEmotion();
                        int currentEmotion = Math.round(currentIntimate * 0.75f);
                        emotion += currentEmotion;

                        // 保存涩涩详情
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
                        sexDetail.setGenerateSensitiveUdder(sensitiveVal);  // 欧派敏感度
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
                        updateWife.setSensitiveUdder(wife.getSensitiveUdder() + sensitiveVal);  // 欧派敏感度
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
                        StringBuilder sb = new StringBuilder("揉胸成功，");
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
                        sb.append("欧派敏感度+")
                                .append(sensitiveVal)
                                .append("（")
                                .append(updateWife.getSensitiveUdder())
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
                    // 找到多个群老婆
                    default -> {
                        StringBuilder sb = new StringBuilder();
                        sexWives.forEach(wife -> sb.append(wife.getLoveName()).append("、"));
                        if (sb.length() > 1) sb.delete(sb.length() - 1, sb.length());
                        String content = "搜索似乎有点模糊，共找到" + sb + "（" + sexWives.size() + "个）群老婆，请确定老婆后重新执行。";
                        MessageTemplate.sendGroupMessage(url, groupId, content);
                        return;
                    }
                }
                template = MessageTemplate.groupTextTemplateSingle(groupId, text);
            }
        }
        HttpUtils.post(url, template);
    }

    private boolean containsText(List<String> list, String key) {
        List<String> filter = list.stream().filter(key::contains).toList();
        return filter.size() > 0;
    }

}
