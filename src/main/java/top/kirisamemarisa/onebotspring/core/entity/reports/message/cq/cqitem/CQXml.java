package top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.base.CQData;

/**
 * @Author: MarisaDAZE
 * @Description: XML 消息
 * <p><a href="https://docs.go-cqhttp.org/cqcode/#xml-%E6%B6%88%E6%81%AF">go-cqhttp 参考</a></p>
 * <p><a href="https://github.com/botuniverse/onebot-11/blob/master/message/segment.md#xml-%E6%B6%88%E6%81%AF">onebot-11 参考</a></p>
 * @Date: 2024/07/02
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CQXml extends CQData {

    /**
     * <p>go-cqhttp: 可xml内容, xml中的value部分, 记得实体化处理</p>
     * <p>onebot-11: XML内容</p>
     */
    @JSONField(name = "data")
    private String data;

    /**
     * <p>go-cqhttp: 可能为空, 或空字符串</p>
     * <p>onebot-11: 不支持</p>
     */
    @JSONField(name = "resid")
    private Integer resid;
}
