package top.kirisamemarisa.onebotspring.core.entity.reports.request;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.request.base.RequestReport;

/**
 * @Author: MarisaDAZE
 * @Description: 加好友请求
 * @Date: 2024/07/05
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RFriendReport extends RequestReport {

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
     * 添加后的好友备注（仅在同意时有效），默认 无备注
     * <p style="color: #66CCFF;">快速操作字段</p>
     */
    @JSONField(name = "remark")
    private String remark;
}
