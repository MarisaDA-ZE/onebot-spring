package top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.base.CQData;

/**
 * @Author: MarisaDAZE
 * @Description: QQ表情
 * <p><a href="https://docs.go-cqhttp.org/cqcode/#qq-%E8%A1%A8%E6%83%85">go-cqhttp 参考</a></p>
 * <p><a href="https://github.com/botuniverse/onebot-11/blob/master/message/segment.md#qq-%E8%A1%A8%E6%83%85">onebot-11 参考</a></p>
 * @Date: 2024/07/02
 */
@Data
@ToString
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CQFace extends CQData {

    /**
     * <p>go-cqhttp: QQ 表情 ID</p>
     * <p>onebot-11: 同go-cqhttp</p>
     * <p>详见 <a href="https://github.com/kyubotics/coolq-http-api/wiki/%E8%A1%A8%E6%83%85-CQ-%E7%A0%81-ID-%E8%A1%A8">QQ表情ID表</a></p>
     */
    @JSONField(name = "id")
    private Integer id;
}
