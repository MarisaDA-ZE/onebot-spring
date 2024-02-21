package top.kirisamemarisa.onebotspring.utils;

import org.springframework.util.ObjectUtils;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.GroupReport;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.Massage;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.MAt;
import top.kirisamemarisa.onebotspring.core.enums.ContentType;
import top.kirisamemarisa.onebotspring.core.enums.MassageType;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: MarisaDAZE
 * @Description: CommandUtil.描述
 * @Date: 2024/2/16
 */
public class CommandUtil {

    /**
     * 是否含有at消息
     *
     * @param groupReport 上报消息
     * @return .
     */
    public static boolean groupBeAt(GroupReport groupReport) {
        String selfId = groupReport.getSelfId();
        MassageType messageType = groupReport.getMessageType();
        Massage[] messages = groupReport.getMessage();
        if (messageType == MassageType.GROUP) {
            List<?> ats = Arrays.stream(messages).filter(msg -> {
                // 存在at则判断at的是不是自己
                if (msg.getType() == ContentType.AT) {
                    MAt at = (MAt) msg.getData();
                    String target = at.getMention();
                    return (selfId + "").equals(target);
                }
                return false;
            }).toList();
            return !ObjectUtils.isEmpty(ats);
        }
        return false;
    }
}
