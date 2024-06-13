package top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import top.kirisamemarisa.onebotspring.annotation.MessageField;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.base.MData;

/**
 * @Author: MarisaDAZE
 * @Description: @(at)消息
 * @Date: 2024/2/15
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class MAt extends MData {

    // 被@用户的QQ号
    @MessageField("qq")
    private String mention;
}
