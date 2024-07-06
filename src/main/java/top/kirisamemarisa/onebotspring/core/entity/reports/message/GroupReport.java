package top.kirisamemarisa.onebotspring.core.entity.reports.message;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.cq.CQMessage;
import top.kirisamemarisa.onebotspring.core.entity.reports.common.Anonymous;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.base.MessageReport;
import top.kirisamemarisa.onebotspring.core.serializer.CQDataDeserializer;

/**
 * @Author: MarisaDAZE
 * @Description: 群聊上报
 * @Date: 2024/07/05
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GroupReport extends MessageReport {

    /**
     * 群号
     */
    @JSONField(name = "group_id")
    private String groupId;

    /**
     * 匿名信息, 如果不是匿名消息则为 null
     * <p style="color: orange;">注意：anonymous 字段从 go-cqhttp-v0.9.36 开始支持</p>
     */
    @JSONField(name = "anonymous")
    private Anonymous anonymous;

    /**
     * <p>要回复的内容，默认 不回复 </p>
     * <p style="color: rgb(162,78,130);">来源：快速操作</p>
     */
    @JSONField(name = "reply", deserializeUsing = CQDataDeserializer.class)
    private CQMessage[] reply;

    /**
     * <p>消息内容是否作为纯文本发送 ( 即不解析 CQ 码 )，默认 不转义 </p>
     * <p style="color: rgb(162,78,130);">来源：快速操作</p>
     * <p style="color:orange;">注意：只在 reply 字段是字符串时有效</p>
     */
    @JSONField(name = "auto_escape")
    private Boolean autoEscape;

    /**
     * <p>是否要在回复开头 at 发送者 ( 自动添加 ) ，默认 at发送者 </p>
     * <p style="color: rgb(162,78,130);">来源：快速操作</p>
     * <p style="color:orange;">注意：发送者是匿名用户时无效</p>
     */
    @JSONField(name = "at_sender")
    private Boolean atSender;

    /**
     * <p>撤回该条消息，默认 不撤回 </p>
     * <p style="color: rgb(162,78,130);">来源：快速操作</p>
     */
    @JSONField(name = "delete")
    private Boolean delete;

    /**
     * <p>把发送者踢出群组 ( 需要登录号权限足够 )，
     * <span style="color: orange;">不拒绝</span>
     * 此人后续加群请求，默认 不踢出 </p>
     * <p style="color: rgb(162,78,130);">来源：快速操作</p>
     * <p style="color:orange;">注意：发送者是匿名用户时无效</p>
     */
    @JSONField(name = "kick")
    private Boolean kick;

    /**
     * <p>禁言该消息发送者, 对匿名用户也有效，默认 不禁言 </p>
     * <p style="color: rgb(162,78,130);">来源：快速操作</p>
     */
    @JSONField(name = "ban")
    private Boolean ban;

    /**
     * <p>若要执行禁言操作时的禁言时长，默认 30分钟 </p>
     * <p style="color: rgb(162,78,130);">来源：快速操作</p>
     */
    @JSONField(name = "ban_duration")
    private Integer banDuration;

    public GroupReport(String groupId, Anonymous anonymous) {
        this.groupId = groupId;
        this.anonymous = anonymous;
    }
}
