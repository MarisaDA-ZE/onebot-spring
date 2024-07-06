package top.kirisamemarisa.onebotspring.core.entity.reports.notice;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.notice.base.NoticeReport;

/**
 * @Author: MarisaDAZE
 * @Description: 群成员名片更新
 * <p style="color: orange;">此事件不保证时效性, 仅在收到消息时校验卡片</p>
 * @Date: 2024/07/05
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GroupCardReport extends NoticeReport {

    /**
     * 群号
     */
    @JSONField(name = "group_id")
    private String groupId;

    /**
     * 成员id
     */
    @JSONField(name = "user_id")
    private String userId;

    /**
     * 新名片
     */
    @JSONField(name = "card_new")
    private String cardNew;

    /**
     * 旧名片
     */
    @JSONField(name = "card_old")
    private String cardOld;
}
