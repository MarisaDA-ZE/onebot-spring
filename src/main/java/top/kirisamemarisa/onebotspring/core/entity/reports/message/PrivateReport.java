package top.kirisamemarisa.onebotspring.core.entity.reports.message;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.CQMessage;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.base.MessageReport;
import top.kirisamemarisa.onebotspring.core.serializer.CQDataDeserializer;

/**
 * @Author: MarisaDAZE
 * @Description: 私聊上报
 * @Date: 2024/07/05
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PrivateReport extends MessageReport {

    /**
     * 接收者 QQ 号
     */
    @JSONField(name = "target_id")
    private String targetId;

    /**
     * 临时会话来源
     */
    @JSONField(name = "temp_source")
    private Integer tempSource;

    /**
     * 要回复的内容
     * <p>来源：快速操作</p>
     */
    @JSONField(name = "reply", deserializeUsing = CQDataDeserializer.class)
    private CQMessage[] reply;

    /**
     * 消息内容是否作为纯文本发送 ( 即不解析 CQ 码 )
     * <p>来源：快速操作</p>
     * <p style="color:orange;">注意：只在 reply 字段是字符串时有效</p>
     */
    @JSONField(name = "auto_escape")
    private Boolean autoEscape;

    public PrivateReport(String targetId, Integer tempSource) {
        this.targetId = targetId;
        this.tempSource = tempSource;
    }
}
