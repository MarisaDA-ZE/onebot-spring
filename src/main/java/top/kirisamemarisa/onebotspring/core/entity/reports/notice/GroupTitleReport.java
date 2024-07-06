package top.kirisamemarisa.onebotspring.core.entity.reports.notice;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.notice.base.NoticeReport;
import top.kirisamemarisa.onebotspring.core.enums.reports.notices.NoticeSubType;
import top.kirisamemarisa.onebotspring.core.serializer.MrsEnumSerializer;

/**
 * @Author: MarisaDAZE
 * @Description: 群成员头衔变更
 * @Date: 2024/07/05
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GroupTitleReport extends NoticeReport {

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
     * 变更头衔的用户 QQ 号
     */
    @JSONField(name = "user_id")
    private String userId;

    /**
     * 获得的新头衔
     */
    @JSONField(name = "title")
    private String title;

    public GroupTitleReport(String groupId, String userId, String title) {
        this.subType = NoticeSubType.TITLE;
        this.groupId = groupId;
        this.userId = userId;
        this.title = title;
    }
}
