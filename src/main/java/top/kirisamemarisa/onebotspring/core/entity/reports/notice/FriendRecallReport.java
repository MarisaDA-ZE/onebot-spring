package top.kirisamemarisa.onebotspring.core.entity.reports.notice;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.notice.base.NoticeReport;

/**
 * @Author: MarisaDAZE
 * @Description: 好友消息撤回
 * @Date: 2024/07/05
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FriendRecallReport extends NoticeReport {

    /**
     * 好友 QQ 号
     */
    @JSONField(name = "user_id")
    private String userId;

    /**
     * 被撤回的消息 ID
     */
    @JSONField(name = "message_id")
    private String messageId;
}
