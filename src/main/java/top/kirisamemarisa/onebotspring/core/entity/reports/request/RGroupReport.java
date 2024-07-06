package top.kirisamemarisa.onebotspring.core.entity.reports.request;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.request.base.RequestReport;
import top.kirisamemarisa.onebotspring.core.enums.reports.request.RequestSubType;
import top.kirisamemarisa.onebotspring.core.serializer.MrsEnumSerializer;

/**
 * @Author: MarisaDAZE
 * @Description: 加群请求／邀请
 * @Date: 2024/07/05
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RGroupReport extends RequestReport {

    /**
     * 请求子类型
     */
    @JSONField(name = "sub_type", serializeUsing = MrsEnumSerializer.class)
    private RequestSubType subType;

    /**
     * 群号
     */
    @JSONField(name = "group_id")
    private String groupId;

    /**
     * 发送请求的 QQ 号
     */
    @JSONField(name = "user_id")
    private String userId;

    /**
     * 验证信息
     */
    @JSONField(name = "comment")
    private String comment;

    /**
     * 请求 flag, 在调用处理请求的 API 时需要传入
     */
    @JSONField(name = "flag")
    private String flag;

    /**
     * 是否同意请求，默认 不处理
     * <p style="color: #66CCFF;">快速操作字段</p>
     */
    @JSONField(name = "approve")
    private Boolean approve;

    /**
     * 拒绝理由 ( 仅在拒绝时有效 )，默认 无理由
     * <p style="color: #66CCFF;">快速操作字段</p>
     */
    @JSONField(name = "reason")
    private String reason;
}
