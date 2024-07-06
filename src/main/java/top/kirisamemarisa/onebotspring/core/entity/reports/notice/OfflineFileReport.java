package top.kirisamemarisa.onebotspring.core.entity.reports.notice;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.common.OfflineFile;
import top.kirisamemarisa.onebotspring.core.entity.reports.notice.base.NoticeReport;

/**
 * @Author: MarisaDAZE
 * @Description: 接收到离线文件
 * @Date: 2024/07/05
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OfflineFileReport extends NoticeReport {

    /**
     * 发送者id
     */
    @JSONField(name = "user_id")
    private String userId;

    /**
     * 文件数据
     */
    @JSONField(name = "file")
    private OfflineFile file;
}
