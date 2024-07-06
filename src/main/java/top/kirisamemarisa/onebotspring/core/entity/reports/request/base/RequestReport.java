package top.kirisamemarisa.onebotspring.core.entity.reports.request.base;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.base.MrsReport;
import top.kirisamemarisa.onebotspring.core.enums.reports.request.RequestType;
import top.kirisamemarisa.onebotspring.core.serializer.MrsEnumSerializer;

/**
 * @Author: MarisaDAZE
 * @Description: 请求上报
 * <p>post_type 为 request 的上报会有以下有效通用数据</p>
 * @Date: 2024/07/05
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class RequestReport extends MrsReport {

    /**
     * 请求类型
     */
    @JSONField(name = "request_type", serializeUsing = MrsEnumSerializer.class)
    private RequestType requestType;
}
