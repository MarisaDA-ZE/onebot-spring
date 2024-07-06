package top.kirisamemarisa.onebotspring.commands;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.kirisamemarisa.onebotspring.core.annotation.BotCommand;
import top.kirisamemarisa.onebotspring.core.api.ClientApi;
import top.kirisamemarisa.onebotspring.core.command.MrsCommand;
import top.kirisamemarisa.onebotspring.core.entity.reports.base.MrsReport;
import top.kirisamemarisa.onebotspring.core.entity.reports.common.Sender;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.GroupReport;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.CQMessage;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.CQText;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.base.CQData;
import top.kirisamemarisa.onebotspring.core.enums.reports.message.MessageType;
import top.kirisamemarisa.onebotspring.entity.mrspixiv.Illustration;
import top.kirisamemarisa.onebotspring.mapper.mrspixiv.IllustrationMapper;
import top.kirisamemarisa.onebotspring.utils.CommandUtil;
import top.kirisamemarisa.onebotspring.utils.HttpUtils;
import top.kirisamemarisa.onebotspring.core.util.CQMessageTemplate;

import java.util.List;


/**
 * @Author: MarisaDAZE
 * @Description: 获取随机图片
 * @Date: 2024/2/16
 */
@Component
@BotCommand
public class RandomImage implements MrsCommand {

    // Pixiv图片代理
    @Value("${mrs-bot.pixiv.proxy}")
    private String pixivProxy;

    // 机器人客户端地址
    @Value("${mrs-bot.default-client-url}")
    private String clientPath;

    @Resource
    private IllustrationMapper illustrationMapper;

    @Override
    public boolean trigger(MrsReport report) {
        if (!(report instanceof GroupReport groupReport)) {
            return false;
        }
        MessageType messageType = groupReport.getMessageType();
        // 群聊环境下需要at机器人
        if (messageType == MessageType.GROUP) {
            boolean isAt = CommandUtil.hasAtSelf(groupReport);
            if (!isAt) return false;
        }

        CQMessage[] messages = groupReport.getMessages();
        // 触发
        for (CQMessage message : messages) {
            CQData data = message.getData();
            if (data instanceof CQText cqText) {
                String ctx = (StrUtil.trim(cqText.getText()) + "");
                if (ctx.startsWith("/来点二次元") || ctx.startsWith("/来点色图") || ctx.startsWith("/来点涩图")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void action(MrsReport report) {
        System.out.println("获取随机图片...");
        if (!(report instanceof GroupReport groupReport)) {
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
        String url = clientPath + ClientApi.SEND_MSG.getApiURL();
        Sender sender = groupReport.getSender();
        String userId = sender.getUserId();
        CQMessage[] messages = groupReport.getMessages();
        int cit = 4;
        for (CQMessage message : messages) {
            CQData data = message.getData();
            if (data instanceof CQText cqText) {
                String context = StrUtil.trim(cqText.getText());
                String[] ts = context.split(" ");
                // 返回图像清晰度(1:mini、2:thumb、3:small、4:regular、5:origin)
                cit = ts.length >= 5 ? Integer.parseInt(ts[4]) : 4;
                break;
            }
        }
        QueryWrapper<Illustration> illusWrapper = generatorWrapper(groupReport);
        List<Illustration> illustrations = illustrationMapper.selectList(illusWrapper);
        int clarity = cit;
        List<String> urls = illustrations.stream().map(illustration -> {
                    String result = "";
                    switch (clarity) {
                        // 1:mini、2:thumb、3:small、4:regular、5:origin
                        case 1 -> result = illustration.getUrlMini();
                        case 2 -> result = illustration.getUrlThumb();
                        case 3 -> result = illustration.getUrlSmall();
                        case 4 -> result = illustration.getUrlRegular();
                        case 5 -> result = illustration.getUrlOriginal();
                    }
                    // 替换图片代理
                    return HttpUtils.setUrlProxy(result, pixivProxy);
                }
        ).toList();
        String template = CQMessageTemplate.imageListTemplate(MessageType.PRIVATE, userId, urls);
        System.out.println(template);
        HttpUtils.post(url, template);
    }

    /**
     * 群聊情况
     *
     * @param groupReport 群聊上报
     */
    private void groupHandler(GroupReport groupReport) {
        String url = clientPath + ClientApi.SEND_MSG.getApiURL();
        String groupId = groupReport.getGroupId();
        CQMessage[] messages = groupReport.getMessages();
        int cit = 4;
        for (CQMessage message : messages) {
            CQData data = message.getData();
            if (data instanceof CQText cqText) {
                String context = StrUtil.trim(cqText.getText());
                String[] ts = context.split(" ");
                // 返回图像清晰度(1:mini、2:thumb、3:small、4:regular、5:origin)
                cit = ts.length >= 5 ? Integer.parseInt(ts[4]) : 4;
                break;
            }
        }
        QueryWrapper<Illustration> illusWrapper = generatorWrapper(groupReport);
        List<Illustration> illustrations = illustrationMapper.selectList(illusWrapper);
        int clarity = cit;
        List<String> urls = illustrations.stream().map(illustration -> {
                    String result = "";
                    switch (clarity) {
                        // 1:mini、2:thumb、3:small、4:regular、5:origin
                        case 1 -> result = illustration.getUrlMini();
                        case 2 -> result = illustration.getUrlThumb();
                        case 3 -> result = illustration.getUrlSmall();
                        case 4 -> result = illustration.getUrlRegular();
                        case 5 -> result = illustration.getUrlOriginal();
                    }
                    // 替换图片代理
                    return HttpUtils.setUrlProxy(result, pixivProxy);
                }
        ).toList();
        String template = CQMessageTemplate.imageListTemplate(MessageType.GROUP, groupId, urls);
        System.out.println(template);
        HttpUtils.post(url, template);
    }

    private QueryWrapper<Illustration> generatorWrapper(GroupReport groupReport) {
        MessageType mType = groupReport.getMessageType();
        CQMessage[] messages = groupReport.getMessages();
        QueryWrapper<Illustration> queryWrapper = new QueryWrapper<>();
        int limit = 1;
        for (CQMessage message : messages) {
            CQData data = message.getData();
            if (data instanceof CQText cqText) {
                String context = StrUtil.trim(cqText.getText());
                String[] ts = context.split(" ");
                // [ 名称][ 图片数量][ 最低收藏点赞数][ 是否为R18]

                // 搜索关键词
                if (ts.length >= 2) {
                    String title = ts[1].trim();
                    queryWrapper.and(
                            wr -> wr.like("TITLE", title)
                                    .or()
                                    .like("TAGS", title)
                    );
                }
                // 返回条数限制
                if (ts.length >= 3) {
                    int count = Integer.parseInt(ts[2]);
                    limit = Math.min(count, 10);
                }

                // 最低点赞、收藏数限制
                if (ts.length >= 4) {
                    int bookmark = Math.max(1, Integer.parseInt(ts[3]));
                    queryWrapper.and(
                            wr -> wr.gt("LIKE_COUNT", bookmark)
                                    .gt("BOOKMARK_COUNT", bookmark)
                    );
                }

                // R18限制，默认全年龄
                int isR18 = ts.length >= 6 ? Integer.parseInt(ts[5]) : 0;

                // 群聊不允许发涩图
                if (mType == MessageType.GROUP) isR18 = 0;

                queryWrapper.eq("IS_R18", isR18);
                break;
            }
        }
        queryWrapper.last("ORDER BY RAND() LIMIT " + limit);
        return queryWrapper;
    }
}
