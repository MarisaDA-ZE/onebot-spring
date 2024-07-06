package top.kirisamemarisa.onebotspring.core.entity.reports.metaevent;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: MarisaDAZE
 * @Description: 应用程序状态
 * @Date: 2024/7/5
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HeartStatus {

    /**
     * 程序是否初始化完毕
     */
    @JSONField(name = "app_initialized")
    private Boolean appInitialized;

    /**
     * 程序是否可用
     */
    @JSONField(name = "app_enabled")
    private Boolean appEnabled;

    /**
     * 插件正常(可能为 null)
     */
    @JSONField(name = "plugins_good")
    private Boolean pluginsGood;

    /**
     * 程序正常
     */
    @JSONField(name = "app_good")
    private Boolean appGood;

    /**
     * 是否在线
     */
    @JSONField(name = "online")
    private Boolean online;

    /**
     * 统计信息
     */
    @JSONField(name = "stat")
    private StatusStatistics stat;
}
