package top.kirisamemarisa.onebotspring.utils;

import cn.hutool.core.util.StrUtil;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.GroupReport;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.Massage;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.MAt;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.MText;
import top.kirisamemarisa.onebotspring.core.enums.ContentType;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: MarisaDAZE
 * @Description: 命令工具类
 * @Date: 2024/2/28
 */
public class CommandUtils {

    /**
     * 排除at自己
     * <p>如果一个上报中有多条at自己的消息，那只会被排除掉一次</p>
     * <p>这个方法还会去除掉空的消息元素</p>
     *
     * @param groupReport 上报消息
     * @return 排除后的上报
     */
    public static GroupReport excludeAtSelfOne(GroupReport groupReport) {
        Massage[] messages = groupReport.getMessage();
        String selfId = groupReport.getSelfId();
        List<Massage> massageList = new ArrayList<>();
        boolean first = true;
        for (Massage message : messages) {
            ContentType type = message.getType();
            switch (type) {
                case AT -> {
                    MAt at = (MAt) message.getData();
                    String atId = at.getMention();
                    if (first) {
                        // 是第一次at自己
                        if ((atId + "").equals(selfId)) {
                            first = false;
                        } else {
                            massageList.add(message);
                        }
                    } else {
                        massageList.add(message);
                    }
                }
                case TEXT -> {
                    MText mText = (MText) message.getData();
                    String text = mText.getText();
                    if (StrUtil.isNotBlank(text.trim())) {
                        massageList.add(message);
                    }
                }
                default -> massageList.add(message);
            }
        }
        groupReport.setMessage(massageList.toArray(new Massage[0]));
        return groupReport;
    }
}
