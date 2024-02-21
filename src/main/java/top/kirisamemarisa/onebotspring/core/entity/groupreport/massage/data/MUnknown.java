package top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import top.kirisamemarisa.onebotspring.core.entity.groupreport.massage.data.base.MData;

/**
 * @Author: MarisaDAZE
 * @Description: 未知类型，目前遇到的都是空的（{}）
 * @Date: 2024/2/15
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class MUnknown extends MData {

    // 这个没用，这个对象中没有任何属性，因为JSON是{}的
    private String unknown;
}
