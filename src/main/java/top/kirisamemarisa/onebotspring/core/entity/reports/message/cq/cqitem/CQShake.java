package top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.base.CQData;

/**
 * @Author: MarisaDAZE
 * @Description: 返回窗口抖动（戳一戳）对象
 * <p><a href="https://docs.go-cqhttp.org/cqcode/#%E7%AA%97%E5%8F%A3%E6%8A%96%E5%8A%A8-%E6%88%B3%E4%B8%80%E6%88%B3">go-cqhttp 参考</a></p>
 * <p><a href="https://github.com/botuniverse/onebot-11/blob/master/message/segment.md#%E7%AA%97%E5%8F%A3%E6%8A%96%E5%8A%A8%E6%88%B3%E4%B8%80%E6%88%B3-">onebot-11 参考</a></p>
 * @Date: 2024/07/02
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class CQShake extends CQData {
    // 窗口抖动（戳一戳）返回一个空对象
}
