package top.kirisamemarisa.onebotspring.core.entity.massage.data;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import top.kirisamemarisa.onebotspring.core.entity.massage.data.base.MData;

/**
 * @Author: MarisaDAZE
 * @Description: 文本消息
 * @Date: 2024/2/15
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class MText extends MData {

    // 文本信息
    @JSONField(name = "text")
    private String text;
}
