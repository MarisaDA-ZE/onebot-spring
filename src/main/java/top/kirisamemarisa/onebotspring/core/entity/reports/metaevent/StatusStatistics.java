package top.kirisamemarisa.onebotspring.core.entity.reports.metaevent;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: MarisaDAZE
 * @Description: 统计信息
 * @Date: 2024/07/05
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StatusStatistics {

    /**
     * 收包数
     */
    @JSONField(name = "packet_received")
    private Long packetReceived;

    /**
     * 发包数
     */
    @JSONField(name = "packet_sent")
    private Long packetSent;

    /**
     * 丢包数
     */
    @JSONField(name = "packet_lost")
    private Long packetLost;

    /**
     * 消息接收数
     */
    @JSONField(name = "message_received")
    private Long messageReceived;

    /**
     * 消息发送数
     */
    @JSONField(name = "message_sent")
    private Long messageSent;

    /**
     * 连接断开次数
     */
    @JSONField(name = "disconnect_times")
    private Long disconnectTimes;

    /**
     * 连接丢失次数
     */
    @JSONField(name = "lost_times")
    private Long lostTimes;

    /**
     * 最后一次消息时间
     */
    @JSONField(name = "last_message_time")
    private Long lastMessageTime;
}
