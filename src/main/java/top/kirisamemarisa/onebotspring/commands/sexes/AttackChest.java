package top.kirisamemarisa.onebotspring.commands.sexes;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import top.kirisamemarisa.onebotspring.common.Constant;
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
import top.kirisamemarisa.onebotspring.core.enums.ContentType;
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
import top.kirisamemarisa.onebotspring.utils.HttpUtils;
import top.kirisamemarisa.onebotspring.utils.MassageTemplate;
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
    private IGroupWifeService groupWifeService;

    @Resource
    private IGroupSexUserService groupSexUserService;

    @Resource
    private IGroupSexWifeService groupSexWifeService;

    @Resource
    private IGroupSexDetailService groupSexDetailService;

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
        System.out.println("涩涩-揉胸...");
        MassageType messageType = groupReport.getMessageType();
        String url = null;
        String template = null;
        switch (messageType) {
            case PRIVATE -> {
                BotConfig config = botUtil.getFriendConfig(groupReport);
                url = config.getClientUrl() + ClientApi.SEND_MSG.getApiURL();
                Sender sender = groupReport.getSender();
                String s = "暂不支持私聊中进行揉胸操作！";
                template = MassageTemplate.friendTextTemplateSingle(sender.getUserId(), s);
            }
            case GROUP -> {
                BotConfig config = botUtil.getGroupConfig(groupReport);
                url = config.getClientUrl() + ClientApi.SEND_MSG.getApiURL();
                String groupId = groupReport.getGroupId();
                Sender sender = groupReport.getSender();

                QueryWrapper<GroupSexUser> sexUserWrapper = new QueryWrapper<>();
                sexUserWrapper.eq("GROUP_ID", groupId);
                sexUserWrapper.eq("USER_QQ", sender.getUserId());
                GroupSexUser sexUser = groupSexUserService.getOne(sexUserWrapper);
                if (ObjectUtils.isEmpty(sexUser)) {
                    String s = "您还未注册账号，请绑定群老婆后再试~";
                    template = MassageTemplate.groupTextTemplateSingle(groupId, s);
                    HttpUtils.post(url, template);
                }
                String target = ""; // 目标账号的qq号或在本群的昵称
                int count;
                Message[] messages = groupReport.getMessage();
                for (Message message : messages) {
                    // 有at则目标直接为at的对象
                    if (message.getType() == ContentType.AT) {
                        MAt at = (MAt) message.getData();
                        target = at.getMention();
                        break;
                    }
                }
                StringBuilder tsb = new StringBuilder();
                Arrays.stream(messages)
                        .filter(msg -> msg.getType() == ContentType.TEXT)
                        .forEach(msg -> {
                            MText mText = (MText) msg.getData();
                            tsb.append(mText.getText());
                        });
                String messageText = tsb.toString();
                // 获取字符串中的参数 {用户名|null，1或者n次，n>= 0}
                String[] textParams = getCommandParams(messageText);
                if (ObjectUtils.isEmpty(textParams)) {
                    String s = "指令错误，肯定是你输错了！٩(๑•̀ω•́๑)۶";
                    template = MassageTemplate.groupTextTemplateSingle(groupId, s);
                    HttpUtils.post(url, template);
                    return;
                }

                // target没有值，则0位应是QQ号或昵称，第1位应该是操作次数
                if (StrUtil.isBlank(target)) {
                    if (StrUtil.isNotBlank(textParams[0])) {
                        target = textParams[0];
                    } else {
                        String s = "指令错误，找不到目标(´•ω•̥`)";
                        template = MassageTemplate.groupTextTemplateSingle(groupId, s);
                        HttpUtils.post(url, template);
                        return;
                    }
                }
                if (sender.getUserId().equals(target)) {
                    String s = "不要揉自己的胸啊魂淡！•᷄ࡇ•᷅";
                    template = MassageTemplate.groupTextTemplateSingle(groupId, s);
                    HttpUtils.post(url, template);
                    return;
                }

                // 取出次数（没有次数就默认1次）
                count = StrUtil.isNotBlank(textParams[1]) ? Integer.parseInt(textParams[1]) : 1;
                if (count < 1) {
                    String s = "次数太少啦！" + count + "次你叫我怎么揉•᷄ࡇ•᷅";
                    template = MassageTemplate.groupTextTemplateSingle(groupId, s);
                    HttpUtils.post(url, template);
                    return;
                } else if (count > 50) {
                    String s = "揉太多次啦（每回最多揉50次哦）QwQ";
                    template = MassageTemplate.groupTextTemplateSingle(groupId, s);
                    HttpUtils.post(url, template);
                    return;
                }
                System.out.println("目标: " + target + ", 次数: " + count);
                // 查找对应的群老婆
                QueryWrapper<GroupSexWife> sexWifeWrapper = new QueryWrapper<>();
                sexWifeWrapper.eq("USER_QQ", sender.getUserId());
                sexWifeWrapper.eq("GROUP_ID", groupId);
                sexWifeWrapper.eq("WIFE_QQ", target)
                        .or()
                        .eq("LOVE_NAME", target);
                GroupSexWife sexWife = groupSexWifeService.getOne(sexWifeWrapper);

                String s;
                if (ObjectUtils.isEmpty(sexWife)) {
                    s = "不太对哦，没有找到QQ号或昵称为“" + target + "”的群老婆，有可能她还不是你老婆哦(´•ω•̥`)";
                } else {
                    int expendEnergy = 1;   // 消耗精力
                    int sensitiveVal = Math.max(Math.round(0.3f * count), 1);   // 产生敏感度 最每回最少产生1点
                    sensitiveVal = Math.min(sensitiveVal, 2);      // 敏感度最每回最多加2
                    int comfortVal = Math.max(Math.round(1.2f * count), 1);     // 产生快感度 最每回最少产生1点
                    comfortVal = Math.min(comfortVal, 5);          // 舒服值最每回最多加5
                    int lewdnessVal = Math.max(Math.round(0.5f * count), 1);    // 产生银乱值 最每回最少产生1点
                    lewdnessVal = Math.min(lewdnessVal, 3);         // 银乱值每回合最多加3点
                    // 获取群老婆对象（多群员共用一个）
                    GroupWife groupWifeDb = groupWifeService.getById(sexWife.getWifeId());

                    GroupWife groupWife = new GroupWife();
                    BeanUtils.copyProperties(groupWifeDb, groupWife);   // 克隆数据 以免算错
                    groupWife.setComfortValue(groupWifeDb.getComfortValue() + comfortVal);      // 快感度
                    groupWife.setLewdnessLevel(groupWifeDb.getLewdnessLevel() + lewdnessVal);   // 银乱度
                    groupWife.setSensitiveUdder(groupWifeDb.getSensitiveUdder() + sensitiveVal);// 欧派敏感度
                    int remainEnergy = groupWifeDb.getRemainingEnergy();        // 获取做之前的精力
                    groupWife.setRemainingEnergy(remainEnergy - expendEnergy);  // 扣除精力
                    groupWife.setUpdateBy(sexWife.getUserId()); // 更新人
                    groupWife.setUpdateTime(new Date());        // 更新时间


                    // 保存涩涩详情
                    GroupSexDetail sexDetail = new GroupSexDetail();
                    sexDetail.setId(SnowflakeUtil.nextId());    // 主键ID
                    sexDetail.setGroupId(groupId);              // 群号
                    sexDetail.setUserId(sexWife.getUserId());   // 用户ID
                    sexDetail.setUserQq(sexWife.getUserQq());   // 用户QQ
                    sexDetail.setWifeId(sexWife.getWifeId());   // 老婆ID
                    sexDetail.setWifeQq(sexWife.getWifeQq());   // 老婆QQ
                    sexDetail.setSexType(SexType.SEX_HARASS);       // 涩涩类型（骚扰）
                    sexDetail.setOperationsCount(count);        // 进行次数
                    sexDetail.setExpendEnergy(expendEnergy);    // 消耗精力
                    sexDetail.setGenerateComfort(comfortVal);   // 快感度
                    sexDetail.setGenerateSensitiveUdder(sensitiveVal);  // 欧派敏感度
                    sexDetail.setGenerateLewdness(lewdnessVal); // 银乱值

                    // 亲密度：低于65（一般朋友） 则减1+round(count * 0.2)否则加（单次范围：±1~3）
                    Integer intimate = sexWife.getIntimateLevel();  // 亲密度（初始值是45）
                    int currentIntimate = Math.max(Math.round(count * 0.2f), 1);
                    currentIntimate = Math.min(currentIntimate, 3);
                    currentIntimate = intimate > 60 ? currentIntimate : -currentIntimate;
                    intimate += currentIntimate;
                    sexDetail.setIntimate(currentIntimate);    // 设置本次亲密度

                    // 情绪值：亲密度为正 则加 (0.75 * 亲密度) 否则减 （单次范围±1~2）
                    Integer emotion = groupWife.getEmotion();
                    int currentEmotion = Math.round(currentIntimate * 0.75f);
                    emotion += currentEmotion;
                    groupWife.setEmotion(emotion);// 设置老婆的情绪值
                    sexDetail.setGenerateEmotion(currentEmotion);// 设置情绪值
                    sexDetail.setCreateTime(new Date());        // 创建时间
                    sexDetail.setCreateBy(sexWife.getUserId()); // 创建时间

                    // 更新关联表中的情绪值
                    GroupSexWife updateSexWife = new GroupSexWife();
                    updateSexWife.setId(sexWife.getId());
                    updateSexWife.setIntimateLevel(intimate);

                    // 更新自身精力
                    GroupSexUser updateSexUser = new GroupSexUser();
                    updateSexUser.setId(sexUser.getId());
                    updateSexUser.setRemainingEnergy(sexUser.getRemainingEnergy() - expendEnergy);

                    // 更新数据库
                    groupWifeService.updateById(groupWife);
                    groupSexUserService.updateById(updateSexUser);
                    groupSexWifeService.updateById(updateSexWife);
                    groupSexDetailService.save(sexDetail);

                    // 组装反馈信息
                    String intimateDictText = Constant.getIntimateDictText(intimate);
                    String emotionDictText = Constant.getEmotionDictText(emotion);

                    StringBuilder sb = new StringBuilder("揉胸成功，");
                    sb.append(sexWife.getLoveName()).append(sexWife.getCallName());
                    sb.append("快感增加").append(comfortVal).append("；");
                    sb.append("好感度");
                    if (currentIntimate > 0) sb.append("+");
                    sb.append(currentIntimate).append("（").append(intimateDictText).append(" ").append(intimate).append("）；");
                    sb.append("情绪");
                    if (currentEmotion > 0) sb.append("+");
                    sb.append(currentEmotion).append("（").append(emotionDictText).append(" ").append(emotion).append("）");
                    s = sb.toString();
                }
                template = MassageTemplate.groupTextTemplateSingle(groupId, s);
            }
        }
        // System.out.println("模板: " + template);
        HttpUtils.post(url, template);
    }

    private boolean containsText(List<String> list, String key) {
        List<String> filter = list.stream().filter(key::contains).toList();
        return filter.size() > 0;
    }


    /**
     * 从字符串中获取到需要的参数
     *
     * @param command 指令字符串
     * @return 结果
     */
    public String[] getCommandParams(String command) {
        command = command.replaceAll("\\s+", " ");
        String[] arr = command.split(" ");
        if (ObjectUtils.isEmpty(arr)) return null;
        List<String> cmdList = Arrays.stream(arr)
                .filter(cmd -> !cmds.contains(cmd.trim()))
                .toList();
        List<String> uniqueList = new ArrayList<>(new LinkedHashSet<>(cmdList));
        switch (uniqueList.size()) {
            case 0 -> {
                return new String[]{null, "1"};
            }
            case 1 -> {
                List<String> list = uniqueList.stream().toList();
                return new String[]{list.get(0).trim(), "1"};
            }
            case 2 -> {
                List<String> list = uniqueList.stream().toList();
                Integer count;
                try {
                    String s = (list.get(1).trim()).replaceAll("[^0-9]", "");
                    count = Integer.parseInt(s);
                } catch (Exception ignored) {
                    count = 1;
                }
                return new String[]{list.get(0).trim(), String.valueOf(count)};
            }
            default -> {
                return null;
            }
        }
    }
}
