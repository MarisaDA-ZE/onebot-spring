package top.kirisamemarisa.onebotspring.common;

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
}
