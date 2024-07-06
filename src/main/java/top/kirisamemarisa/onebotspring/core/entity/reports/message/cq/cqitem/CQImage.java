package top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.base.CQData;

/**
 * @Author: MarisaDAZE
 * @Description: 图片
 * <p><a href="https://docs.go-cqhttp.org/cqcode/#%E5%9B%BE%E7%89%87">go-cqhttp 参考</a></p>
 * <p><a href="https://github.com/botuniverse/onebot-11/blob/master/message/segment.md#%E5%9B%BE%E7%89%87">onebot-11 参考</a></p>
 * @Date: 2024/07/02
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CQImage extends CQData {

    /**
     * <p>go-cqhttp: 图片文件名，支持：绝对路径、网络路径、Base64</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "file")
    private String file;

    /**
     * <p>go-cqhttp: 图片类型（flash 表示闪照, show 表示秀图, 默认普通图片）</p>
     * <p>onebot-11: 图片类型，flash 表示闪照，无此参数表示普通图片</p>
     */
    @JSONField(name = "type")
    private String type;

    /**
     * <p>go-cqhttp: 图片子类型, 只出现在群聊.</p>
     * <p>
     * 0	正常图片
     * 1	表情包, 在客户端会被分类到表情包图片并缩放显示
     * 2	热图
     * 3	斗图
     * 4	智图?
     * 7	贴图
     * 8	自拍
     * 9	贴图广告?
     * 10	有待测试
     * 13	热搜图
     * </p>
     * <p>onebot-11: 不支持</p>
     */
    @JSONField(name = "subType")
    private Integer subType;

    /**
     * <p>go-cqhttp: 图片URL</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "url")
    private String url;

    /**
     * <p>go-cqhttp: 只在通过网络URL发送时有效, 表示是否使用已缓存的文件, 默认1</p>
     * <p>可能的值：0、1</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "cache")
    private Integer cache;

    /**
     * <p>go-cqhttp: 发送秀图时的特效id, 默认为40000</p>
     * <p>
     * 40000	普通
     * 40001	幻影
     * 40002	抖动
     * 40003	生日
     * 40004	爱你
     * 40005	征友
     * </p>
     * <p>onebot-11: 不支持</p>
     */
    @JSONField(name = "id")
    private Integer id;

    /**
     * <p>go-cqhttp: 通过网络下载图片时的线程数, 默认单线程. (在资源不支持并发时会自动处理)</p>
     * <p>可能的值：2、3</p>
     * <p>onebot-11: 不支持</p>
     */
    @JSONField(name = "c")
    private Integer c;

    /**
     * <p>go-cqhttp: 不支持</p>
     * <p>onebot-11: 只在通过网络URL发送时有效，表示是否通过代理下载文件（需通过环境变量或配置文件配置代理），默认1</p>
     */
    @JSONField(name = "proxy")
    private String proxy;

    /**
     * <p>go-cqhttp: 不支持</p>
     * <p>onebot-11: 只在通过网络 URL 发送时有效，单位秒，表示下载网络文件的超时时间，默认不超时</p>
     */
    @JSONField(name = "timeout")
    private Integer timeout;

    /**
     * 普通图片构造函数
     * @param file  图片文件
     */
    public CQImage(String file) {
        this.file = file;
    }
}
