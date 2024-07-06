package top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.base.CQData;

/**
 * @Author: MarisaDAZE
 * @Description: 返回文本对象
 * <p><a href="https://docs.go-cqhttp.org/cqcode/#%E7%9F%AD%E8%A7%86%E9%A2%91">go-cqhttp 参考</a></p>
 * <p><a href="https://github.com/botuniverse/onebot-11/blob/master/message/segment.md#%E7%9F%AD%E8%A7%86%E9%A2%91">onebot-11 参考</a></p>
 * @Date: 2024/07/02
 */
@Data
@ToString
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CQVideo extends CQData {

    /**
     * <p>go-cqhttp: 视频地址（支持http、本地文件路径）</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "file")
    private String file;

    /**
     * <p>go-cqhttp: 视频封面, 支持http, file和base64发送, 格式必须为jpg</p>
     * <p>onebot-11: 不支持</p>
     */
    @JSONField(name = "cover")
    private String cover;

    /**
     * <p>go-cqhttp: 通过网络下载视频时的线程数, 默认单线程. (在资源不支持并发时会自动处理)</p>
     * <p>onebot-11: 不支持</p>
     */
    @JSONField(name = "c")
    private Integer c;

    /**
     * <p>go-cqhttp: 不支持 </p>
     * <p>onebot-11: 视频URL</p>
     */
    @JSONField(name = "url")
    private String url;

    /**
     * <p>go-cqhttp: 不支持</p>
     * <p>onebot-11: 只在通过网络URL发送时有效，表示是否使用已缓存的文件，默认1</p>
     */
    @JSONField(name = "cache")
    private Integer cache;

    /**
     * <p>go-cqhttp: 不支持</p>
     * <p>onebot-11: 只在通过网络URL发送时有效，表示是否通过代理下载文件（需通过环境变量或配置文件配置代理），默认1</p>
     */
    @JSONField(name = "proxy")
    private Integer proxy;

    /**
     * <p>go-cqhttp: 不支持</p>
     * <p>onebot-11: 只在通过网络URL发送时有效，单位秒，表示下载网络文件的超时时间，默认不超时</p>
     */
    @JSONField(name = "timeout")
    private Integer timeout;

    public CQVideo(String file) {
        this.file = file;
    }
}
