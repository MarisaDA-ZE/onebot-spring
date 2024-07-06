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
public class GroupFile {

    /**
     * 文件 ID
     */
    @JSONField(name = "id")
    private String id;

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
     * busid ( 目前不清楚有什么作用 )
     */
    @JSONField(name = "busid")
    private String busid;
}
