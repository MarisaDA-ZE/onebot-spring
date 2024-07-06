package top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.base.CQData;

/**
 * @Author: MarisaDAZE
 * @Description: 戳一戳
 * <p><a href="https://docs.go-cqhttp.org/cqcode/#%E6%88%B3%E4%B8%80%E6%88%B3">go-cqhttp 参考</a></p>
 * <p><a href="https://github.com/botuniverse/onebot-11/blob/master/message/segment.md#%E6%88%B3%E4%B8%80%E6%88%B3">onebot-11 参考</a></p>
 * @Date: 2024/07/02
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CQPoke extends CQData {

    /**
     * <p>go-cqhttp: 需要戳的成员</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "qq")
    private String qq;

    /**
     * <p>go-cqhttp: 不支持</p>
     * <p>onebot-11: 类型</p>
     * <p>详见 <a href="https://github.com/mamoe/mirai/blob/f5eefae7ecee84d18a66afce3f89b89fe1584b78/mirai-core/src/commonMain/kotlin/net.mamoe.mirai/message/data/HummerMessage.kt#L49">Mirai的PokeMessage类</a></p>
     */
    @JSONField(name = "type")
    private String type;

    /**
     * <p>go-cqhttp: 不支持</p>
     * <p>onebot-11: ID，详情同type字段</p>
     */
    @JSONField(name = "id")
    private String id;

    /**
     * <p>go-cqhttp: 不支持</p>
     * <p>onebot-11: 表情名，详情同type字段</p>
     */
    @JSONField(name = "name")
    private String name;

    public CQPoke(String qq) {
        this.qq = qq;
    }
}
