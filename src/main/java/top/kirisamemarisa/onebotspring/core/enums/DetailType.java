package top.kirisamemarisa.onebotspring.core.enums;

/**
 * @Author: MarisaDAZE
 * @Description: DetailType.描述
 * @Date: 2024/1/20
 */
public enum DetailType {
    GROUP("group"),     // 群聊消息
    FRIEND("friend"),  // 私聊消息
    PRIVATE("private"); // 私聊消息

    private final String detailType;

    DetailType(String detailType){
        this.detailType = detailType;
    }

    public String getDetailType() {
        return detailType;
    }
}
