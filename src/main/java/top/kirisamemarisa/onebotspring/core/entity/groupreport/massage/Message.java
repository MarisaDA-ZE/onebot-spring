package top.kirisamemarisa.onebotspring.core.entity.groupreport.massage;

import lombok.Data;
import lombok.ToString;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.base.MData;
import top.kirisamemarisa.onebotspring.core.enums.ContentType;

/**
 * @Author: MarisaDAZE
 * @Description: 消息内容
 * @Date: 2024/2/15
 */
@Data
@ToString
public class Message {

    private MData data;

    private ContentType type;

}
