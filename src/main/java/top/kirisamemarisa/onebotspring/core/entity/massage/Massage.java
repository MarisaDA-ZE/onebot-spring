package top.kirisamemarisa.onebotspring.core.entity.massage;

import lombok.Data;
import lombok.ToString;
import top.kirisamemarisa.onebotspring.core.entity.massage.data.base.MData;
import top.kirisamemarisa.onebotspring.core.enums.MassageType;

/**
 * @Author: MarisaDAZE
 * @Description: 消息内容
 * @Date: 2024/2/15
 */
@Data
@ToString
public class Massage {

    private MData data;

    private MassageType type;

}
