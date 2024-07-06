package top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.base.CQData;

/**
 * @Author: MarisaDAZE
 * @Description: 红包（仅接收）
 * <p><a href="https://docs.go-cqhttp.org/cqcode/#%E7%BA%A2%E5%8C%85">go-cqhttp 参考</a></p>
 * <p>onebot-11 不支持</p>
 * @Date: 2024/07/02
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CQRedbag extends CQData {

    /**
     * <p>go-cqhttp: 祝福语/口令</p>
     * <p>onebot-11: 不支持</p>
     */
    @JSONField(name = "title")
    private String title;
}
