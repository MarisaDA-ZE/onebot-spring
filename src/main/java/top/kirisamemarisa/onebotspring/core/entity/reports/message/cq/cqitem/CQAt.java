package top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.base.CQData;

/**
 * @Author: MarisaDAZE
 * @Description: @某人
 * <p><a href="https://docs.go-cqhttp.org/cqcode/#%E6%9F%90%E4%BA%BA">go-cqhttp 参考</a></p>
 * <p><a href="https://github.com/botuniverse/onebot-11/blob/master/message/segment.md#%E6%9F%90%E4%BA%BA">onebot-11 参考</a></p>
 * @Date: 2024/07/02
 */
@Data
@ToString
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CQAt extends CQData {

    /**
     * <p>go-cqhttp: at的QQ号，all表示全体成员</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "qq")
    private String qq;

    /**
     * <p>go-cqhttp: 当在群中找不到此QQ号的名称时才会生效</p>
     * <p>onebot-11: 不支持</p>
     */
    @JSONField(name = "name")
    private String name;

    public CQAt(String qq) {
        this.qq = qq;
    }
}
