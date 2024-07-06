package top.kirisamemarisa.onebotspring.core.entity.reports.notice;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.notice.base.NoticeReport;
import top.kirisamemarisa.onebotspring.core.enums.reports.notices.NoticeSubType;
import top.kirisamemarisa.onebotspring.core.serializer.MrsEnumSerializer;

/**
 * @Author: MarisaDAZE
 * @Description: 戳一戳
 * <p>包含好友戳一戳和群内戳一戳</p>
 * <p style="color: orange;">注意：群内戳一戳无法在手表协议上触发</p>
 * @Date: 2024/07/05
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PokeReport extends NoticeReport {

    /**
     * 提示类型
     */
    @JSONField(name = "sub_type", serializeUsing = MrsEnumSerializer.class)
    private NoticeSubType subType;

    /**
     * 发送者 QQ 号
     * <p style="color: orange;">好友戳一戳时此字段有效</p>
     */
    @JSONField(name = "sender_id")
    private String senderId;

    /**
     * 群号
     * <p style="color: orange;">群内戳一戳时此字段有效</p>
     */
    @JSONField(name = "group_id")
    private String groupId;

    /**
     * 发送者 QQ 号
     */
    @JSONField(name = "user_id")
    private String userId;

    /**
     * 被戳者 QQ 号
     */
    @JSONField(name = "target_id")
    private String targetId;

    public PokeReport(String userId, String targetId) {
        this.subType = NoticeSubType.POKE;
        this.userId = userId;
        this.targetId = targetId;
    }
}
