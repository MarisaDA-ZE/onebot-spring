package top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.base.CQData;

/**
 * @Author: MarisaDAZE
 * @Description: 语音
 * <p><a href="https://docs.go-cqhttp.org/cqcode/#%E8%AF%AD%E9%9F%B3">go-cqhttp 参考</a></p>
 * <p><a href="https://github.com/botuniverse/onebot-11/blob/master/message/segment.md#%E8%AF%AD%E9%9F%B3">onebot-11 参考</a></p>
 * @Date: 2024/07/02
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CQVoice extends CQData {

    /**
     * <p>go-cqhttp: 语音文件名（支持URL、本地文件路径、Base64）</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "file")
    private String file;

    /**
     * <p>go-cqhttp: 音频URL</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "url")
    private String url;

    /**
     * <p>go-cqhttp: 发送时可选, 默认 0, 设置为 1 表示变声</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "magic")
    private String magic;

    /**
     * <p>go-cqhttp: 只在通过网络 URL 发送时有效, 表示是否使用已缓存的文件, 默认 1</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "cache")
    private String cache;

    /**
     * <p>go-cqhttp: 只在通过网络URL发送时有效, 表示是否通过代理下载文件 (需通过环境变量或配置文件配置代理), 默认1</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "proxy")
    private String proxy;

    /**
     * <p>go-cqhttp: 只在通过网络 URL 发送时有效, 单位秒, 表示下载网络文件的超时时间 , 默认不超时</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "timeout")
    private Integer timeout;

    public CQVoice(String file) {
        this.file = file;
    }

    public CQVoice(String url, String file) {
        this.url = url;
        this.file = file;
    }
}
