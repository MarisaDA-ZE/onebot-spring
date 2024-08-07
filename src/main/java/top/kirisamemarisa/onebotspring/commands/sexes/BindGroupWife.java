package top.kirisamemarisa.onebotspring.commands.sexes;

import cn.hutool.core.util.StrUtil;
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
import top.kirisamemarisa.onebotspring.core.entity.reports.base.MrsReport;
import top.kirisamemarisa.onebotspring.core.entity.reports.common.Sender;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.GroupReport;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.PrivateReport;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.base.MessageReport;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.CQMessage;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.CQAt;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.CQReply;
import top.kirisamemarisa.onebotspring.core.enums.reports.message.MessageType;
import top.kirisamemarisa.onebotspring.core.enums.reports.message.cq.CQMessageType;
import top.kirisamemarisa.onebotspring.core.util.BotUtil;
import top.kirisamemarisa.onebotspring.core.util.CQMessageTemplate;
import top.kirisamemarisa.onebotspring.entity.onebot.sexes.GroupSexUser;
import top.kirisamemarisa.onebotspring.entity.onebot.sexes.GroupSexWife;
import top.kirisamemarisa.onebotspring.entity.onebot.sexes.GroupWife;
import top.kirisamemarisa.onebotspring.entity.onebot.system.BotConfig;
import top.kirisamemarisa.onebotspring.service.onebot.sexes.IGroupSexUserService;
import top.kirisamemarisa.onebotspring.service.onebot.sexes.IGroupSexWifeService;
import top.kirisamemarisa.onebotspring.service.onebot.sexes.IGroupWifeService;
import top.kirisamemarisa.onebotspring.utils.HttpUtils;
import top.kirisamemarisa.onebotspring.utils.SnowflakeUtil;

import java.util.Date;

import static top.kirisamemarisa.onebotspring.common.Constant.MASSAGE_ID_MAP;


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

    @Resource
    private RedisTemplate<String, String> stringRedis;

    @Override
    public boolean trigger(MrsReport report) {
//        if(!(report instanceof GroupReport groupReport)){
//            return false;
//        }
//        MessageType messageType = groupReport.getMessageType();
//        // 是群聊消息
//        int flag = 0;
//        if (messageType == MessageType.GROUP) {
//            Message[] messages = groupReport.getMessage();
//            for (Message message : messages) {
//                // 消息中有回复
//                if (message.getType() == ContentType.REPLY) {
//                    flag++;
//                }
//                // 文本关键字
//                if (message.getType() == ContentType.TEXT) {
//                    MText context = (MText) message.getData();
//                    String text = context.getText().trim();
//                    if ("绑定群老婆".equals(text)) {
//                        flag++;
//                        break;
//                    }
//                }
//            }
//        }
//        return flag == 2;
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
                String content = "在私聊中无法绑定群老婆哦~";
                template = CQMessageTemplate.friendTextTemplateSingle(sender.getUserId(), content);
            }
            case GROUP -> {
                GroupReport groupReport = (GroupReport) messageReport;
                BotConfig config = botUtil.getGroupConfig(groupReport);
                url = config.getClientUrl() + ClientApi.SEND_MSG.getApiURL();
                String groupId = groupReport.getGroupId();
                Sender sender = groupReport.getSender();
                CQMessage[] messages = groupReport.getMessages();
                String target;
                String replyId = "";
                for (CQMessage message : messages) {
                    if (message.getType() == CQMessageType.REPLY) {
                        CQReply reply = (CQReply) message.getData();
                        replyId = String.valueOf(reply.getId());
                        break;
                    }
                }
                String sk = groupId + MASSAGE_ID_MAP + ":" + replyId;
                target = stringRedis.opsForValue().get(sk);

                if (StrUtil.isBlank(target)) {
                    for (CQMessage message : messages) {
                        if (message.getType() == CQMessageType.AT) {
                            CQAt at = (CQAt) message.getData();
                            target = at.getQq();
                            break;
                        }
                    }
                }
                if (StrUtil.isBlank(target)) {
                    String s = "回复的消息过期了哟，请回复最近30分钟内发出的消息。（如果你要我做你的老婆，请不要删除回复时自动添加的@）";
                    template = CQMessageTemplate.groupTextTemplateSingle(groupId, s);
                    HttpUtils.post(url, template);
                    return;
                }
                if (target.equals(sender.getUserId())) {
                    String s = "您不能自己做自己的老婆哟！";
                    template = CQMessageTemplate.groupTextTemplateSingle(groupId, s);
                    HttpUtils.post(url, template);
                    return;
                }

                System.out.println("绑定群老婆: " + target);
                String u = config.getClientUrl() + ClientApi.GET_GROUP_MEMBER_INFO.getApiURL();
                String s = CQMessageTemplate.getGroupMemberInfo(groupId, target);
                // 获取群成员信息
                // String res = HttpUtils.post(u, s);
                // GroupMemberInfo member = JSONObject.parseObject(res, GroupMemberInfo.class);
                Object member = null;
                // 目标信息存在
                if (member != null) {
                    // GroupMemberDetail data = member.getData();
                    String nickName = "data.getNickName()";
                    String memberId = "String.valueOf(data.getUserId())";

                    QueryWrapper<GroupSexUser> sexUserWrapper = new QueryWrapper<>();
                    sexUserWrapper.eq("group_id", groupId);
                    sexUserWrapper.eq("user_qq", sender.getUserId());
                    GroupSexUser groupSexUser = groupSexUserService.getOne(sexUserWrapper);
                    GroupSexUser groupSexUserDb = groupSexUser;
                    System.out.println("涩涩用户：" + groupSexUser);
                    // 数据库中不存在，则新建涩涩用户
                    if (ObjectUtils.isEmpty(groupSexUser)) {
                        groupSexUser = new GroupSexUser();
                        groupSexUser.setId(SnowflakeUtil.nextId());
                        groupSexUser.setUserQq(sender.getUserId());
                        groupSexUser.setGroupId(groupId);
                        groupSexUser.setCreateBy("system");
                        groupSexUser.setCreateTime(new Date());
                    }

                    QueryWrapper<GroupWife> wifeWrapper = new QueryWrapper<>();
                    wifeWrapper.eq("GROUP_ID", groupId);
                    wifeWrapper.eq("SELF_ID", memberId);
                    GroupWife groupWife = groupWifeService.getOne(wifeWrapper);
                    GroupWife groupWifeDb = groupWife;
                    // 群老婆不存在，则构建群老婆对象
                    if (ObjectUtils.isEmpty(groupWife)) {
                        groupWife = new GroupWife();
                        groupWife.setId(SnowflakeUtil.nextId());
                        groupWife.setGroupId(groupId);
                        groupWife.setSelfId(memberId);
                        groupWife.setCreateBy(groupSexUser.getId());
                        groupWife.setCreateTime(new Date());
                    } else {
                        template = CQMessageTemplate.groupTextTemplateSingle(groupId,
                                nickName + "已是您的老婆。");
                        HttpUtils.post(url, template);
                        return;
                    }

                    QueryWrapper<GroupSexWife> sexWifeWrapper = new QueryWrapper<>();
                    sexWifeWrapper.eq("group_id", groupId);
                    sexWifeWrapper.eq("user_qq", sender.getUserId());
                    sexWifeWrapper.eq("wife_qq", memberId);
                    GroupSexWife groupSexWife = groupSexWifeService.getOne(sexWifeWrapper);
                    GroupSexWife groupSexWifeDb = groupSexWife;
                    // 创建关联不存在则新建关联关系
                    if (ObjectUtils.isEmpty(groupSexWife)) {
                        groupSexWife = new GroupSexWife();
                        groupSexWife.setId(SnowflakeUtil.nextId());
                        groupSexWife.setGroupId(groupId);
                        groupSexWife.setUserId(groupSexUser.getId());
                        groupSexWife.setUserQq(groupSexUser.getUserQq());
                        groupSexWife.setWifeId(groupWife.getId());
                        groupSexWife.setWifeQq(target);
                        groupSexWife.setLoveName(nickName);
                        groupSexWife.setNickName(nickName);
                        groupSexWife.setCallName("老婆");
                        groupSexWife.setCreateBy(groupSexUser.getId());
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
                template = CQMessageTemplate.groupTextTemplateSingle(groupId, "绑定成功！");
            }
        }
        HttpUtils.post(url, template);
    }
}
