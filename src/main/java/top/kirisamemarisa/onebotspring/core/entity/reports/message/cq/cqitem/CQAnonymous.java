package top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.base.CQData;
import top.kirisamemarisa.onebotspring.core.enums.reports.message.cq.cqitem.IgnoreAction;
import top.kirisamemarisa.onebotspring.core.serializer.MrsEnumSerializer;

/**
 * @Author: MarisaDAZE
 * @Description: 匿名发消息
 * <p><a href="https://docs.go-cqhttp.org/cqcode/#%E5%8C%BF%E5%90%8D%E5%8F%91%E6%B6%88%E6%81%AF">go-cqhttp 参考</a></p>
 * <p><a href="https://github.com/botuniverse/onebot-11/blob/master/message/segment.md#%E5%8C%BF%E5%90%8D%E5%8F%91%E6%B6%88%E6%81%AF-">onebot-11 参考</a></p>
 * @Date: 2024/07/02
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CQAnonymous extends CQData {

    /**
     * <p>go-cqhttp: 可选, 表示无法匿名时是否继续发送（0、1）</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "ignore", serializeUsing = MrsEnumSerializer.class)
    private IgnoreAction ignore;
}
