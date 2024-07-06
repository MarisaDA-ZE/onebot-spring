package top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.base.CQData;

/**
 * @Author: MarisaDAZE
 * @Description: 位置
 * <p><a href="https://docs.go-cqhttp.org/cqcode/#%E4%BD%8D%E7%BD%AE">go-cqhttp 参考</a></p>
 * <p><a href="https://github.com/botuniverse/onebot-11/blob/master/message/segment.md#%E4%BD%8D%E7%BD%AE">onebot-11 参考</a></p>
 * @Date: 2024/07/02
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CQLocation extends CQData {

    /**
     * <p>go-cqhttp: 纬度</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "lat")
    private Float lat;

    /**
     * <p>go-cqhttp: 经度</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "lon")
    private Float lon;

    /**
     * <p>go-cqhttp: 发送时可选，标题</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "title")
    private String title;

    /**
     * <p>go-cqhttp: 发送时可选，内容描述</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "content")
    private String content;

    public CQLocation(Float lat, Float lon) {
        this.lat = lat;
        this.lon = lon;
    }
}
