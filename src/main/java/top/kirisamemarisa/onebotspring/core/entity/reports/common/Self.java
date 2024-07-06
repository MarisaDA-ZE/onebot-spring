package top.kirisamemarisa.onebotspring.core.entity.reports.common;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;
import top.kirisamemarisa.onebotspring.core.enums.common.PlatformType;
import top.kirisamemarisa.onebotspring.core.serializer.MrsEnumSerializer;

/**
 * @Author: MarisaDAZE
 * @Description: 自己账号的信息
 * @Date: 2024/2/15
 */
@Data
@ToString
public class Self {

    // 发送平台（一般是“qq”）
    @JSONField(name = "platform", serializeUsing = MrsEnumSerializer.class)
    private PlatformType platform;

    // 用户（自己）的qq号
    @JSONField(name = "user_id")
    private String userId;


}
