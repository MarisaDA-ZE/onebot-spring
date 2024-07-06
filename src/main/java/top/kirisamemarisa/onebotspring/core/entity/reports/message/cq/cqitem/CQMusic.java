package top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.base.CQData;
import top.kirisamemarisa.onebotspring.core.enums.reports.message.cq.cqitem.CQMusicShareType;
import top.kirisamemarisa.onebotspring.core.serializer.MrsEnumSerializer;

/**
 * @Author: MarisaDAZE
 * @Description: 音乐分享，音乐自定义分享与本类进行合并了
 * <p><a href="https://docs.go-cqhttp.org/cqcode/#%E9%9F%B3%E4%B9%90%E5%88%86%E4%BA%AB">go-cqhttp 参考</a></p>
 * <p><a href="https://github.com/botuniverse/onebot-11/blob/master/message/segment.md#%E9%9F%B3%E4%B9%90%E5%88%86%E4%BA%AB-">onebot-11 参考</a></p>
 * @Date: 2024/07/02
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CQMusic extends CQData {

    /**
     * <p>go-cqhttp: 分别表示使用（qq：QQ音乐、163：网易云音乐、xm：虾米音乐、custom：表示自定义分享）</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "type", serializeUsing = MrsEnumSerializer.class)
    private CQMusicShareType type;

    /**
     * <p>go-cqhttp: 歌曲 ID（类型为custom时无效）</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "id")
    private String id;

    /**
     * <p>go-cqhttp: 点击后跳转目标 URL</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "url")
    private String url;

    /**
     * <p>go-cqhttp: 音乐 URL</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "audio")
    private String audio;

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
     * <p>go-cqhttp: 发送时可选，图片 URL</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "image")
    private String image;

    public CQMusic(CQMusicShareType type, String id) {
        this.type = type;
        this.id = id;
    }

    public CQMusic(String url, String audio, String title) {
        this.type = CQMusicShareType.CUSTOM;
        this.url = url;
        this.audio = audio;
        this.title = title;
    }

    public CQMusic(String url, String audio, String title, String content, String image) {
        this.type = CQMusicShareType.CUSTOM;
        this.url = url;
        this.audio = audio;
        this.title = title;
        this.content = content;
        this.image = image;
    }
}
