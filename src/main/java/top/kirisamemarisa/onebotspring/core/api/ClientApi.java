package top.kirisamemarisa.onebotspring.core.api;

public enum ClientApi {
    SEND_MSG("/send_msg"),       // 发送消息
    GET_GROUP_MEMBER_INFO("/get_group_member_info"),// 获取群成员信息
    TEST("/test"); // 保留

    // 获取url
    private final String url;

    ClientApi(String urlText) {
        this.url = urlText;
    }

    public String getApiURL() {
        return url;
    }

}
