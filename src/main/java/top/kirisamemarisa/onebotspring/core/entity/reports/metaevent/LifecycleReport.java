package top.kirisamemarisa.onebotspring.core.entity.reports.metaevent;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.metaevent.base.MetaEventReport;
import top.kirisamemarisa.onebotspring.core.enums.reports.metaevent.MetaEventSubType;
import top.kirisamemarisa.onebotspring.core.serializer.MrsEnumSerializer;

/**
 * @Author: MarisaDAZE
 * @Description: 生命周期
 * @Date: 2024/07/05
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LifecycleReport extends MetaEventReport {

    /**
     * 子类型
     */
    @JSONField(name = "sub_type", serializeUsing = MrsEnumSerializer.class)
    private MetaEventSubType subType;
}
