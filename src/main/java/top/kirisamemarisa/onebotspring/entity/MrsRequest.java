package top.kirisamemarisa.onebotspring.entity;

import lombok.Data;
import lombok.ToString;
import top.kirisamemarisa.onebotspring.core.entity.GroupReport;
import top.kirisamemarisa.onebotspring.core.enums.DetailType;

import java.util.List;

/**
 * @Author: MarisaDAZE
 * @Description: MrsRequest.描述
 * @Date: 2024/1/20
 */
@Data
@ToString
public class MrsRequest {

    private DetailType detail_type;     // 内容类型（群聊还是私聊）
    private String self_id;             // 机器人QQ号
    private DetailType message_type;    // 内容类型 似乎和detail_type是一样的
    private String message_id;          // 消息ID
    private String type;                // 消息类型，一般是"massage"
    private List<GroupReport> message;      // 消息列表
    private String sub_type;
    private String group_id;
    private String user_id;
    private Sender sender;
    private Self self;
    private String post_type;
    private Integer time;

}
