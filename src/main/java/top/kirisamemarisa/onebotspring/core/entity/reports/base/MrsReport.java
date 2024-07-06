package top.kirisamemarisa.onebotspring.core.entity.reports.base;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import top.kirisamemarisa.onebotspring.core.entity.reports.message.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.metaevent.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.notice.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.request.*;
import top.kirisamemarisa.onebotspring.core.enums.reports.PostType;
import top.kirisamemarisa.onebotspring.core.enums.reports.message.MessageType;
import top.kirisamemarisa.onebotspring.core.enums.reports.metaevent.MetaEventType;
import top.kirisamemarisa.onebotspring.core.enums.reports.notices.NoticeSubType;
import top.kirisamemarisa.onebotspring.core.enums.reports.notices.NoticeType;
import top.kirisamemarisa.onebotspring.core.enums.reports.request.RequestType;
import top.kirisamemarisa.onebotspring.core.serializer.MrsEnumSerializer;
import top.kirisamemarisa.onebotspring.core.util.EnumUtils;

/**
 * @Author: MarisaDAZE
 * @Description: LLOnebot消息上报
 * @Date: 2024/07/05
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class MrsReport {

    /**
     * 事件发生的unix时间戳
     */
    @JSONField(name = "time")
    private Long time;

    /**
     * 收到事件的机器人的 QQ 号
     */
    @JSONField(name = "self_id")
    private String selfId;

    /**
     * 表示该上报的类型, 消息, 消息发送, 请求, 通知, 或元事件
     * <p style="color: orange">
     * 注意：message与message_sent的数据是一致的, 区别仅在于后者是bot发出的消息.
     * 默认配置下不会上报message_sent, 仅在配置message下report-self-message项为true时上报
     * </p>
     */
    @JSONField(name = "post_type", serializeUsing = MrsEnumSerializer.class)
    private PostType postType;

    public static MrsReport translate(JSONObject jsonObject) {
        Class<? extends MrsReport> clazz = null;
        String pt = jsonObject.getString("post_type");
        PostType postType = EnumUtils.translate(pt, PostType.class);
        switch (postType) {
            case MESSAGE, MESSAGE_SENT -> {
                String mt = jsonObject.getString("message_type");
                MessageType messageType = EnumUtils.translate(mt, MessageType.class);
                switch (messageType) {
                    case GROUP -> clazz = GroupReport.class;
                    case PRIVATE -> clazz = PrivateReport.class;
                }
            }
            case REQUEST -> {
                String mt = jsonObject.getString("request_type");
                RequestType requestType = EnumUtils.translate(mt, RequestType.class);
                switch (requestType) {
                    case FRIEND -> clazz = RFriendReport.class;
                    case GROUP -> clazz = RGroupReport.class;
                }
            }
            case NOTICE -> {
                String nt = jsonObject.getString("notice_type");
                NoticeType noticeType = EnumUtils.translate(nt, NoticeType.class);
                switch (noticeType) {
                    case GROUP_UPLOAD -> clazz = GroupUploadReport.class;
                    case GROUP_ADMIN -> clazz = GroupAdminReport.class;
                    case GROUP_DECREASE, GROUP_INCREASE -> clazz = MemberChangeReport.class;
                    case GROUP_BAN -> clazz = GroupBanReport.class;
                    case FRIEND_ADD -> clazz = FriendAddReport.class;
                    case GROUP_RECALL -> clazz = GroupRecallReport.class;
                    case FRIEND_RECALL -> clazz = FriendRecallReport.class;
                    case GROUP_CARD -> clazz = GroupCardReport.class;
                    case OFFLINE_FILE -> clazz = OfflineFileReport.class;
                    case CLIENT_STATUS -> clazz = ClientStatusReport.class;
                    case ESSENCE -> clazz = EssenceReport.class;
                    case NOTIFY -> {
                        String st = jsonObject.getString("sub_type");
                        NoticeSubType noticeSubType = EnumUtils.translate(st, NoticeSubType.class);
                        switch (noticeSubType){
                            case POKE -> clazz = PokeReport.class;
                            case LUCKY_KING -> clazz = LuckyKingReport.class;
                            case HONOR -> clazz = GroupHonorReport.class;
                            case TITLE -> clazz = GroupTitleReport.class;
                        }
                    }
                }
            }
            case META_EVENT -> {
                String met = jsonObject.getString("meta_event_type");
                MetaEventType metaEventType = EnumUtils.translate(met, MetaEventType.class);
                switch (metaEventType) {
                    case HEARTBEAT -> clazz = HeartbeatReport.class;
                    case LIFECYCLE -> clazz = LifecycleReport.class;
                }
            }
        }
        if (clazz == null) return null;
        return jsonObject.to(clazz);
    }
}
