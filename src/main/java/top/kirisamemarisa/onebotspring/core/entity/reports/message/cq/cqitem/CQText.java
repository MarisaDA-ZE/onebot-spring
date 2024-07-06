package top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.base.CQData;

/**
 * @Author: MarisaDAZE
 * @Description: 文本对象
 * @Date: 2024/07/02
 */
@Data
@ToString
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CQText extends CQData {
    /**
     * 文本数据
     */
    @JSONField(name = "text")
    private String text;
}
