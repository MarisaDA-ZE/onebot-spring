package top.kirisamemarisa.onebotspring.core.entity.reports.message.base;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.CQMessage;
import top.kirisamemarisa.onebotspring.core.entity.reports.common.Sender;
import top.kirisamemarisa.onebotspring.core.entity.reports.base.MrsReport;
import top.kirisamemarisa.onebotspring.core.enums.reports.message.MessageType;
import top.kirisamemarisa.onebotspring.core.enums.reports.SubType;
import top.kirisamemarisa.onebotspring.core.serializer.CQDataDeserializer;
import top.kirisamemarisa.onebotspring.core.serializer.MrsEnumSerializer;

/**
 * @Author: MarisaDAZE
 * @Description: 消息上报
 * @Date: 2024/07/03
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class MessageReport extends MrsReport {

    /**
     * 消息类型
     */
    @JSONField(name = "message_type", serializeUsing = MrsEnumSerializer.class)
    private MessageType messageType;

    /**
     * 消息子类型
     * <p>
     * 正常消息是 normal, 匿名消息是 anonymous, 系统提示 ( 如「管理员已禁止群内匿名聊天」 ) 是 notice
     * </p>
     */
    @JSONField(name = "sub_type", serializeUsing = MrsEnumSerializer.class)
    private SubType subType;

    /**
     * 消息ID
     */
    @JSONField(name = "message_id")
    private String messageId;

    /**
     * 发送者QQ号
     */
    @JSONField(name = "user_id")
    private String userId;

    /**
     * 一个消息链
     */
    @JSONField(name = "message", deserializeUsing = CQDataDeserializer.class)
    private CQMessage[] messages;

    /**
     * CQ 码格式的消息
     */
    @JSONField(name = "raw_message")
    private String rawMessage;

    /**
     * 字体
     */
    @JSONField(name = "font")
    private Integer font;

    /**
     * 发送人信息
     */
    @JSONField(name = "sender")
    private Sender sender;
}
