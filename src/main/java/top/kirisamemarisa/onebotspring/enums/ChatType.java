package top.kirisamemarisa.onebotspring.enums;

/**
 * @Author: MarisaDAZE
 * @Description: MessageType.描述
 * @Date: 2024/1/20
 */
public enum ChatType {
    GROUP("group"),     // 群聊消息
    PRIVATE("private"); // 私聊消息

    private final String messageType;

    ChatType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageType() {
        return messageType;
    }
}
