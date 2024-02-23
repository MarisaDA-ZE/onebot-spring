package top.kirisamemarisa.onebotspring.core.util;

import cn.hutool.core.util.StrUtil;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.GroupReport;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.Massage;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.MAt;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.MText;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.base.MData;
import top.kirisamemarisa.onebotspring.core.enums.ContentType;

/**
 * @Author: MarisaDAZE
 * @Description: 工具类
 * @Date: 2024/2/16
 */
public class MrsUtil {

    /**
     * 将下划线命名转换为驼峰命名
     *
     * @param underscoreName 下划线命名字符串
     * @return 驼峰命名字符串
     */
    public static String toCamelCase(String underscoreName) {
        StringBuilder camelCase = new StringBuilder();
        boolean nextIsUpper = false;
        for (int i = 0; i < underscoreName.length(); i++) {
            char c = underscoreName.charAt(i);
            if (c == '_') {
                nextIsUpper = true;
            } else {
                if (nextIsUpper) {
                    camelCase.append(Character.toUpperCase(c));
                    nextIsUpper = false;
                } else {
                    camelCase.append(c);
                }
            }
        }
        return camelCase.toString();
    }

    /**
     * 将驼峰命名转换为下划线命名
     *
     * @param camelCaseName 驼峰命名字符串
     * @return 下划线命名字符串
     */
    public static String toUnderscore(String camelCaseName) {
        StringBuilder underscore = new StringBuilder();
        for (int i = 0; i < camelCaseName.length(); i++) {
            char c = camelCaseName.charAt(i);
            if (Character.isUpperCase(c)) {
                underscore.append('_');
                underscore.append(Character.toLowerCase(c));
            } else {
                underscore.append(c);
            }
        }
        return underscore.toString();
    }

    /**
     * 去除掉消息列表中的第一个at自己
     * <p>第一个at自己通常是用来触发方法的</p>
     *
     * @param groupReport .
     * @return .
     */
    public static GroupReport trimMassageAtSelf(GroupReport groupReport) {
        Massage[] messages = groupReport.getMessage();
        String selfId = groupReport.getSelfId();
        Massage[] newMessages = new Massage[messages.length - 1];
        int count = 0;
        int index = 0;
        for (Massage message : messages) {
            ContentType type = message.getType();
            if (type == ContentType.AT) {
                MAt at = (MAt) message.getData();
                if (at.getMention().equals(selfId)) {
                    // 第一次at自己，丢弃
                    if (count == 0) {
                        count++;
                    } else {
                        newMessages[index] = message;
                        index++;
                    }
                }
            } else {
                // 去除空字符串
                if (message.getType() == ContentType.TEXT) {
                    MText mText = (MText) message.getData();
                    String text = mText.getText();
                    if (StrUtil.isNotBlank(text)) {
                        newMessages[index] = message;
                        index++;
                    }
                } else {
                    newMessages[index] = message;
                    index++;
                }
            }
        }
        groupReport.setMessage(newMessages);
        return groupReport;
    }
}
