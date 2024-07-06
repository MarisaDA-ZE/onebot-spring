package top.kirisamemarisa.onebotspring.core.entity.reports.notice;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.notice.base.NoticeReport;

/**
 * @Author: MarisaDAZE
 * @Description: 群消息撤回
 * @Date: 2024/07/05
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GroupRecallReport extends NoticeReport {

    /**
     * 群号
     */
    @JSONField(name = "group_id")
    private String groupId;

    /**
     * 消息发送者 QQ 号
     */
    @JSONField(name = "user_id")
    private String userId;

    /**
     * 操作者 QQ 号
     */
    @JSONField(name = "operatorId")
    private String operator_id;

    /**
     * 被撤回的消息 ID
     */
    @JSONField(name = "message_id")
    private String messageId;
}
