package top.kirisamemarisa.onebotspring.utils;


import org.springframework.stereotype.Component;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.GroupReport;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.CQAt;
import top.kirisamemarisa.onebotspring.entity.onebot.sexes.CmdPreParam;
import top.kirisamemarisa.onebotspring.entity.onebot.sexes.GroupSexWife;
import top.kirisamemarisa.onebotspring.enums.CommandType;
import top.kirisamemarisa.onebotspring.enums.Emoji;

import java.util.List;

/**
 * @Author: MarisaDAZE
 * @Description: 涩涩对象组装工具
 * @Date: 2024/3/18
 */
@Component
public class SexObjectUtil {

    /**
     * <p>快速获取 /指令 ＜target＞ [count次] 形式或</p>
     * <p>@＜target＞ /指令 [count次]形式命令中的参数</p>
     *
     * @param groupReport 群聊上报
     * @param cmds        触发命令
     * @return 结果
     */
    public static CmdPreParam getPreParam(GroupReport groupReport, List<String> cmds) {
        List<CQAt> ats = CommandUtil.getAts(groupReport);
        String target;
        int count;
        String error = "指令格式有误哦，详情请查看涩涩帮助" + Emoji.SORROW_9.getEmoji();
        switch (ats.size()) {
            // /指令 <target> [count次]
            case 0 -> {
                String command = CommandUtil.concatenateMText(groupReport);
                String trimmed = CommandUtil.trimCommand(command, cmds);
                if (trimmed == null) {
                    System.err.println("命令修剪后为空.");
                    return new CmdPreParam(null, 0, error);
                }
                String[] array = CommandUtil.getTargetAndCount(trimmed, CommandType.TYPE_2);
                if (array == null) {
                    System.err.println("参数获取错误.");
                    return new CmdPreParam(null, 0, error);
                }
                target = array[0];
                count = Integer.parseInt(array[1]);
            }
            // @<target> /指令 [count次]
            case 1 -> {
                target = ats.get(0).getQq();
                String command = CommandUtil.concatenateMText(groupReport);
                String trimmed = CommandUtil.trimCommand(command, cmds);
                if (trimmed == null) {
                    System.err.println("命令修剪后为空.");
                    return new CmdPreParam(null, 0, error);
                }
                String[] array = CommandUtil.getTargetAndCount(trimmed, CommandType.TYPE_1);
                if (array == null) {
                    System.err.println("参数获取错误.");
                    return new CmdPreParam(null, 0, error);
                }
                count = Integer.parseInt(array[1]);
            }
            // 其它情况
            default -> {
                System.err.println("其它情况.");
                return new CmdPreParam(null, 0, error);
            }
        }
        // 次数至少是1次
        if (count < 1) return new CmdPreParam(null, 0, error);
        return new CmdPreParam(target, count, null);
    }

    /**
     * 当搜索结果大于1时，通用的返回结果
     *
     * @param sexWives 群老婆列表
     * @return 提示字符串
     */
    public static String moreOneTarget(List<GroupSexWife> sexWives) {
        StringBuilder sb = new StringBuilder();
        sexWives.forEach(wife -> sb.append(wife.getLoveName()).append("、"));
        if (sb.length() > 1) sb.delete(sb.length() - 1, sb.length());
        return "爱称似乎有点模糊，共找到" + sb + "（" + sexWives.size() + "个）群老婆，请确定老婆后重新执行。";
    }

}