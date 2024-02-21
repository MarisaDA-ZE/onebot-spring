package top.kirisamemarisa.onebotspring.commands;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import top.kirisamemarisa.onebotspring.core.annotation.BotCommand;
import top.kirisamemarisa.onebotspring.core.api.ClientApi;
import top.kirisamemarisa.onebotspring.core.command.MrsCommand;
import top.kirisamemarisa.onebotspring.core.entity.groupmemberinfo.GroupMemberDetail;
import top.kirisamemarisa.onebotspring.core.entity.groupmemberinfo.GroupMemberInfo;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.GroupReport;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.Sender;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.Massage;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.MAt;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.MText;
import top.kirisamemarisa.onebotspring.core.enums.ContentType;
import top.kirisamemarisa.onebotspring.core.enums.MassageType;
import top.kirisamemarisa.onebotspring.core.util.BotUtil;
import top.kirisamemarisa.onebotspring.entity.sexes.GroupSexUser;
import top.kirisamemarisa.onebotspring.entity.sexes.GroupSexWife;
import top.kirisamemarisa.onebotspring.entity.sexes.GroupWife;
import top.kirisamemarisa.onebotspring.entity.system.BotConfig;
import top.kirisamemarisa.onebotspring.service.sexes.IGroupSexUserService;
import top.kirisamemarisa.onebotspring.service.sexes.IGroupSexWifeService;
import top.kirisamemarisa.onebotspring.service.sexes.IGroupWifeService;
import top.kirisamemarisa.onebotspring.utils.HttpUtils;
import top.kirisamemarisa.onebotspring.utils.MassageTemplate;
import top.kirisamemarisa.onebotspring.utils.SnowflakeUtil;

import java.util.Date;


/**
 * @Author: MarisaDAZE
 * @Description: 绑定群老婆
 * @Date: 2024/2/21
 */
@Component
@BotCommand
public class BindGroupWife implements MrsCommand {

    @Resource
    private BotUtil botUtil;

    @Resource
    private IGroupWifeService groupWifeService;

    @Resource
    private IGroupSexUserService groupSexUserService;

    @Resource
    private IGroupSexWifeService groupSexWifeService;

    @Override
    public boolean trigger(GroupReport groupReport) {
        MassageType messageType = groupReport.getMessageType();
        // 是群聊消息
        int flag = 0;
        if (messageType == MassageType.GROUP) {
            Massage[] messages = groupReport.getMessage();
            for (Massage message : messages) {
                // 消息中有回复
                if (message.getType() == ContentType.REPLY) {
                    flag++;
                }
                // 文本关键字
                if (message.getType() == ContentType.TEXT) {
                    MText context = (MText) message.getData();
                    String text = context.getText().trim();
                    if ("绑定群老婆".equals(text)) {
                        flag++;
                        break;
                    }
                }
            }
        }
        return flag == 2;
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
                String content = "在私聊中无法绑定群老婆哦~";
                template = MassageTemplate.friendTextTemplateSingle(sender.getUserId(), content);
            }
            case GROUP -> {
                BotConfig config = botUtil.getFriendConfig(groupReport);
                url = config.getClientUrl() + ClientApi.SEND_MSG.getApiURL();
                Massage[] messages = groupReport.getMessage();
                String target = "";
                for (Massage message : messages) {
                    if (message.getType() == ContentType.AT) {
                        MAt at = (MAt) message.getData();
                        target = at.getMention();
                        break;
                    }
                }
                System.out.println("绑定群老婆: " + target);
                String u = config.getClientUrl() + ClientApi.GET_GROUP_MEMBER_INFO.getApiURL();
                String s = MassageTemplate.getGroupMemberInfo(groupReport.getGroupId(), target);
                // 获取群成员信息
                String res = HttpUtils.post(u, s);
                GroupMemberInfo member = JSONObject.parseObject(res, GroupMemberInfo.class);
                // 目标信息存在
                if (member != null) {

                    GroupMemberDetail data = member.getData();
                    String nickName = data.getNickName();
                    Sender sender = groupReport.getSender();

                    QueryWrapper<GroupSexUser> sexUserWrapper = new QueryWrapper<>();
                    sexUserWrapper.eq("user_qq", target);
                    GroupSexUser groupSexUser = groupSexUserService.getOne(sexUserWrapper);
                    GroupSexUser groupSexUserDb = groupSexUser;
                    // 数据库中不存在，则新建涩涩用户
                    if (ObjectUtils.isEmpty(groupSexUser)) {
                        groupSexUser = new GroupSexUser();
                        groupSexUser.setId(SnowflakeUtil.nextId());
                        groupSexUser.setUserQq(sender.getUserId());
                        groupSexUser.setCreateBy("system");
                        groupSexUser.setCreateTime(new Date());
                    }

                    String groupId = String.valueOf(data.getGroupId());
                    String memberId = String.valueOf(data.getUserId());
                    QueryWrapper<GroupWife> wifeWrapper = new QueryWrapper<>();
                    wifeWrapper.eq("group_id", groupId);
                    wifeWrapper.eq("user_qq", sender.getUserId());
                    wifeWrapper.eq("wife_qq", memberId);
                    GroupWife groupWife = groupWifeService.getOne(wifeWrapper);
                    GroupWife groupWifeDb = groupWife;
                    // 群老婆不存在，则构建群老婆对象
                    if (ObjectUtils.isEmpty(groupWife)) {
                        groupWife = new GroupWife();
                        groupWife.setId(SnowflakeUtil.nextId());
                        groupWife.setGroupId(groupId);
                        groupWife.setUserId(groupSexUser.getId());
                        groupWife.setUserQq(groupSexUser.getUserQq());
                        groupWife.setWifeQq(target);
                        groupWife.setCreateBy("system");
                        groupWife.setCreateTime(new Date());
                    } else {
                        template = MassageTemplate.groupTextTemplateSingle(groupReport.getGroupId(),
                                nickName + "已是您的老婆。");
                        HttpUtils.post(url, template);
                        return;
                    }

                    QueryWrapper<GroupSexWife> sexWifeWrapper = new QueryWrapper<>();
                    sexWifeWrapper.eq("user_qq", sender.getUserId());
                    sexWifeWrapper.eq("wife_qq", memberId);
                    GroupSexWife groupSexWife = groupSexWifeService.getOne(sexWifeWrapper);
                    GroupSexWife groupSexWifeDb = groupSexWife;
                    // 创建关联不存在则新建关联关系
                    if (ObjectUtils.isEmpty(groupSexWife)) {
                        groupSexWife = new GroupSexWife();
                        groupSexWife.setId(SnowflakeUtil.nextId());
                        groupSexWife.setUserId(groupSexUser.getId());
                        groupSexWife.setUserQq(groupSexUser.getUserQq());
                        groupSexWife.setWifeId(groupWife.getId());
                        groupSexWife.setWifeQq(groupWife.getWifeQq());
                        groupSexWife.setNickName(nickName);
                        groupSexWife.setCallName("老婆");
                        groupSexWife.setCreateBy("system");
                        groupSexWife.setCreateTime(new Date());
                    }

                    // 保存到数据库
                    if (ObjectUtils.isEmpty(groupSexUserDb)) {
                        groupSexUserService.save(groupSexUser);
                    }
                    if (ObjectUtils.isEmpty(groupWifeDb)) {
                        groupWifeService.save(groupWife);
                    }
                    if (ObjectUtils.isEmpty(groupSexWifeDb)) {
                        groupSexWifeService.save(groupSexWife);
                    }
                }
                template = MassageTemplate.groupTextTemplateSingle(groupReport.getGroupId(), "绑定成功！");
            }
        }
        HttpUtils.post(url, template);
    }
}
