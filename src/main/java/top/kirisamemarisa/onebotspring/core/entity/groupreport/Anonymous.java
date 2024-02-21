package top.kirisamemarisa.onebotspring.core.entity.groupreport;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

/**
 * @Author: MarisaDAZE
 * @Description: 匿名信息，如果不是匿名消息则为 null
 * @Date: 2024/2/15
 */
@Data
@ToString
public class Anonymous {

    // 匿名用户 ID
    @JSONField(name = "id")
    private long id;

    // 匿名用户名称
    @JSONField(name = "name")
    private String name;

    // 匿名用户 flag，在调用禁言 API 时需要传入
    @JSONField(name = "flag")
    private String flag;
}
