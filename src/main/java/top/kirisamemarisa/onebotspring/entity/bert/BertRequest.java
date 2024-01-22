package top.kirisamemarisa.onebotspring.entity.bert;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: MarisaDAZE
 * @Description: BertRequest.描述
 * @Date: 2024/1/21
 */
@Data
@ToString
public class BertRequest {
    private Object[] data;
    private String event_data;
    private Integer fn_index;
}
