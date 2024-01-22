package top.kirisamemarisa.onebotspring.entity;

import lombok.Data;
import lombok.ToString;
import top.kirisamemarisa.onebotspring.entity.massage.MassageData;


/**
 * @Author: MarisaDAZE
 * @Description: 消息类
 * @Date: 2024/1/20
 */
@Data
@ToString
public class Massage {
    private String type;
    private MassageData data;
}


