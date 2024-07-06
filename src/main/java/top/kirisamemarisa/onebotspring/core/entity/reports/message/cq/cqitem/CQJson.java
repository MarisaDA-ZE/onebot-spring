package top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.base.CQData;

/**
 * @Author: MarisaDAZE
 * @Description: JSON 消息
 * <p><a href="https://docs.go-cqhttp.org/cqcode/#json-%E6%B6%88%E6%81%AF">go-cqhttp 参考</a></p>
 * <p><a href="https://github.com/botuniverse/onebot-11/blob/master/message/segment.md#json-%E6%B6%88%E6%81%AF">onebot-11 参考</a></p>
 * @Date: 2024/07/02
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CQJson extends CQData {

    /**
     * <p>go-cqhttp: json内容, json的所有字符串记得实体化处理</p>
     * <p>转义:
     * ","=> &#44;
     * "&"=> &amp;
     * "["=> &#91;
     * "]"=> &#93;
     * </p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "data")
    private String data;

    /**
     * <p>go-cqhttp: 默认不填为0, 走小程序通道, 填了走富文本通道发送</p>
     * <p>onebot-11: 不支持</p>
     */
    @JSONField(name = "resid")
    private Integer resid;
}
