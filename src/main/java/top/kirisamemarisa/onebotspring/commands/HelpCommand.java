package top.kirisamemarisa.onebotspring.commands;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.kirisamemarisa.onebotspring.core.annotation.BotCommand;
import top.kirisamemarisa.onebotspring.core.api.ClientApi;
import top.kirisamemarisa.onebotspring.core.command.MrsCommand;
import top.kirisamemarisa.onebotspring.core.entity.reports.base.MrsReport;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.GroupReport;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.CQMessage;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.CQText;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.base.CQData;
import top.kirisamemarisa.onebotspring.core.enums.reports.message.MessageType;
import top.kirisamemarisa.onebotspring.utils.CommandUtil;
import top.kirisamemarisa.onebotspring.utils.HttpUtils;
import top.kirisamemarisa.onebotspring.core.util.CQMessageTemplate;

import java.util.List;


/**
 * @Author: MarisaDAZE
 * @Description: 查看帮助菜单
 * @Date: 2024/2/16
 */
@Component
@BotCommand
public class HelpCommand implements MrsCommand {

    @Value("${mrs-bot.default-client-url}")
    private String clientPath;

    @Override
    public boolean trigger(MrsReport mrsReport) {
        if (!(mrsReport instanceof GroupReport report)) {
            return false;
        }
        MessageType messageType = report.getMessageType();
        // 群聊情况下需要at机器人
        if (messageType == MessageType.GROUP) {
            boolean isAt = CommandUtil.hasAtSelf(report);
            if (!isAt) return false;
        }

        CQMessage[] messages = report.getMessages();
        // 触发
        for (CQMessage message : messages) {
            CQData data = message.getData();
            if (data instanceof CQText cqText) {
                String ctx = StrUtil.trim(cqText.getText()) + "";
                if (ctx.startsWith("/帮助") || ctx.startsWith("/help")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void action(MrsReport report) {
        System.out.println("查看帮助...");
        if (!(report instanceof GroupReport groupReport)) {
            System.err.println("类型错误...");
            return;
        }
        MessageType messageType = groupReport.getMessageType();
        switch (messageType) {
            case PRIVATE -> friendHandler(groupReport);
            case GROUP -> groupHandler(groupReport);
        }
    }

    /**
     * 私聊情况
     *
     * @param groupReport 私聊上报
     */
    private void friendHandler(GroupReport groupReport) {
        String help =
                """
                        私聊帮助菜单
                        ============
                        @我 <命令><参数>即可使用。
                        -功能相关：
                        --随机图片：@我 /来点二次元 或 /随机图片 或 /来点涩图 或 /来点色图
                        ---参数举例：@我 /来点涩图[ 名称][ 图片数量][ 最低收藏点赞数][ 图片质量][ 是否为R18]
                        ----图片质量：1:mini、2:thumb、3:small、4:regular、5:origin
                        --帮助：@我 /帮助 或 /help
                        ============""";
        String url = clientPath + ClientApi.SEND_MSG.getApiURL();
        String uid = groupReport.getSender().getUserId();
        MessageType type = MessageType.PRIVATE;
        String template = CQMessageTemplate.textListTemplate(type, uid, List.of(help));
        System.out.println(template);
        System.out.println(url);
        HttpUtils.post(url, template);
    }

    /**
     * 群聊情况
     *
     * @param groupReport 群聊上报
     */
    private void groupHandler(GroupReport groupReport) {
        String help =
                """
                        帮助菜单
                        ============
                        @我 <命令><参数>即可使用。
                        -功能相关：
                        --随机图片：@我 来点二次元 或 随机图片 或 来点涩图 或 来点色图
                        --帮助：@我 /帮助 或 /help
                                                
                        -日常相关：
                        --绑定群老婆：[回复ta的消息] @<目标> 绑定群老婆
                        --群老婆人数：@我 查询群老婆人数 或 @我 查询群老婆 或 @我 查询老婆
                        --查自身精力：@我 查询自身精力 或 查询剩余精力 或 查询精力
                        --修改称呼：@我 [@目标] 修改老婆爱称 或 编辑老婆爱称 或 更改老婆爱称 [旧爱称 或 QQ] 新爱称
                                                
                        -涩涩相关：
                        --摸头：[@对象] /摸头[ <次数>次] 或 /摸头[ 你老婆的爱称][ <次数>次]
                        --闲聊：[@对象] /闲聊[ <次数>次] 或 /闲聊[ 你老婆的爱称][ <次数>次]
                        --袭胸：[@对象] /袭胸[ <次数>次] 或 /袭胸[ 你老婆的爱称][ <次数>次]
                        --蹭蹭：[@对象] /蹭蹭[ <次数>次] 或 /蹭蹭[ 你老婆的爱称][ <次数>次]
                        ============""";

        String url = clientPath + ClientApi.SEND_MSG.getApiURL();
        String gid = groupReport.getGroupId();
        MessageType type = MessageType.GROUP;
        String template = CQMessageTemplate.textListTemplate(type, gid, List.of(help));
        HttpUtils.post(url, template);
    }
}
