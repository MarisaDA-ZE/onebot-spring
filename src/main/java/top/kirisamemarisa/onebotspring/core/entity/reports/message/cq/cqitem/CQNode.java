package top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.cqitem.base.CQData;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.CQMessage;
import top.kirisamemarisa.onebotspring.core.serializer.CQDataDeserializer;

/**
 * @Author: MarisaDAZE
 * @Description: 合并转发消息节点（仅发送）
 * <p><a href="https://docs.go-cqhttp.org/cqcode/#%E5%90%88%E5%B9%B6%E8%BD%AC%E5%8F%91%E6%B6%88%E6%81%AF%E8%8A%82%E7%82%B9">go-cqhttp 参考</a></p>
 * <p><a href="https://github.com/botuniverse/onebot-11/blob/master/message/segment.md#%E5%90%88%E5%B9%B6%E8%BD%AC%E5%8F%91%E8%8A%82%E7%82%B9-">onebot-11 参考</a></p>
 * @Date: 2024/07/02
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CQNode extends CQData {

    /**
     * <p>go-cqhttp: 转发的消息 ID</p>
     * <p>直接引用他人的消息合并转发, 实际查看顺序为原消息发送顺序 与下面的自定义消息二选一</p>
     * <p>onebot-11: 同go-cqhttp</p>
     */
    @JSONField(name = "id")
    private String id;

    /**
     * <p>go-cqhttp: 不支持</p>
     * <p>onebot-11: 发送者QQ号</p>
     */
    @JSONField(name = "user_id")
    private String userId;

    /**
     * <p>go-cqhttp: 发送者显示名字</p>
     * <p>用于自定义消息 (自定义消息并合并转发, 实际查看顺序为自定义消息段顺序)</p>
     * <p>onebot-11: 不支持</p>
     */
    @JSONField(name = "name")
    private String name;

    /**
     * <p>go-cqhttp: 不支持</p>
     * <p>onebot-11: 发送者昵称</p>
     */
    @JSONField(name = "nickname")
    private String nickName;

    /**
     * <p>go-cqhttp: 发送者QQ号，用于自定义消息</p>
     * <p>onebot-11: 不支持</p>
     */
    @JSONField(name = "uin")
    private String uin;

    /**
     * <p>go-cqhttp: 具体消息，用于自定义消息，不支持转发套娃；content字段支持Array套娃</p>
     * <p>onebot-11: 消息内容，支持发送消息时的 message 数据类型，见
     * <a href="https://github.com/botuniverse/onebot-11/tree/master/api#%E5%8F%82%E6%95%B0">API的参数</a>
     * </p>
     */
    @JSONField(name = "content", deserializeUsing = CQDataDeserializer.class)
    private CQMessage[] content;

    /**
     * <p>go-cqhttp: 具体消息，用于自定义消息</p>
     * <p>onebot-11: 不支持</p>
     */
    @JSONField(name = "seq")
    private CQMessage seq;

    /**
     * 直接引用消息合并转发
     * <p>此构造函数适用于go-cqhttp</p>
     *
     * @param id 自定义消息ID
     */
    public CQNode(String id) {
        this.id = id;
    }

    /**
     * 自定义消息合并转发
     * <p>此构造函数适用于go-cqhttp</p>
     *
     * @param name    发送者名字
     * @param uni     发送者QQ
     * @param content 消息内容
     * @param seq     暂时不知道这个有什么用，由于前三个参数和onebot-11的构造函数一样，req只是用于区分二者的构造函数
     */
    public CQNode(String uni, String name, CQMessage[] content, CQMessage seq) {
        this.name = name;
        this.uin = uni;
        this.content = content;
        this.seq = seq;
    }

    /**
     * 自定义消息合并转发
     * <p>此构造函数适用于onebot-11</p>
     *
     * @param userId   发送者QQ
     * @param nickName 发送者昵称
     * @param content  消息段内容
     */
    public CQNode(String userId, String nickName, CQMessage[] content) {
        this.userId = userId;
        this.nickName = nickName;
        this.content = content;
    }
}
