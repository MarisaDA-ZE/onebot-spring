package top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.base.CQData;

/**
 * @Author: MarisaDAZE
 * @Description: 掷骰子魔法
 * <p><a href="https://docs.go-cqhttp.org/cqcode/#%E6%8E%B7%E9%AA%B0%E5%AD%90%E9%AD%94%E6%B3%95%E8%A1%A8%E6%83%85">go-cqhttp 参考</a></p>
 * <p><a href="https://github.com/botuniverse/onebot-11/blob/master/message/segment.md#%E6%8E%B7%E9%AA%B0%E5%AD%90%E9%AD%94%E6%B3%95%E8%A1%A8%E6%83%85">onebot-11 参考</a></p>
 * @Date: 2024/07/02
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class CQDice extends CQData {
    // 掷骰子魔法返回一个空对象
}
