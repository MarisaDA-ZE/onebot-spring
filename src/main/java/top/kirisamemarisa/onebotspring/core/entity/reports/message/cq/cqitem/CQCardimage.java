package top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.base.CQData;

/**
 * @Author: MarisaDAZE
 * @Description: 一种xml的图片消息（装逼大图，仅发送）
 * <p><a href="https://docs.go-cqhttp.org/cqcode/#cardimage">go-cqhttp 参考</a></p>
 * <p>onebot-11 不支持</p>
 * @Date: 2024/07/02
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CQCardimage extends CQData {

    /**
     * <p>go-cqhttp: 和image的file字段对齐, 支持也是一样的</p>
     * <p>onebot-11: 不支持</p>
     */
    @JSONField(name = "file")
    private String file;

    /**
     * <p>go-cqhttp: 默认不填为400, 最小width</p>
     * <p>onebot-11: 不支持</p>
     */
    @JSONField(name = "minwidth")
    private Integer minWidth;

    /**
     * <p>go-cqhttp: 默认不填为400, 最小height</p>
     * <p>onebot-11: 不支持</p>
     */
    @JSONField(name = "minheight")
    private Integer minHeight;

    /**
     * <p>go-cqhttp: 默认不填为500, 最大width</p>
     * <p>onebot-11: 不支持</p>
     */
    @JSONField(name = "maxwidth")
    private Integer maxWidth;

    /**
     * <p>go-cqhttp: 默认不填为1000, 最大height</p>
     * <p>onebot-11: 不支持</p>
     */
    @JSONField(name = "maxheight")
    private Integer maxHeight;

    /**
     * <p>go-cqhttp:分享来源的名称, 可以留空 </p>
     * <p>onebot-11: 不支持</p>
     */
    @JSONField(name = "source")
    private String source;

    /**
     * <p>go-cqhttp: 分享来源的icon图标url, 可以留空</p>
     * <p>onebot-11: 不支持</p>
     */
    @JSONField(name = "icon")
    private String icon;

    public CQCardimage(String file) {
        this.file = file;
    }

    public CQCardimage(String file, Integer minWidth, Integer minHeight) {
        this.file = file;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
    }
}
