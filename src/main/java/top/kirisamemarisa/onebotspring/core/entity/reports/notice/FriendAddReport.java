package top.kirisamemarisa.onebotspring.core.entity.reports.notice;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.notice.base.NoticeReport;

/**
 * @Author: MarisaDAZE
 * @Description: 好友添加
 * @Date: 2024/07/05
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FriendAddReport extends NoticeReport {

    /**
     * 新添加好友 QQ 号
     */
    @JSONField(name = "user_id")
    private String userId;
}
