package top.kirisamemarisa.onebotspring.core.entity.reports.message.cq;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.base.CQData;
import top.kirisamemarisa.onebotspring.core.enums.reports.message.cq.CQMessageType;
import top.kirisamemarisa.onebotspring.core.serializer.MrsEnumSerializer;

/**
 * @Author: MarisaDAZE
 * @Description: CQ消息段
 * @Date: 2024/07/03
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CQMessage {

    @JSONField(name = "type", serializeUsing = MrsEnumSerializer.class)
    private CQMessageType type;

    @JSONField(name = "data")
    private CQData data;
}
