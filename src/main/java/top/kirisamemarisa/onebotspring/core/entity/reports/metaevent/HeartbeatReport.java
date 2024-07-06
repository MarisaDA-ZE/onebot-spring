package top.kirisamemarisa.onebotspring.core.entity.reports.metaevent;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.metaevent.base.MetaEventReport;

/**
 * @Author: MarisaDAZE
 * @Description: 心跳包
 * @Date: 2024/07/05
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HeartbeatReport extends MetaEventReport {

    /**
     * 应用程序状态
     * <p><a href="https://docs.go-cqhttp.org/reference/data_struct.html#post-type">参考</a></p>
     */
    @JSONField(name = "status")
    private HeartStatus status;

    /**
     * 距离上一次心跳包的时间(单位是毫秒)
     */
    @JSONField(name = "interval")
    private Long interval;
}
