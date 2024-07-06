package top.kirisamemarisa.onebotspring.core.entity.reports.notice;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.*;
import top.kirisamemarisa.onebotspring.core.entity.reports.notice.base.NoticeReport;
import top.kirisamemarisa.onebotspring.core.enums.reports.notices.NoticeSubType;

/**
 * @Author: MarisaDAZE
 * @Description: 其他客户端在线状态变更
 * @Date: 2024/07/05
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ClientStatusReport extends NoticeReport {

    /**
     * 客户端信息
     * <p>Device 可在 API - <a href="https://docs.go-cqhttp.org/api/#%E5%9F%BA%E7%A1%80%E4%BC%A0%E8%BE%93">获取当前账号在线客户端列表</a> 查看</p>
     * <p> >_<|| 这块的文档太长不想看，先拿Object对付一下</p>
     */
    @JSONField(name = "client")
    private Object client;

    /**
     * 当前是否在线
     */
    @JSONField(name = "online")
    private Boolean online;
}
