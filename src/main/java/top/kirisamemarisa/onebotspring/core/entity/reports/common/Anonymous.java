package top.kirisamemarisa.onebotspring.core.entity.reports.common;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

/**
 * @Author: MarisaDAZE
 * @Description: 匿名信息
 * @Date: 2024/07/05
 */
@Data
@ToString
public class Anonymous {

    /**
     * 匿名用户 ID
     */
    @JSONField(name = "id")
    private String id;

    /**
     * 匿名用户名称
     */
    @JSONField(name = "name")
    private String name;

    /**
     * 匿名用户 flag, 在调用禁言 API 时需要传入
     */
    @JSONField(name = "flag")
    private String flag;


}
