package top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.base.CQData;

/**
 * @Author: MarisaDAZE
 * @Description: 链接分享
 * <p><a href="https://docs.go-cqhttp.org/cqcode/#%E9%93%BE%E6%8E%A5%E5%88%86%E4%BA%AB">go-cqhttp 参考</a></p>
 * <p><a href="https://github.com/botuniverse/onebot-11/blob/master/message/segment.md#%E9%93%BE%E6%8E%A5%E5%88%86%E4%BA%AB">onebot-11 参考</a></p>
 * @Date: 2024/07/02
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CQShare extends CQData {

    /**
     * <p>go-cqhttp: 链接URL地址</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "url")
    private String url;

    /**
     * <p>go-cqhttp: 标题</p>
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

    /**
     * <p>go-cqhttp: 发送时可选，图片URL</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "image")
    private String image;

    public CQShare(String url, String title) {
        this.url = url;
        this.title = title;
    }
}
