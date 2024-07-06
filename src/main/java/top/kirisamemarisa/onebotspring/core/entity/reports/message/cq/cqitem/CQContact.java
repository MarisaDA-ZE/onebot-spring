package top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.base.CQData;
import top.kirisamemarisa.onebotspring.core.enums.reports.message.cq.cqitem.CQRecType;
import top.kirisamemarisa.onebotspring.core.serializer.MrsEnumSerializer;

/**
 * @Author: MarisaDAZE
 * @Description: 推荐好友/群
 * <p><a href="https://docs.go-cqhttp.org/cqcode/#%E6%8E%A8%E8%8D%90%E5%A5%BD%E5%8F%8B-%E7%BE%A4">go-cqhttp 参考</a></p>
 * <p><a href="https://github.com/botuniverse/onebot-11/blob/master/message/segment.md#%E6%8E%A8%E8%8D%90%E5%A5%BD%E5%8F%8B">onebot-11 参考</a></p>
 * @Date: 2024/07/02
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CQContact extends CQData {

    /**
     * <p>go-cqhttp: 推荐好友/群（qq:好友, group: 群）</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "type", serializeUsing = MrsEnumSerializer.class)
    private CQRecType type;

    /**
     * <p>go-cqhttp: 被推荐人/群的QQ号</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "id")
    private String id;
}
