package top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.base.CQData;

/**
 * @Author: MarisaDAZE
 * @Description: 回复
 * <p><a href="https://docs.go-cqhttp.org/cqcode/#%E5%9B%9E%E5%A4%8D">go-cqhttp 参考</a></p>
 * <p><a href="https://github.com/botuniverse/onebot-11/blob/master/message/segment.md#%E5%9B%9E%E5%A4%8D">onebot-11 参考</a></p>
 * @Date: 2024/07/02
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CQReply extends CQData {

    /**
     * <p>go-cqhttp: 回复时所引用的消息id，必须为本群消息</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "id")
    private String id;

    /**
     * <p>go-cqhttp: 自定义回复的信息</p>
     * <p>onebot-11: 不支持</p>
     */
    @JSONField(name = "text")
    private String text;

    /**
     * <p>go-cqhttp: 自定义回复时的自定义QQ, 如果使用自定义信息必须指定</p>
     * <p>onebot-11: 不支持</p>
     */
    @JSONField(name = "qq")
    private String qq;

    /**
     * <p>go-cqhttp: 自定义回复时的时间, 格式为Unix时间</p>
     * <p>onebot-11: 不支持</p>
     */
    @JSONField(name = "time")
    private Long time;

    /**
     * <p>go-cqhttp: 起始消息序号, 可通过get_msg获得</p>
     * <p>onebot-11: 不支持</p>
     */
    @JSONField(name = "seq")
    private String seq;

    public CQReply(String id, String text) {
        this.id = id;
        this.text = text;
    }
}
