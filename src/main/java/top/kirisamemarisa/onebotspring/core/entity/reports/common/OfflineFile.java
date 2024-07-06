package top.kirisamemarisa.onebotspring.core.entity.reports.common;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: MarisaDAZE
 * @Description: 群文件信息
 * @Date: 2024/07/05
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OfflineFile {

    /**
     * 文件名
     */
    @JSONField(name = "name")
    private String name;

    /**
     * 文件大小 ( 字节数 )
     */
    @JSONField(name = "size")
    private Long size;

    /**
     * 下载链接
     */
    @JSONField(name = "url")
    private String url;
}
