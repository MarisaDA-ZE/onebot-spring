package top.kirisamemarisa.onebotspring.core.entity.reports.metaevent.base;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.base.MrsReport;
import top.kirisamemarisa.onebotspring.core.enums.reports.metaevent.MetaEventType;
import top.kirisamemarisa.onebotspring.core.serializer.MrsEnumSerializer;

/**
 * @Author: MarisaDAZE
 * @Description: 元事件上报
 * <p>post_type 为 meta_event 的上报会有以下有效通用数据</p>
 * @Date: 2024/07/05
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class MetaEventReport extends MrsReport {

    /**
     * 请求类型
     */
    @JSONField(name = "meta_event_type", serializeUsing = MrsEnumSerializer.class)
    private MetaEventType metaEventType;
}
