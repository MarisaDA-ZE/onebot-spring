package top.kirisamemarisa.onebotspring.common;

import cn.hutool.core.util.StrUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: MarisaDAZE
 * @Description: Constants.描述
 * @Date: 2024/1/23
 */
public class Constant {

    public static final String CONFIG_SUFFIX = ".config";   // 配置信息的后缀，通常用于存redis时
    public static final Integer TIMEOUT = 60 * 15;// 过期时间(15分钟)
    public static final long MILLIS_15 = 1000 * 60 * 15;// 15分钟（毫秒值）
    public static final long MILLIS_30 = 1000 * 60 * 30;// 30分钟（毫秒值）
    public static final String MASSAGE_ID_MAP = ":massage_id_map";// 消息ID和发送者QQ的对应
    public static final String DEFAULT_CMD_SEPARATOR = " ";   // 默认命令分隔符
    public static final int DEFAULT_EMOTION = 60;   // 默认情绪值

    // 情绪字典（0~9：绝望，10~29：悲伤，30~39：焦虑，40~49：忧郁，50~69：平静，70~79：高兴，80~100+：兴奋）
    public static final Map<String, String> EMOTION_MAP = new HashMap<>();
    public static final int DEFAULT_INTIMATE = 45;   // 默认亲密度

    // 亲密度字典（0~9：厌恶，10~19：讨厌，20~39：陌生，40~49：熟悉，50~59：朋友，60~79：喜欢，80~100+：夫妻）
    public static final Map<String, String> INTIMATE_MAP = new HashMap<>();

    static {
        // 情绪字典
        EMOTION_MAP.put("[0, 9]", "绝望");
        EMOTION_MAP.put("[10, 29]", "悲伤");
        EMOTION_MAP.put("[30, 39]", "焦虑");
        EMOTION_MAP.put("[40, 49]", "忧郁");
        EMOTION_MAP.put("[50, 69]", "平静");
        EMOTION_MAP.put("[70, 79]", "高兴");
        EMOTION_MAP.put("[80, 999999]", "兴奋");

        // 亲密度字典
        INTIMATE_MAP.put("[0, 9]", "厌恶");
        INTIMATE_MAP.put("[10, 19]", "讨厌");
        INTIMATE_MAP.put("[20, 39]", "陌生");
        INTIMATE_MAP.put("[40, 49]", "熟悉");
        INTIMATE_MAP.put("[50, 59]", "朋友");
        INTIMATE_MAP.put("[60, 79]", "喜欢");
        INTIMATE_MAP.put("[80, 999999]", "夫妻");

    }

    /**
     * 按值获取对应区间的情绪描述
     *
     * @param num .
     * @return .
     */
    public static String getEmotionDictText(int num) {
        return getDictTextByNumRange(EMOTION_MAP, num);
    }

    /**
     * 按值获取对应区间的亲密度描述
     *
     * @param num .
     * @return .
     */
    public static String getIntimateDictText(int num) {
        return getDictTextByNumRange(INTIMATE_MAP, num);
    }

    /**
     * 按数值区间获取对应的值
     *
     * @param map .
     * @param num .
     * @return .
     */
    private static String getDictTextByNumRange(Map<String, String> map, int num) {
        Set<String> keySet = map.keySet();
        String key = null;
        for (String k : keySet) {
            String[] split = k.split(",");
            String _min = (split[0]).replaceAll("[^0-9]", "");
            String _max = (split[1]).replaceAll("[^0-9]", "");
            int min = Integer.parseInt(_min);
            int max = Integer.parseInt(_max);
            if (num >= min && num <= max) {
                key = k;
                break;
            }
        }
        if (StrUtil.isEmpty(key)) return null;
        return map.get(key);
    }

}
