package top.kirisamemarisa.onebotspring.core.entity.reports.notice;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.common.GroupFile;
import top.kirisamemarisa.onebotspring.core.entity.reports.notice.base.NoticeReport;

/**
 * @Author: MarisaDAZE
 * @Description: 群文件上传
 * @Date: 2024/07/05
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GroupUploadReport extends NoticeReport {

    /**
     * 群号
     */
    @JSONField(name = "group_id")
    private String groupId;

    /**
     * 发送者 QQ 号
     */
    @JSONField(name = "user_id")
    private String userId;

    /**
     * 文件信息
     */
    @JSONField(name = "file")
    private GroupFile file;
}
