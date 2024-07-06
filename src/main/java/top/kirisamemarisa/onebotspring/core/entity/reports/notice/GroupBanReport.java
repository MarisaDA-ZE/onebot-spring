package top.kirisamemarisa.onebotspring.core.entity.reports.notice;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.notice.base.NoticeReport;
import top.kirisamemarisa.onebotspring.core.enums.reports.notices.NoticeSubType;
import top.kirisamemarisa.onebotspring.core.serializer.MrsEnumSerializer;

/**
 * @Author: MarisaDAZE
 * @Description: 群禁言
 * @Date: 2024/07/05
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GroupBanReport extends NoticeReport {

    /**
     * <p>事件子类型, 群禁言状态变化</p>
     */
    @JSONField(name = "sub_type", serializeUsing = MrsEnumSerializer.class)
    private NoticeSubType subType;

    /**
     * 群号
     */
    @JSONField(name = "group_id")
    private String groupId;

    /**
     * 管理员 QQ 号
     */
    @JSONField(name = "operator_id")
    private String operatorId;

    /**
     * 被禁言 QQ 号
     * <p style="color:orange;">注意：为全员禁言时为0</p>
     */
    @JSONField(name = "user_id")
    private String userId;

    /**
     * 禁言时长, 单位秒
     * <p style="color:orange;">注意：为全员禁言时为 -1</p>
     */
    @JSONField(name = "duration")
    private Integer duration;
}
