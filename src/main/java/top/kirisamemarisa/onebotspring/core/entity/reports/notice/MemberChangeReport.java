package top.kirisamemarisa.onebotspring.core.entity.reports.notice;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.notice.base.NoticeReport;
import top.kirisamemarisa.onebotspring.core.enums.reports.notices.NoticeSubType;
import top.kirisamemarisa.onebotspring.core.serializer.MrsEnumSerializer;

/**
 * @Author: MarisaDAZE
 * @Description: 群成员变化
 * <p>合并了群成员增加（group_increase）、群成员减少（group_decrease）两种变化</p>
 * @Date: 2024/07/05
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MemberChangeReport extends NoticeReport {

    /**
     * <p>事件子类型, 群成员状态变化</p>
     */
    @JSONField(name = "sub_type", serializeUsing = MrsEnumSerializer.class)
    private NoticeSubType subType;

    /**
     * 群号
     */
    @JSONField(name = "group_id")
    private String groupId;

    /**
     * 操作者 QQ 号
     * <p>如果是主动退群, 则和 user_id 相同</p>
     */
    @JSONField(name = "operator_id")
    private String operatorId;

    /**
     * 加入/离开者 QQ 号
     */
    @JSONField(name = "user_id")
    private String userId;
}
