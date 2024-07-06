package top.kirisamemarisa.onebotspring.core.entity.reports.notice;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.notice.base.NoticeReport;
import top.kirisamemarisa.onebotspring.core.enums.reports.notices.NoticeSubType;
import top.kirisamemarisa.onebotspring.core.serializer.MrsEnumSerializer;

/**
 * @Author: MarisaDAZE
 * @Description: 精华消息变更
 * @Date: 2024/07/05
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EssenceReport extends NoticeReport {

    /**
     * 精华消息变更
     * <p>添加为add,移出为delete</p>
     */
    @JSONField(name = "sub_type", serializeUsing = MrsEnumSerializer.class)
    private NoticeSubType subType;

    /**
     * 群号
     */
    @JSONField(name = "group_id")
    private String groupId;

    /**
     * 消息发送者ID
     */
    @JSONField(name = "sender_id")
    private String senderId;

    /**
     * 操作者ID
     */
    @JSONField(name = "operator_id")
    private String operatorId;

    /**
     * 消息ID
     */
    @JSONField(name = "message_id")
    private String messageId;
}
