package top.kirisamemarisa.onebotspring.core.entity.reports.notice;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.notice.base.NoticeReport;
import top.kirisamemarisa.onebotspring.core.enums.reports.notices.NoticeSubType;
import top.kirisamemarisa.onebotspring.core.serializer.MrsEnumSerializer;

/**
 * @Author: MarisaDAZE
 * @Description: 群红包运气王提示
 * <p style="color: orange;">注意：此事件无法在手表协议上触发</p>
 * @Date: 2024/07/05
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LuckyKingReport extends NoticeReport {

    /**
     * 提示类型
     */
    @JSONField(name = "sub_type", serializeUsing = MrsEnumSerializer.class)
    private NoticeSubType subType;

    /**
     * 群号
     */
    @JSONField(name = "group_id")
    private String groupId;

    /**
     * 红包发送者id
     */
    @JSONField(name = "user_id")
    private String userId;

    /**
     * 运气王id
     */
    @JSONField(name = "target_id")
    private String targetId;

    public LuckyKingReport(String groupId, String userId, String targetId) {
        this.subType = NoticeSubType.LUCKY_KING;
        this.groupId = groupId;
        this.userId = userId;
        this.targetId = targetId;
    }
}
